package com.douzon.smartlogistics.domain.warehousestock.dao.mapper;

import com.douzon.smartlogistics.domain.receiveitem.dto.ReceiveItemInsertDto;
import com.douzon.smartlogistics.domain.receiveitem.dto.ReceiveItemModifyDto;
import com.douzon.smartlogistics.domain.warehousestock.dto.WarehouseStockResponseDto;
import com.douzon.smartlogistics.domain.warehousestock.dto.WarehouseStockSumResponseDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface WarehouseStockMapper {

    @Transactional
    void insertWarehouseStock(
            @Param("receiveItem") ReceiveItemInsertDto receiveItem,
            @Param("receiveDate") String receiveDate
    );

    @Transactional
    void modifyWarehouseStock (
        @Param("ReceiveItem") ReceiveItemModifyDto receiveItemModifyDto
    );

    List<WarehouseStockResponseDto> searchWarehouseStockList(
        @Param("warehouseStockNo") Long warehouseStockNo,
        @Param("warehouseNo") Integer warehouseNo,
        @Param("receiveCode") String receiveCode,
        @Param("receiveItemNo") Integer receiveItemNo,
        @Param("itemCode") Integer itemCode,
        @Param("itemName") String itemName,
        @Param("startDate") String startDate,
        @Param("endDate") String endDate,
        @Param("warehouseName") String warehouseName);

    List<WarehouseStockSumResponseDto> searchWarehouseStocks(
        @Param("warehouseName") String warehouseName,
        @Param("itemName") String itemName,
        @Param("manager") String manager);
    @Transactional
    void deleteReceiveItemWarehouseStock(String receiveCode, Long receiveItemNo);
}
