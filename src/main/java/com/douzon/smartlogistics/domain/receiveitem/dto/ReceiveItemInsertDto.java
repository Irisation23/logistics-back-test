package com.douzon.smartlogistics.domain.receiveitem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@NoArgsConstructor
@Getter
@Setter
public class ReceiveItemInsertDto {

    @Schema(title = "입고 코드", description = "등록할 입고 코드 입니다.", example = "RV20230726145745750")
    @NotBlank
    @Size(max = 20)
    @JsonProperty(value = "receiveCode")
    private String receiveCode;

    @Schema(title = "입고 순번", description = "등록할 입고 순번 입니다.", example = "1")
    @NotBlank
    @Size(max = 10)
    private Integer receiveItemNo;

    @Schema(title = "발주 코드", description = "등록할 발주 코드 입니다.", example = "PO20230726145745750")
    @NotBlank
    @Size(max = 20)
    private String porderCode;

    @Schema(title = "발주 품목 번호", description = "등록할 발주품목번호 입니다.", example = "9")
    @NotBlank
    private Integer porderItemNo;

    @Schema(title = "물품 코드", description = "등록할 물품 코드 입니다.", example = "4")
    @NotBlank
    @Digits(integer = 10, fraction = 0)
    private Integer itemCode;

    @Schema(title = "입고 수량", description = "등록할 입고 수량 입니다.", example = "3.0")
    @NotBlank
    @JsonProperty(value = "receiveCount")
    private Double receiveCount;

    @Schema(title = "거래처 번호", description = "등록할 거래처 번호입니다.", example = "1005")
    @NotBlank
    @Digits(integer = 10,fraction = 0)
    private Integer accountNo;

    @Schema(title = "창고 분류 번호", description = "적재할 창고의 분류 번호입니다..", example = "5")
    @Digits(integer = 4, fraction = 0)
    @JsonProperty(value = "warehouseNo")
    private Integer warehouseNo;

    private String createIp;
    private String createId;
}
