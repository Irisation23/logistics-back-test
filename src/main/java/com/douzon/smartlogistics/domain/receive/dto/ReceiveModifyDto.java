package com.douzon.smartlogistics.domain.receive.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Setter
@Getter
public class ReceiveModifyDto {
    @Schema(title = "입고 코드", description = "수정할 데이터의 입고 코드 입니다.", example = "RV20230726145745750")
    @NotBlank
    @Size(max = 20)
    private String receiveCode;

    @Schema(title = "담당자", description = "수정할 데이터의 입고 담당자입니다.")
    private String manager;

    @Schema(title = "입고 날짜", description = "수정할 데이터의 입고 날짜 입니다.")
    private String receiveDate;

    @Schema(title = "수정 IP" , description = "수정할 발주의 수정 IP 입니다.", example = "발주 수정 테스트 IP")
    private String modifyIp;
    @Schema(title = "수정 ID" , description = "수정할 발주의 수정 ID 입니다.", example = "발주 수정 테스트 ID")
    private String modifyId;

}
