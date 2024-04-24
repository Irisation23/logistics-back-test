package com.douzon.smartlogistics.domain.warehousestock.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class WarehouseStockResponseDto {

    public Long warehouseStockNo;
    public Integer warehouseNo;
    public String receiveCode;
    public String receiveItemCode;
    public String itemCode;
    public Double count;
    public String registDate;
    public String createDate;
    public String createId;
    public String createIp;
    public String modifyDate;
    public String modifyIp;
    public String modifyId;
    public String itemName;
    public String warehouseName;
}
