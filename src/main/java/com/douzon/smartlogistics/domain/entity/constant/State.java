package com.douzon.smartlogistics.domain.entity.constant;

import com.fasterxml.jackson.annotation.JsonValue;

public enum State {

    WAIT("준비"),
    ING("진행 중"),
    CMP("완료");

    @JsonValue
    private final String code;

    State(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static State find(String code) {
        for (State customerType : State.values()) {
            if (customerType.code.equals(code)) {
                return customerType;
            }
        }
        return null;
    }
}
