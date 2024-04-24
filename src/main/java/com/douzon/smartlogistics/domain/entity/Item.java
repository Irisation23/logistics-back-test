package com.douzon.smartlogistics.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Item {

    private final Integer itemCode;
    private final String itemName;
    private final String spec;
    private final String unit;
    private final Integer itemPrice;
    private final String createDate;
    private final String createIp;
    private final String createId;
    private final String modifyDate;
    private final String modifyIp;
    private final String modifyId;
}
