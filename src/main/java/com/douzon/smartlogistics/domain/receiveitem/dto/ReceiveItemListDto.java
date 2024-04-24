package com.douzon.smartlogistics.domain.receiveitem.dto;

import com.douzon.smartlogistics.domain.entity.constant.State;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class ReceiveItemListDto {
    @Schema(title = "입고 코드", description = "조회할 입고 코드 입니다.", example = "RV20230726145745750")
    @NotBlank
    @Size(max = 20)
    @JsonProperty(value = "receiveCode")
    private String receiveCode;

    @Schema(title = "입고 순번", description = "조회할 입고 순번 입니다.", example = "1")
    @NotBlank
    @Size(max = 10)
    private Integer receiveItemNo;

    @Schema(title = "발주 코드", description = "조회할 발주 코드 입니다.", example = "PO20230726145745750")
    @NotBlank
    @Size(max = 20)
    private String porderCode;

    @Schema(title = "발주 품목 번호", description = "조회할 발주품목번호 입니다.", example = "9")
    @NotBlank
    private Integer porderItemNo;

    @Schema(title = "발주 품목 진행상태", description = "조회할 발주품목의 진행상태 입니다.", example = "ING")
    @NotBlank
    private State pOrderState;

    @Schema(title = "발주품목수량", description = "조회할 발주물품의 발주품목수량 입니다.", example = "3.0")
    @NotBlank
    private Double pOrderCount;

    @Schema(title = "물품 코드", description = "조회할 물품 코드 입니다.", example = "4")
    @NotBlank
    @Digits(integer = 10, fraction = 0)
    private Integer itemCode;

    @Schema(title = "물품 이름", description = "조회할 물품의 이름 입니다.", example = "테스트 물품")
    @NotBlank
    @Size(max = 20)
    private String itemName;

    @Schema(title = "입고 수량", description = "조회할 입고 수량 입니다.", example = "3.0")
    @NotBlank
    @JsonProperty(value = "receiveCount")
    private Double receiveCount;

    @Schema(title = "거래처 번호", description = "조회할 거래처 번호입니다.", example = "1005")
    @NotBlank
    @Digits(integer = 10,fraction = 0)
    private Integer accountNo;

    @Schema(title = "거래처명", description = "조회할 거래처명 입니다.", example = "테스트 거래처명")
    @NotBlank
    @Size(max = 20)
    private String accountName;

    @Schema(title = "창고번호", description = "조회할 창고 번호 입니다.", example = "1")
    @NotBlank
    private Integer warehouseNo;

    @Schema(title = "창고이름", description = "조회할 창고 이름 입니다.", example = "A-1")
    @NotBlank
    private String warehouseName;

}
