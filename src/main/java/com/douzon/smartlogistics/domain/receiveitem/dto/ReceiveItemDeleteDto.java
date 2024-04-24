package com.douzon.smartlogistics.domain.receiveitem.dto;

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
public class ReceiveItemDeleteDto {
    @Schema(title = "입고 코드", description = "삭제할 입고 코드 입니다.", example = "RV20230726145745750")
    @NotBlank
    @Size(max = 20)
    private String receiveCode;
    @Schema(title = "입고 순번", description = "삭제할 입고 순번 입니다.", example = "1")
    @NotBlank
    @Digits(integer = 10, fraction = 0)
    private Long receiveItemNo;
}
