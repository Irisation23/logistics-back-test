package com.douzon.smartlogistics.domain.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ItemModifyDto {

    @Schema(title = "물품 이름", description = "수정할 물품의 이름 입니다.", example = "테스트 수정 물품")
    @NotBlank
    @Size(max = 20)
    private String itemName;
    @Schema(title = "규격", description = "수정할 물품의 규격 입니다.", example = "테스트 수정 규격")
    @NotBlank
    @Size(max = 20)
    private String spec;
    @Schema(title = "단위", description = "수정할 물품의 단위 입니다.", example = "테스트 수정 단위")
    @NotBlank
    @Size(max = 10)
    private String unit;
    @Schema(title = "가격", description = "수정할 물품의 가격 입니다.", example = "11111")
    @Digits(integer = 10, fraction = 0)
    private Integer itemPrice;
    @Schema(title = "수정 IP", description = "수정할 물품의 수정 IP 입니다.", example = "테스트 수정 IP")
    private String modifyIp;
    @Schema(title = "수정 ID", description = "수정할 물품의 수정 IP 입니다.", example = "테스트 수정 ID")
    private String modifyId;
}
