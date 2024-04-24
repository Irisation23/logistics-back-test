package com.douzon.smartlogistics.domain.porder.application;

import com.douzon.smartlogistics.domain.entity.POrder;
import com.douzon.smartlogistics.domain.entity.constant.SeqCode;
import com.douzon.smartlogistics.domain.entity.constant.State;
import com.douzon.smartlogistics.domain.porder.dao.POrderDao;
import com.douzon.smartlogistics.domain.porder.dto.POrderInsertDto;
import com.douzon.smartlogistics.domain.porder.dto.POrderModifyDto;
import com.douzon.smartlogistics.domain.porderitem.dto.POrderItemInsertDto;
import com.douzon.smartlogistics.global.common.util.AutoSeqGenerator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class POrderService {

    private final POrderDao pOrderDao;
    public List<POrder> searchPOrder(String pOrderCode, String manager, State state, String createId, String createIp,
        Integer accountNo, String startDate, String endDate, String pOrderDate, String type) {

        return pOrderDao.searchPOrder(pOrderCode,manager, state, createId, createIp, accountNo, startDate, endDate,
            pOrderDate, type);
    }

    @Transactional
    public String insert(POrderInsertDto pOrderInsertDto) {
        String generatedSeqCode = AutoSeqGenerator.generate(SeqCode.PO);

        pOrderInsertDto.setPOrderCode(generatedSeqCode);

        List<POrderItemInsertDto> pOrderItems = pOrderInsertDto.getPOrderItems();

        for (POrderItemInsertDto pOrderItem : pOrderItems) {
            pOrderItem.setPOrderCode(generatedSeqCode);
        }

        pOrderDao.insert(pOrderInsertDto);

        return generatedSeqCode;
    }

    @Transactional
    public String modify(String pOrderCode, POrderModifyDto pOrderModifyDto) {
        return pOrderDao.modify(pOrderCode, pOrderModifyDto);
    }

    public void delete(List<String> pOrderCodes) {
        pOrderDao.delete(pOrderCodes);
    }

    public List<POrder> searchRecentPK(){
        return pOrderDao.searchRecentPK();
    }
}
