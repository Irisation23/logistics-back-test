package com.douzon.smartlogistics.domain.porder.dto;

import com.douzon.smartlogistics.domain.porderitem.dto.POrderItemInsertDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.Digits;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class POrderInsertDto {

    @Schema(title = "발주 코드", description = "등록할 발주 코드 입니다.")
    @Nullable
    private String pOrderCode;
    @Schema(title = "발주 날짜", description = "등록할 발주 날짜입니다")
    @JsonProperty(value = "pOrderDate")
    private String pOrderDate;
    @Schema(title = "담당자", description = "등록할 담당자 입니다.", example = "admin")
    private String manager;
    @Schema(title = "생성 IP", description = "등록할 발주의 생성 IP 입니다.", example = "발주 테스트 IP")
    private String createIp;
    @Schema(title = "생성 ID", description = "등록할 발주의 생성 IP 입니다.", example = "발주 테스트 ID")
    private String createId;
    @Schema(title = "거래처 번호", description = "등록할 거래처 번호 입니다." , example = "1")
    @Digits(integer = 10, fraction = 0)
    private Integer accountNo;
    @Schema(title = "발주품목 등록 데이터", description = "등록할 발주 품목 리스트 입니다.")
    @JsonProperty(value = "pOrderItems")
    private List<POrderItemInsertDto> pOrderItems;
}
