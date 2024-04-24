package com.douzon.smartlogistics.domain.porderitem.dao;

import com.douzon.smartlogistics.domain.entity.POrder;
import com.douzon.smartlogistics.domain.entity.POrderItem;
import com.douzon.smartlogistics.domain.entity.constant.State;
import com.douzon.smartlogistics.domain.item.dao.mapper.ItemMapper;
import com.douzon.smartlogistics.domain.porder.dao.mapper.POrderMapper;
import com.douzon.smartlogistics.domain.porder.exception.InvalidStateException;
import com.douzon.smartlogistics.domain.porder.exception.UnModifiableStateException;
import com.douzon.smartlogistics.domain.porderitem.dao.mapper.POrderItemMapper;
import com.douzon.smartlogistics.domain.porderitem.dto.POrderItemInsertDto;
import com.douzon.smartlogistics.domain.porderitem.dto.POrderItemModifyDto;
import com.douzon.smartlogistics.domain.porderitem.dto.POrderItemStateModifyDto;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
@Slf4j
public class POrderItemDao {

    private final POrderMapper pOrderMapper;
    private final POrderItemMapper pOrderItemMapper;
    private final ItemMapper itemMapper;

    @Transactional
    public void insert(POrderItemInsertDto pOrderItemInsertDto) {
        if (!checkExistItem(pOrderItemInsertDto.getItemCode())) {
            throw new NoSuchElementException("해당 물품 데이터는 존재하지 않습니다.");
        }

        if (!checkExistPOrder(pOrderItemInsertDto.getPOrderCode())) {
            throw new NoSuchElementException("해당 발주 데이터는 존재하지 않습니다.");
        }

        if (checkPOrderCode(pOrderItemInsertDto.getPOrderCode())) {
            String maxPkNum = pOrderItemMapper.selectPkNumber(pOrderItemInsertDto.getPOrderCode());
            pOrderItemInsertDto.setPOrderItemNo(Integer.valueOf(maxPkNum) + 1);
        }

        /*TODO
           1. 발주 코드(pOrderCode)를 기반으로 발주품목이 있는지 조회
        *  2. 발주품목이 있다면 해당 발주품목의 가장 마지막 PK를 조회해서 해당 PK 보다 +1 의 값을 더해 줌
        */

        pOrderItemMapper.insert(pOrderItemInsertDto);
    }

    @Transactional
    public void modify(Integer pOrderItemNo, POrderItemModifyDto pOrderItemModifyDto) {
        POrderItem retrievePOrderItem = retrievePOrderItem(pOrderItemNo, pOrderItemModifyDto.getPOrderCode());
        if (retrievePOrderItem.getPOrderState() != State.WAIT) {
            throw new UnModifiableStateException();
        }
        pOrderItemMapper.modify(pOrderItemNo, pOrderItemModifyDto);
    }

    public List<POrderItem> searchPOrderItemList(String pOrderCode) {
        POrder retrievePOrder = retrievePOrder(pOrderCode);
        return pOrderItemMapper.searchPOrderItemList(retrievePOrder.getPOrderCode());
    }

    @Transactional
    public void delete(List<Integer> pOrderItemNo, String pOrderCode) {
        List<POrderItem> checkPOrderItems = getPOrderItems(pOrderItemNo, pOrderCode);
        if (checkPOrderItems.isEmpty()) {
            throw new NoSuchElementException("해당 발주 물품 내역이 존재하지 않습니다");
        }
        List<Integer> noIngStatePOrdersItems = checkPOrderItems.stream()
                .filter(i -> i.getPOrderState() != State.ING)
                .map(POrderItem::getPOrderItemNo)
                .collect(Collectors.toList());

        if (noIngStatePOrdersItems.isEmpty()) {
            throw new InvalidStateException();
        }
        pOrderItemMapper.deletePOrderItem(noIngStatePOrdersItems, pOrderCode);

    }

    @Transactional
    public Integer modifyStateToIng(Integer pOrderItemNo, POrderItemStateModifyDto pOrderItemStateModifyDto) {
        POrderItem modifiedPOrderItem = modifyStatePOrderItem(pOrderItemNo, pOrderItemStateModifyDto);

        pOrderMapper.modifyStateToIng(modifiedPOrderItem.getPOrderCode(), pOrderItemStateModifyDto);

        return modifiedPOrderItem.getPOrderItemNo();
    }

    @Transactional
    public Integer modifyStateToCmp(Integer pOrderItemNo, POrderItemStateModifyDto pOrderItemStateModifyDto) {
        // 2. 상태 업데이트 후 해당 발주의 모든 발주 물품의 상태를 확인 한 뒤 CMP 라면 발주 상태를 CMP로 업데이트 한다.
        POrderItem modifiedPOrderItem = modifyStatePOrderItem(pOrderItemNo, pOrderItemStateModifyDto);

        List<POrderItem> pOrderItems = searchPOrderItemList(pOrderItemStateModifyDto.getPOrderCode());

        int totalPOrderItems = pOrderItems.size();

        long countCmpNum = pOrderItems.stream()
                .map(POrderItem::getPOrderState)
                .takeWhile(state -> state == State.CMP)
                .count();

        if (totalPOrderItems == countCmpNum) {
            pOrderMapper.modifyStateToCmp(modifiedPOrderItem.getPOrderCode(), pOrderItemStateModifyDto);
        }

        return modifiedPOrderItem.getPOrderItemNo();
    }

    @Transactional
    public POrderItem modifyStatePOrderItem(Integer pOrderItemNo, POrderItemStateModifyDto pOrderItemStateModifyDto) {
        POrderItem retrievePOrderItem = retrievePOrderItem(pOrderItemNo, pOrderItemStateModifyDto.getPOrderCode());
        pOrderItemMapper.modifyState(retrievePOrderItem.getItemCode(), pOrderItemStateModifyDto);

        return retrievePOrderItem;
    }

    private List<POrderItem> getPOrderItems(List<Integer> pOrderItemNo, String pOrderCode) {
        return pOrderItemMapper.checkPOrderItems(pOrderItemNo, pOrderCode);

    }

    private POrderItem retrievePOrderItem(Integer pOrderItemNo, String pOrderCode) {
        return pOrderItemMapper.retrieve(pOrderItemNo, pOrderCode).orElseThrow(
                () -> {
                    throw new NoSuchElementException("해당 발주 물품 데이터는 존재하지 않습니다.");
                }
        );
    }

    private POrder retrievePOrder(String pOrderCode) {
        return pOrderMapper.retrieve(pOrderCode).orElseThrow(
                () -> {
                    throw new NoSuchElementException("해당 발주 데이터는 존재하지 않습니다.");
                }
        );
    }

    private boolean checkExistItem(Integer itemCode) {
        return itemMapper.checkExistItem(itemCode);
    }

    private boolean checkExistPOrder(String pOrderCode) {
        return pOrderMapper.checkExistPOrder(pOrderCode);
    }

    private boolean checkPOrderCode(String pOrderCode) {
        return pOrderItemMapper.checkPOrderCode(pOrderCode);
    }

    public int searchPOrderItemRemainder(String porderCode, Integer porderItemNo) {
        return pOrderItemMapper.searchPOrderItemRemainder(porderCode,porderItemNo);
    }

    public List<POrderItem> exceptSearchCmpPorderItemList(String pOrderCode) {
        POrder retrievePOrder = retrievePOrder(pOrderCode);
        return pOrderItemMapper.exceptSearchCmpPorderItemList(retrievePOrder.getPOrderCode());
    }
}
