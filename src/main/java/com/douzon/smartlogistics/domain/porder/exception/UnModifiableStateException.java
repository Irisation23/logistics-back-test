package com.douzon.smartlogistics.domain.porder.exception;

public class UnModifiableStateException extends RuntimeException {

    private static final String MESSAGE = "현재 수정 및 삭제 할 수 없는 상태입니다.";

    public UnModifiableStateException() {
        super(MESSAGE);
    }
}
