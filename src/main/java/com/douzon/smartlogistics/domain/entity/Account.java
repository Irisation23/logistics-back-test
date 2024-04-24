package com.douzon.smartlogistics.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Account {

    private final Long accountNo;
    private final String accountName;
    private final String representative;
    private final String contactNumber;
    private final String businessNumber;
    private final String createDate;
    private final String createIp;
    private final String createId;
    private final String modifyDate;
    private final String modifyIp;
    private final String modifyId;
}
