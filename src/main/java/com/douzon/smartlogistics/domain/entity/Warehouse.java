package com.douzon.smartlogistics.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Warehouse {

    private Integer warehouseNo;
    private String warehouseName;
    private String createDate;
    private String createIp;
    private String createId;
    private String modifyDate;
    private String modifyIp;
    private String modifyId;
}
