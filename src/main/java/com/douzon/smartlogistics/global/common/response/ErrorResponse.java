package com.douzon.smartlogistics.global.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ErrorResponse {

    @Schema(required = true, title = "오류 메시지", description = "API 요청 도중 오류 발생 시 어떤 오류인지에 대한 메시지가 담겨있습니다.")
    private final String message;
}
