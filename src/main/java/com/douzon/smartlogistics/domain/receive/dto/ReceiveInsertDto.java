package com.douzon.smartlogistics.domain.receive.dto;

import com.douzon.smartlogistics.domain.receiveitem.dto.ReceiveItemInsertDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import reactor.util.annotation.Nullable;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class ReceiveInsertDto {
    @Schema(title = "입고 코드", description = "등록할 입고 코드 입니다.")
    @Nullable
    private String receiveCode;
    @Schema(title = "입고 날짜", description = "등록할 입고 날짜 입니다.")
    @Nullable
    private String receiveDate;
    @Schema(title = "담당자", description = "등록하는 입고 담당자입니다.")
    private String manager;
    @Schema(title = "생성 IP", description = "등록할 발주의 생성 IP 입니다.", example = "발주 테스트 IP")
    private String createIp;
    @Schema(title = "생성 ID", description = "등록할 발주의 생성 IP 입니다.", example = "발주 테스트 ID")
    private String createId;
    @Schema(title = "입고품목 등록 데이터", description = "등록할 입고 품목 리스트 입니다.")
    @JsonProperty(value = "receiveItems")
    private List<ReceiveItemInsertDto> receiveItems;
}
