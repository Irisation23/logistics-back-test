package com.douzon.smartlogistics.domain.porderitem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class POrderItemModifyDto {

    @Schema(title = "발주코드", description = "수정할 발주물품의 발주코드입니다.", example = "")
    @JsonProperty(value = "pOrderCode")
    private String pOrderCode;

    @Schema(title = "품목코드", description = "수정할 발주물품의 품목코드 입니다.", example = "1")
    @Digits(integer = 10, fraction = 0)
    @JsonProperty(value = "itemCode")
    private Integer itemCode;

    @Schema(title = "발주품목수량", description = "수정할 발주물품의 발주품목수량 입니다.", example = "1.5")
    @JsonProperty(value = "pOrderCount")
    private Double pOrderCount;

    @Schema(title = "발주단가", description = "수정할 발주물품의 발주단가 입니다.", example = "1111")
    @JsonProperty(value = "pOrderPrice")
    private Integer pOrderPrice;

    @Schema(title = "발주품목금액", description = "수정할 발주물품의 발주품목금액 입니다.", example = "111")
    @JsonProperty(value = "pOrderItemPrice")
    private Long pOrderItemPrice;

    @Schema(title = "납기일", description = "수정할 발주물품의 납기일 입니다.", example = "2023-08-02")
    @NotBlank
    private String receiveDeadline;
    private String modifyIp;
    private String modifyId;
}
