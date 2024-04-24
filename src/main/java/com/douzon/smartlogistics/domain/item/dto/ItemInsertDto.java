package com.douzon.smartlogistics.domain.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ItemInsertDto {

    @Schema(title = "물품 이름", description = "등록할 물품의 이름 입니다.", example = "테스트 물품")
    @NotBlank
    @Size(max = 20)
    private String itemName;
    @Schema(title = "규격", description = "등록할 물품의 규격 입니다.", example = "테스트 규격")
    @NotBlank
    @Size(max = 20)
    private String spec;
    @Schema(title = "단위", description = "등록할 물품의 단위 입니다.", example = "테스트 단위")
    @NotBlank
    @Size(max = 10)
    private String unit;
    @Schema(title = "가격", description = "등록할 물품의 가격 입니다.", example = "10000")
    @Digits(integer = 10, fraction = 0)
    private Integer itemPrice;
    @Schema(title = "생성 IP", description = "등록할 물품의 생성 IP 입니다.", example = "테스트 IP")
    private String createIp;
    @Schema(title = "생성 ID", description = "등록할 물품의 생성 IP 입니다.", example = "테스트 ID")
    private String createId;

}
