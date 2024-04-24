package com.douzon.smartlogistics.domain.receiveitem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import reactor.util.annotation.Nullable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class ReceiveItemModifyDto {
    @Schema(title = "입고 코드", description = "수정할 데이터의 입고 코드 입니다.", example = "RV20230726145745750")
    @NotBlank
    @Size(max = 20)
    private String receiveCode;
    @Schema(title = "입고 순번", description = "수정할 데이터의 입고 순번 입니다.", example = "1")
    @Digits(integer = 10, fraction = 0)
    private Long receiveItemNo;

    @NotBlank
    @Schema(title = "발주코드", description = "수정할 데이터의 발주코드입니다.", example = "PO20230717082741033")
    @JsonProperty(value = "pOrderCode")
    private String pOrderCode;

//    @NotBlank
    @Schema(title = "발주순번", description = "수정할 데이터의 발주순번입니다.", example = "1")
    @JsonProperty(value = "pOrderItemNo")
    private Integer pOrderItemNo;

    @Schema(title = "입품목수량", description = "수정할 입고물품의 입고품목수량 입니다.", example = "1.5")
    @JsonProperty(value = "receiveCount")
    private Double receiveCount;

    @Schema(title = "창고번호", description = "수정할 창고의 번호 입니다.", example = "8")
    @Digits(integer = 4, fraction = 0)
    @JsonProperty(value = "warehouseNo")
    private Integer warehouseNo;

    @Nullable
    private String modifyDate;
    private String modifyIp;
    private String modifyId;


}

