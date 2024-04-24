package com.douzon.smartlogistics.domain.receiveitem.dao;
import com.douzon.smartlogistics.domain.entity.ReceiveItem;
import com.douzon.smartlogistics.domain.entity.constant.State;
import com.douzon.smartlogistics.domain.porder.dao.mapper.POrderMapper;
import com.douzon.smartlogistics.domain.porderitem.dao.mapper.POrderItemMapper;
import com.douzon.smartlogistics.domain.porderitem.dto.POrderItemStateModifyDto;
import com.douzon.smartlogistics.domain.receiveitem.dao.mapper.ReceiveItemMapper;
import com.douzon.smartlogistics.domain.receiveitem.dto.ReceiveItemInsertDto;
import com.douzon.smartlogistics.domain.receiveitem.dto.ReceiveItemListDto;
import com.douzon.smartlogistics.domain.receiveitem.dto.ReceiveItemModifyDto;
import com.douzon.smartlogistics.domain.warehousestock.dao.mapper.WarehouseStockMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ReceiveItemDao {

    private final ReceiveItemMapper receiveItemMapper;
    private final WarehouseStockMapper warehouseStockMapper;
    private final POrderItemMapper pOrderItemMapper;
    private final POrderMapper pOrderMapper;

    public List<ReceiveItemListDto> searchReceiveItem(String receiveCode) {
        return receiveItemMapper.searchReceiveItem(receiveCode);
    }

    @Transactional
    public void modifyReceiveItem(ReceiveItemModifyDto receiveItemModifyDto) {
        POrderItemStateModifyDto pOrderItemStateModifyDto = new POrderItemStateModifyDto();

        receiveItemMapper.modifyReceiveItem(receiveItemModifyDto);
        warehouseStockMapper.modifyWarehouseStock(receiveItemModifyDto);

        String modifyIp = receiveItemModifyDto.getModifyIp();
        String modifyId = receiveItemModifyDto.getModifyId();

        // 발주쪽에 상태 변경으로 인한 수정 아이피 아이디
        pOrderItemStateModifyDto.setModifyIp(modifyIp);
        pOrderItemStateModifyDto.setModifyId(modifyId);
        pOrderItemStateModifyDto.setPOrderCode(receiveItemModifyDto.getPOrderCode());

        Double receiveItemCountSum = receiveItemMapper.receiveItemCount(receiveItemModifyDto.getPOrderCode(), receiveItemModifyDto.getPOrderItemNo());
        Double pOrderItemCount = pOrderItemMapper.pOrderItemCount(receiveItemModifyDto.getPOrderCode(), receiveItemModifyDto.getPOrderItemNo());
        System.out.println("receiveItemModifyDto = " + receiveItemModifyDto);
        System.out.println("receiveItemCountSum = " + receiveItemCountSum);
        System.out.println("pOrderItemCount = " + pOrderItemCount);

        // 수정된 입고수량이 발주수량보다 크거나 같으면 동작(입고 들어오는 수량+ 이전에 입고된 수량) -> porderItem은 cmp / porder가 가지는 품목들 ing나 cmp 있으면 확인 후 맞으면 cmp 아니면 ing
        if (receiveItemCountSum >= pOrderItemCount){
            pOrderItemStateModifyDto.setPOrderState(State.CMP);
            pOrderItemMapper.modifyState(receiveItemModifyDto.getPOrderItemNo(),pOrderItemStateModifyDto); // 발주 품목 상태 CMP 수정
        }
        // 작으면 동작 -> ING
        else if(receiveItemCountSum < pOrderItemCount) {
            pOrderItemStateModifyDto.setPOrderState(State.ING);
            pOrderItemMapper.modifyState(receiveItemModifyDto.getPOrderItemNo(),pOrderItemStateModifyDto); // 발주 품목 상태 ING 수정
        }

        if (pOrderItemMapper.searchPOrderItemStateCount(receiveItemModifyDto.getPOrderCode())!=0){
            pOrderMapper.modifyStateToIng(receiveItemModifyDto.getPOrderCode(),pOrderItemStateModifyDto);
        }else if (pOrderItemMapper.searchPOrderItemStateCount(receiveItemModifyDto.getPOrderCode())==0){
            pOrderMapper.modifyStateToCmp(receiveItemModifyDto.getPOrderCode(),pOrderItemStateModifyDto);
        }
    }
    public void deleteReceiveItem(String receiveCode, Long receiveItemNo) {
        retrieveReceiveItem(receiveCode, receiveItemNo);
        receiveItemMapper.deleteReceiveItem(receiveCode, receiveItemNo);
    }

    private Long retrieveReceiveItem(String receiveCode, Long receiveItemNo) {
        return receiveItemMapper.retrieveReceiveItem(receiveCode, receiveItemNo).orElseThrow(() -> {
            throw new NoSuchElementException("해당 입고물품은 존재하지 않습니다.");
        }).getReceiveItemNo();
    }

    @Transactional
    public void insertReceiveItem(ReceiveItemInsertDto receiveItemInsertDto) {
        receiveItemMapper.insertReceiveItem(receiveItemInsertDto);
    }
}

