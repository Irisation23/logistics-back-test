package com.douzon.smartlogistics.domain.receive.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import reactor.util.annotation.Nullable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RequiredArgsConstructor
@Getter
@Setter
public class ReceiveListDto {
    @Schema(title = "입고 코드", description = "조회할 입고 코드 입니다.")
    @NotBlank
    private String receiveCode;

    @Schema(title = "입고품목번호", description = "조회할 입고 품목번호입니다.")
    @NotBlank
    private String receiveItemNo;

    @Schema(title = "입고날짜", description = "조회할 입고 날짜입니다.")
    @NotBlank
    private String receiveDate;

    @Schema(title = "담당자", description = "조회할 담당자 입니다.", example = "테스트 담당자")
    @Size(max = 15)
    private String manager;

    @Schema(title = "대기수량", description = "조회할 발주품목의 대기 중인 수량 입니다.", example = "3")
    private Integer waitCount;

    @Schema(title = "진행중 수량", description = "조회할 발주품목의 진행 중인 수량 입니다.", example = "3")
    private Integer ingCount;

    @Schema(title = "완료 수량", description = "조회할 발주품목의 완료된 수량 입니다.", example = "3")
    private Integer cmpCount;


}
