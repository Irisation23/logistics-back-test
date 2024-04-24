package com.douzon.smartlogistics.global.common.converter;

import com.douzon.smartlogistics.domain.entity.constant.State;
import org.springframework.core.convert.converter.Converter;

/**
 * STATE 타입의 쿼리 스트링 wait -> WAIT || Complete -> COMPLETE 로 자동 매핑하기 위한 컨버터
 */
public class StringToStateConverter implements Converter<String, State> {

    @Override
    public State convert(String source) {
        return State.valueOf(source.toUpperCase());
    }
}
