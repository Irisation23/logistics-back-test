package com.douzon.smartlogistics.domain.porder.dto;

import com.douzon.smartlogistics.domain.entity.constant.State;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class POrderModifyDto {

    @Schema(title = "수정 담당자", description = "수정할 담당자입니다.", example = "수정Id")
    private String manager;
    @Schema(title = "수정 IP" , description = "수정할 발주의 수정 IP 입니다.", example = "발주 수정 테스트 IP")
    private String modifyIp;
    @Schema(title = "수정 ID" , description = "수정할 발주의 수정 ID 입니다.", example = "발주 수정 테스트 ID")
    private String modifyId;
    @Schema(title = "거래처 번호", description = "수정할 발주의 거래처 번호입니다.", example = "1")
    private Integer accountNo;
}
