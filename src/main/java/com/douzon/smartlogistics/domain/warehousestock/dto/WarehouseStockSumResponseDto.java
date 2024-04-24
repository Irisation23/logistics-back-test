package com.douzon.smartlogistics.domain.warehousestock.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class WarehouseStockSumResponseDto {

    private Integer warehouseNo;
    private String warehouseName;
    private Integer itemCode;
    private String itemName;
    private Integer totalCount;
    private String manager;
}
