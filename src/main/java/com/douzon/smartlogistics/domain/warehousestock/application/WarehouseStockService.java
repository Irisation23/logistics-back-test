package com.douzon.smartlogistics.domain.warehousestock.application;

import com.douzon.smartlogistics.domain.warehousestock.dao.WarehouseStockDao;
import com.douzon.smartlogistics.domain.warehousestock.dto.WarehouseStockResponseDto;
import com.douzon.smartlogistics.domain.warehousestock.dto.WarehouseStockSumResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseStockService {

    private final WarehouseStockDao warehouseStockDao;
    public List<WarehouseStockResponseDto> searchWarehouseStockList(Long warehouseStockNo, Integer warehouseNo,
                                                                    String receiveCode, Integer receiveItemNo, Integer itemCode, String itemName, String startDate,
                                                                    String endDate, String warehouseName) {

        return warehouseStockDao.searchWarehouseStockList(warehouseStockNo, warehouseNo, receiveCode, receiveItemNo,
            itemCode, itemName, startDate, endDate, warehouseName);
    }

    public List<WarehouseStockSumResponseDto> searchWarehouseStocks(String warehouseName, String itemName, String manager) {
        return warehouseStockDao.searchWarehouseStocks(warehouseName, itemName, manager);
    }
}
