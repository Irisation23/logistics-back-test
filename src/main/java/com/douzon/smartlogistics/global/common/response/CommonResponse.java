package com.douzon.smartlogistics.global.common.response;

import com.douzon.smartlogistics.domain.entity.Item;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommonResponse<T> {

    @Schema(title = "응답 성공 여부", description = "API 요청 시 응답 결과의 성공 반환 여부를 판단하는 flag 값입니다.", example = "true")
    private final boolean success;
    @Schema(title = "응답 결과 데이터", description = "API 요청에 대한 응답 결과 데이터입니다.",
            anyOf = { Item.class })
    private final T data;
    private final ErrorResponse error;

    /**
     * 리턴 타입이 Void 일 때 사용
     */
    public static CommonResponse<String> successWithDefaultMessage() {
        return new CommonResponse<>(true, "processing complete!!", null);
    }

    /**
     * 성공한 응답에 ResponseBody를 담아 줄 때 사용
     */
    public static <T> CommonResponse<T> successWith(T data) {
        return new CommonResponse<>(true, data, null);
    }

    public static <T> CommonResponse<T> error(ErrorResponse error) {
        return new CommonResponse<>(false, null, error);
    }
}
