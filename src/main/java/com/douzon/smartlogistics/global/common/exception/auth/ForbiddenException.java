package com.douzon.smartlogistics.global.common.exception.auth;

public class ForbiddenException extends RuntimeException {

    private static final String MESSAGE = "권한이 없습니다.";
    public ForbiddenException() {
        super(MESSAGE);
    }
}
