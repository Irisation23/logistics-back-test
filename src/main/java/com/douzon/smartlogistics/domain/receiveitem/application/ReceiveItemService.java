package com.douzon.smartlogistics.domain.receiveitem.application;

import com.douzon.smartlogistics.domain.receiveitem.dao.ReceiveItemDao;
import com.douzon.smartlogistics.domain.receiveitem.dto.ReceiveItemDeleteDto;
import com.douzon.smartlogistics.domain.receiveitem.dto.ReceiveItemInsertDto;
import com.douzon.smartlogistics.domain.receiveitem.dto.ReceiveItemListDto;
import com.douzon.smartlogistics.domain.receiveitem.dto.ReceiveItemModifyDto;
import com.douzon.smartlogistics.domain.warehouse.dao.WarehouseDao;
import com.douzon.smartlogistics.domain.warehousestock.dao.WarehouseStockDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiveItemService {

    private final ReceiveItemDao receiveItemDao;
    private final WarehouseStockDao warehouseStockDao;

    @Transactional
    public void deleteReceiveItem(List<ReceiveItemDeleteDto> receiveItemDeleteDto) {
        for (ReceiveItemDeleteDto item : receiveItemDeleteDto) {
            String receiveCode = item.getReceiveCode();
            Long receiveItemNo = item.getReceiveItemNo();
            receiveItemDao.deleteReceiveItem(receiveCode, receiveItemNo);
            warehouseStockDao.deleteReceiveItemWarehouseStock(receiveCode, receiveItemNo);
        }
    }

    @Transactional
    public void modifyReceiveItem(ReceiveItemModifyDto receiveItemModifyDto) {
        receiveItemDao.modifyReceiveItem(receiveItemModifyDto);
    }

    @Transactional
    public void insertReceiveItem(ReceiveItemInsertDto receiveItemInsertDto) {
        receiveItemDao.insertReceiveItem(receiveItemInsertDto);
    }

    public List<ReceiveItemListDto> searchReceiveItem(String receiveCode) {
        return receiveItemDao.searchReceiveItem(receiveCode);
    }
}
