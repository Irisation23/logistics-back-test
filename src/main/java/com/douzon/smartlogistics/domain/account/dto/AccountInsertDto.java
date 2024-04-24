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
public class AccountInsertDto {
    @Schema(title = "거래처명", description = "거래처명 입니다.", example = "테스트 거래처명")
    @NotBlank
    @Size(max = 20)
    private String accountName;
    @Schema(title = "대표자", description = "대표자 입니다.", example = "테스트 대표자")
    @NotBlank
    @Size(max = 10)
    private String representative;
    @Schema(title = "거래처번호", description = "거래처번호 입니다.", example = "테스트 거래처번호")
    @Size(max = 30)
    private String contactNumber;
    @Schema(title = "사업자번호", description = "사업자번호 입니다.", example = "테스트 사업자번호")
    @Size(max = 30)
    private String businessNumber;
    @Schema(title = "생성 IP", description = "등록할 물품의 생성 IP 입니다.", example = "테스트 IP")
    private String createIp;
    @Schema(title = "생성 ID", description = "등록할 물품의 생성 IP 입니다.", example = "테스트 ID")
    private String createId;

}
