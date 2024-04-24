package com.douzon.smartlogistics.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

public class MainPage {
    private String warehouseName;
    private int totalCount;

    private String yearMonth;
    private int totalOrders;
    private int totalReceives;

    private int waitCount;
    private int ingCount;
    private int cmpCount;

}
