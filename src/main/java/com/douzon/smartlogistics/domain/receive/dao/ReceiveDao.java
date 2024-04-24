package com.douzon.smartlogistics.domain.receive.dao;

import com.douzon.smartlogistics.domain.entity.Receive;
import com.douzon.smartlogistics.domain.entity.constant.State;
import com.douzon.smartlogistics.domain.porder.dao.mapper.POrderMapper;
import com.douzon.smartlogistics.domain.porderitem.dao.mapper.POrderItemMapper;
import com.douzon.smartlogistics.domain.porderitem.dto.POrderItemStateModifyDto;
import com.douzon.smartlogistics.domain.receive.dao.mapper.ReceiveMapper;
import com.douzon.smartlogistics.domain.receive.dto.ReceiveInsertDto;
import com.douzon.smartlogistics.domain.receive.dto.ReceiveListDto;
import com.douzon.smartlogistics.domain.receive.dto.ReceiveModifyDto;
import com.douzon.smartlogistics.domain.receiveitem.dao.mapper.ReceiveItemMapper;
import com.douzon.smartlogistics.domain.receiveitem.dto.ReceiveItemInsertDto;
import com.douzon.smartlogistics.domain.warehousestock.dao.mapper.WarehouseStockMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ReceiveDao {

    private final ReceiveMapper receiveMapper;
    private final ReceiveItemMapper receiveItemMapper;
    private final WarehouseStockMapper warehouseStockMapper;
    private final POrderItemMapper pOrderItemMapper;
    private final POrderMapper pOrderMapper;

    public List<ReceiveListDto> findReceive(String receiveCode, String manager, String createIp, String createId, String startDate, String endDate) {
        return receiveMapper.findReceive(receiveCode, manager, createIp, createId, startDate, endDate);
    }

    @Transactional
    public void  insertReceive(ReceiveInsertDto receiveInsertDto){
        POrderItemStateModifyDto pOrderItemStateModifyDto = new POrderItemStateModifyDto();

        receiveMapper.insertReceive(receiveInsertDto);
        String createIp = receiveInsertDto.getCreateIp();
        String createId = receiveInsertDto.getCreateId();
        String receiveDate = receiveInsertDto.getReceiveDate();

        // 발주쪽에 상태 변경으로 인한 수정 아이피 아이디
        pOrderItemStateModifyDto.setModifyIp(createIp);
        pOrderItemStateModifyDto.setModifyId(createId);

        int receiveItemNo =0;
        for(ReceiveItemInsertDto receiveItem : receiveInsertDto.getReceiveItems()) {
            receiveItem.setReceiveCode(receiveInsertDto.getReceiveCode());
            receiveItemNo= receiveItemNo+1;
            receiveItem.setReceiveItemNo(receiveItemNo);
            receiveItem.setCreateIp(createIp);
            receiveItem.setCreateId(createId);
            receiveItemMapper.insertReceiveItem(receiveItem); // 입고 품목 저장
            warehouseStockMapper.insertWarehouseStock(receiveItem, receiveDate); // 입고 후 창고 저장
            pOrderItemStateModifyDto.setPOrderCode(receiveItem.getPorderCode()); //입고 코드 dto 저장

            Double receiveItemCountSum = receiveItemMapper.receiveItemCount(receiveItem.getPorderCode(),receiveItem.getPorderItemNo());

            // 입고수량이 발주수량보다 크거나 같으면 동작(입고 들어오는 수량+ 이전에 입고된 수량) -> porderItem은 cmp / porder가 가지는 품목들 ing나 cmp 있으면 확인 후 맞으면 cmp 아니면 ing
            if (receiveItemCountSum >= pOrderItemMapper.pOrderItemCount(receiveItem.getPorderCode(),receiveItem.getPorderItemNo())){
                pOrderItemStateModifyDto.setPOrderState(State.CMP);
                pOrderItemMapper.modifyState(receiveItem.getPorderItemNo(),pOrderItemStateModifyDto); // 발주 품목 상태 CMP 수정
            }
            // 작으면 동작 -> ING
            else if(receiveItemCountSum < pOrderItemMapper.pOrderItemCount(receiveItem.getPorderCode(),receiveItem.getPorderItemNo())) {
                pOrderItemStateModifyDto.setPOrderState(State.ING);
                pOrderItemMapper.modifyState(receiveItem.getPorderItemNo(),pOrderItemStateModifyDto); // 발주 품목 상태 ING 수정
            }

            //발주품목에 대한 입고 및 창고 적재 진행 후 해당 발주품목의 발주에 대한 상태 업데이트/ 발주품목이 ing거나 wait이 있을 경우 ing 업데이트, ing나 wait이 0이면 CMP
            if (pOrderItemMapper.searchPOrderItemStateCount(receiveItem.getPorderCode())!=0){
                pOrderMapper.modifyStateToIng(receiveItem.getPorderCode(),pOrderItemStateModifyDto);
            }else if (pOrderItemMapper.searchPOrderItemStateCount(receiveItem.getPorderCode())==0){
                pOrderMapper.modifyStateToCmp(receiveItem.getPorderCode(),pOrderItemStateModifyDto);
            }
        }
    }

    @Transactional
    public void deleteReceive(String receiveCode) {
        retrieveReceive(receiveCode);
        receiveMapper.deleteReceive(receiveCode);
    }

    //TODO: 전역 예외처리 필요
    private String retrieveReceive(String receiveCode) {
        return receiveMapper.retrieve(receiveCode).orElseThrow(() -> {
            throw new NoSuchElementException("해당 입고는 존재하지 않습니다.");
        }).getReceiveCode();
    }

    @Transactional
    public void modifyReceive(ReceiveModifyDto receiveModifyDto) {
        receiveMapper.modifyReceive(receiveModifyDto);
    }
}
