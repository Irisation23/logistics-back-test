package com.douzon.smartlogistics.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class WarehouseStock {

    public final Long warehouseStockNo;
    public final Integer warehouseNo;
    public final String receiveCode;
    public final String receiveItemCode;
    public final String itemCode;
    public final Double count;
    public final String registDate;
    public final String createDate;
    public final String createId;
    public final String createIp;
    public final String modifyDate;
    public final String modifyIp;
    public final String modifyId;

}
