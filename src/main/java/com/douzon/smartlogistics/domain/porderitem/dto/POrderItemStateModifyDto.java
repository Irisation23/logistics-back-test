package com.douzon.smartlogistics.domain.porderitem.dto;

import com.douzon.smartlogistics.domain.entity.constant.State;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class POrderItemStateModifyDto {

    @NotBlank
    @Schema(title = "발주코드", description = "수정할 발주코드입니다.", example = "PO20230717082741033")
    @JsonProperty(value = "pOrderCode")
    private String pOrderCode;

    @Schema(title = "발주진행상태", description = "수정할 발주진행상태입니다.", example = "ING")
    @JsonProperty(value = "pOrderState")
    private State pOrderState;
    private String modifyIp;
    private String modifyId;
}
