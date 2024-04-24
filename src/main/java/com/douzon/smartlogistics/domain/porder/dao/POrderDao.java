package com.douzon.smartlogistics.domain.porder.dao;

import com.douzon.smartlogistics.domain.entity.POrder;
import com.douzon.smartlogistics.domain.entity.constant.State;
import com.douzon.smartlogistics.domain.porder.dao.mapper.POrderMapper;
import com.douzon.smartlogistics.domain.porder.dto.POrderInsertDto;
import com.douzon.smartlogistics.domain.porder.dto.POrderModifyDto;
import com.douzon.smartlogistics.domain.porder.exception.UnModifiableStateException;
import com.douzon.smartlogistics.domain.porderitem.dao.mapper.POrderItemMapper;
import com.douzon.smartlogistics.domain.porderitem.dto.POrderItemInsertDto;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
@Slf4j
public class POrderDao {

    private final POrderMapper pOrderMapper;
    private final POrderItemMapper pOrderItemMapper;

    public List<POrder> searchPOrder(String pOrderCode, String manager, State state, String createId, String createIp,
                                     Integer accountNo, String startDate, String endDate, String pOrderDate, String type) {

        if("receive".equals(type)) {
            return pOrderMapper.exceptSearchCmpPOrder(pOrderCode, manager, accountNo, startDate, endDate);
        }

        return pOrderMapper.searchPOrder(pOrderCode, manager, createId, createIp, accountNo, state,
                startDate, endDate, pOrderDate);
    }

    @Transactional
    public void insert(POrderInsertDto pOrderInsertDto) {
        pOrderMapper.insert(pOrderInsertDto);

        if (pOrderInsertDto.getPOrderItems().isEmpty()) {
            return;
        }

        AtomicInteger pOrderItemNo = new AtomicInteger();

        for (POrderItemInsertDto pOrderItem : pOrderInsertDto.getPOrderItems()) {
            pOrderItem.setPOrderItemNo(pOrderItemNo.incrementAndGet());
            pOrderItem.setPOrderCode(pOrderInsertDto.getPOrderCode());

            pOrderItemMapper.insert(pOrderItem);
        }
    }

    @Transactional
    public String modify(String pOrderCode, POrderModifyDto pOrderModifyDto) {
        POrder retrievePOrder = retrievePOrder(pOrderCode);

        if (retrievePOrder.getState() != State.WAIT) {
            throw new UnModifiableStateException();
        }

        pOrderMapper.modify(retrievePOrder.getPOrderCode(), pOrderModifyDto);

        return pOrderCode;
    }

    @Transactional
    public void delete(List<String> pOrderCodes) {
        List<POrder> retrievePOrders = getPOrders(pOrderCodes);

        if (retrievePOrders.isEmpty()) {
            throw new NoSuchElementException("해당 발주 내역은 존재하지 않습니다.");
        }

        List<String> waitStatePOrders = retrievePOrders.stream()
                .filter(i -> i.getState() == State.WAIT)
                .map(POrder::getPOrderCode)
                .collect(Collectors.toList());

        if (waitStatePOrders.isEmpty()) {
            throw new UnModifiableStateException();
        }

        pOrderItemMapper.delete(waitStatePOrders);
        pOrderMapper.delete(waitStatePOrders);
    }

    private List<POrder> getPOrders(List<String> porderCode) {
        return pOrderMapper.checkPOrder(porderCode);
    }


    private POrder retrievePOrder(String pOrderCode) {
        return pOrderMapper.retrieve(pOrderCode).orElseThrow(() -> {
            throw new NoSuchElementException("해당 발주 내역은 존재하지 않습니다.");
        });
    }

    public List<POrder> searchRecentPK() {
        return pOrderMapper.searchPOrderPK();
    }
}
