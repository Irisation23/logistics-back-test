package com.douzon.smartlogistics.domain.warehouse.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class WarehouseModifyDto {

    private String warehouseName;
    private String modifyIp;
    private String modifyId;
}
