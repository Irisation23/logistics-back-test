package com.douzon.smartlogistics.domain.entity;

import com.douzon.smartlogistics.domain.entity.constant.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class POrderItem {

    private Integer pOrderItemNo;
    private String pOrderCode;
    private Integer itemCode;
    private Double pOrderCount;
    private Integer pOrderPrice;
    private Long pOrderItemPrice;
    private State pOrderState;
    private String receiveDeadline;
    private String createIp;
    private String createId;
    private String modifyDate;
    private String modifyIp;
    private String modifyId;
    private String itemName;
}
