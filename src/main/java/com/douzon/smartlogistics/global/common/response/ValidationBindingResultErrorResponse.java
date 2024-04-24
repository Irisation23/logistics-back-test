package com.douzon.smartlogistics.global.common.response;

import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class ValidationBindingResultErrorResponse {

    private String reason;
    private String at;
    private String field;
    private String notValidInput;
}
