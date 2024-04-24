package com.douzon.smartlogistics.domain.entity;

import com.douzon.smartlogistics.domain.entity.constant.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class POrder {

    private String pOrderCode;
    private String pOrderDate;
    private String manager;
    private Integer accountNo;
    private State state;
    private String createDate;
    private String createIp;
    private String createId;
    private String modifyDate;
    private String modifyIp;
    private String modifyId;
}