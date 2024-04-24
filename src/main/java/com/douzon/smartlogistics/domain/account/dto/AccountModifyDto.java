package com.douzon.smartlogistics.domain.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class AccountModifyDto {
    @Schema(title = "거래처 이름", description = "수정할 거래처의 이름 입니다.", example = "테스트 수정 거래처")
    @NotBlank
    @Size(max = 20)
    private String accountName;
    @Schema(title = "대표자", description = "수정할 대표자 입니다.", example = "테스트 수정 대표자")
    @NotBlank
    @Size(max = 10)
    private String representative;
    @Schema(title = "거래처번호", description = "수정할 거래처번호 입니다.", example = "테스트 수정 거래처번호")
    @Size(max = 30)
    private String contactNumber;
    @Schema(title = "사업자번호", description = "수정할 사업자번호 입니다.", example = "테스트 수정 사업자번호")
    @Size(max = 30)
    private String businessNumber;
    @Schema(title = "수정 IP", description = "수정할 거래처의 수정 IP 입니다.", example = "테스트 수정 IP")
    private String modifyIp;
    @Schema(title = "수정 ID", description = "수정할 거래처의 수정 IP 입니다.", example = "테스트 수정 ID")
    private String modifyId;

}
