package com.douzon.smartlogistics.global.common.exception.advice;

import com.douzon.smartlogistics.global.common.exception.auth.AuthException;
import com.douzon.smartlogistics.global.common.exception.auth.ForbiddenException;
import com.douzon.smartlogistics.global.common.response.CommonResponse;
import com.douzon.smartlogistics.global.common.response.ErrorResponse;
import com.douzon.smartlogistics.global.common.response.ValidationBindingResultErrorResponse;
import java.net.UnknownHostException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@Order
@RestControllerAdvice
public class GlobalControllerAdvice {


    /**
     * 자원을 찾지 못했을 때 발생하는 예외를 처리합니다.
     *
     * @param e - 발생한 예외
     * @return - 404 Not Found
     */
    @ExceptionHandler({
        NoHandlerFoundException.class
    })
    public ResponseEntity<CommonResponse<String>> notFoundExceptionHandle(final NoHandlerFoundException e) {
        log.error(e.toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.error(new ErrorResponse("요청한 path에 알맞는 Handler를 찾지 못했습니다.")));
    }

    /**
     * 유효성 검사 Exception 에 대해 관리하는 Handler 입니다.
     *
     * @param ex - 에러 정보를 담은 Exception 입니다.
     * @return 해당 에러의 에러메세지를 반환합니다.
     * @since 1.0.0
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<String>> handleNotValidException(
        final MethodArgumentNotValidException ex) {

        log.error("Data Validation Error 발생했습니다.", ex);
        BindingResult bindingResult = ex.getBindingResult();

        ValidationBindingResultErrorResponse responseBody = new ValidationBindingResultErrorResponse();

        try {
            if (bindingResult.hasErrors()) {
                String reason = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
                String at = bindingResult.getObjectName();
                String field = Objects.requireNonNull(bindingResult.getFieldError()).getField();
                String notValidInput =
                    (String) Objects.requireNonNull(bindingResult.getFieldError()).getRejectedValue();

                responseBody.setReason(reason);
                responseBody.setAt(at);
                responseBody.setField(field);
                responseBody.setNotValidInput(notValidInput);
            }
        } catch (NullPointerException e) {
            log.warn("Field Error Null");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.error(new ErrorResponse(responseBody.toString())));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AuthException.class, UnknownHostException.class})
    public ResponseEntity<CommonResponse<String>> authException(AuthException e) {
        log.error("Auth Exception: {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.error(new ErrorResponse(e.getMessage())));
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<CommonResponse<String>> forbiddenException(ForbiddenException e) {
        log.error("Forbidden Exception: {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.error(new ErrorResponse(e.getMessage())));
    }

    /**
     * 전체 Exception 에 대해 관리하는 Handler 입니다.
     *
     * @param ex - 에러 정보를 담은 Exception 입니다.
     * @return 해당 에러의 에러메세지를 반환합니다.
     * @since 1.0.0
     */
    @Order
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<String>> handleException(final Exception ex) {
        log.error("", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.error(new ErrorResponse("잘못된 요청입니다.")));
    }
}
