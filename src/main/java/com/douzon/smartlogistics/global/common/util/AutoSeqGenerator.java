package com.douzon.smartlogistics.global.common.util;

import com.douzon.smartlogistics.domain.entity.constant.SeqCode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AutoSeqGenerator {

    private AutoSeqGenerator(){
    }

    public static String generate(SeqCode seqCode) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        return seqCode.name() + formattedDateTime;
    }
}
