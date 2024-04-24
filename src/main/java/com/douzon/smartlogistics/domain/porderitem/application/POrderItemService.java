package com.douzon.smartlogistics.domain.porderitem.application;

import com.douzon.smartlogistics.domain.entity.POrderItem;
import com.douzon.smartlogistics.domain.entity.constant.State;
import com.douzon.smartlogistics.domain.porderitem.dao.POrderItemDao;
import com.douzon.smartlogistics.domain.porderitem.dto.POrderItemInsertDto;
import com.douzon.smartlogistics.domain.porderitem.dto.POrderItemModifyDto;
import com.douzon.smartlogistics.domain.porderitem.dto.POrderItemStateModifyDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class POrderItemService {

    private final POrderItemDao pOrderItemDao;

    @Transactional
    public Integer insert(POrderItemInsertDto pOrderItemInsertDto) {
        pOrderItemDao.insert(pOrderItemInsertDto);
        return pOrderItemInsertDto.getPOrderItemNo();
    }

    @Transactional
    public void modify(Integer pOrderItemNo, POrderItemModifyDto pOrderItemModifyDto) {
        pOrderItemDao.modify(pOrderItemNo, pOrderItemModifyDto);
    }

    public List<POrderItem> searchPOrderItemList(String pOrderCode, String type) {
        if("receive".equals(type)) {
            return pOrderItemDao.exceptSearchCmpPorderItemList(pOrderCode);
        }
        return pOrderItemDao.searchPOrderItemList(pOrderCode);
    }

    @Transactional
    public void delete(List<Integer> pOrderItemNo, String pOrderCode) {
        pOrderItemDao.delete(pOrderItemNo, pOrderCode);
    }

    @Transactional
    public Integer modifyState(Integer pOrderItemNo, POrderItemStateModifyDto pOrderItemStateModifyDto) {
        if (pOrderItemStateModifyDto.getPOrderState() == State.ING) {
            return pOrderItemDao.modifyStateToIng(pOrderItemNo, pOrderItemStateModifyDto);
        }

        return pOrderItemDao.modifyStateToCmp(pOrderItemNo, pOrderItemStateModifyDto);
    }


    public int searchPOrderItemRemainder(String porderCode, Integer porderItemNo) {
        return pOrderItemDao.searchPOrderItemRemainder(porderCode,porderItemNo);
    }
}
