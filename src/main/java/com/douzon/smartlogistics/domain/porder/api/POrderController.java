package com.douzon.smartlogistics.domain.porder.api;

import com.douzon.smartlogistics.domain.entity.POrder;
import com.douzon.smartlogistics.domain.entity.constant.State;
import com.douzon.smartlogistics.domain.porder.application.POrderService;
import com.douzon.smartlogistics.domain.porder.dto.POrderInsertDto;
import com.douzon.smartlogistics.domain.porder.dto.POrderModifyDto;
import com.douzon.smartlogistics.global.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "발주관리 API 명세서")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/porder")
@Slf4j
public class POrderController {

    private final POrderService pOrderService;

    @Operation(summary = "발주 리스트 조회",
               description = "발주 리스트 조회 요청을 처리하고 데이터베이스를 조회해 리스트로 결과를 반환합니다.",
               responses = {@ApiResponse(responseCode = "200",
                                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommonResponse.class)
    ))})
    @GetMapping("/list")
    public ResponseEntity<CommonResponse<List<POrder>>> searchPOrderList(
        @RequestParam(required = false, defaultValue = "") @Parameter(description = "발주코드") String pOrderCode,
        @RequestParam(required = false, defaultValue = "") @Parameter(description = "담당자") String manager,
        @RequestParam(required = false, defaultValue = "") @Parameter(description = "상태") State state,
        @RequestParam(required = false, defaultValue = "") @Parameter(description = "거래처번호") Integer accountNo,
        @RequestParam(required = false, defaultValue = "") @Parameter(description = "생성ID") String createId,
        @RequestParam(required = false, defaultValue = "") @Parameter(description = "생성IP") String createIp,
        @RequestParam(required = false, defaultValue = "") @Parameter(description = "조회 시작날짜") String startDate,
        @RequestParam(required = false, defaultValue = "") @Parameter(description = "조회 마감날짜") String endDate,
        @RequestParam(required = false, defaultValue = "") @Parameter(description = "발주 날짜") String pOrderDate,
        @RequestParam(required = false, defaultValue = "") @Parameter(description = "요청페이지")  String type
    ) {
        List<POrder> pOrderList = pOrderService.searchPOrder(pOrderCode, manager, state, createId, createIp, accountNo,
            startDate, endDate, pOrderDate, type);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWith(pOrderList));
    }

    @Operation(summary = "발주 등록",
               description = "발주 등록에 알맞은 데이터를 받아 데이터베이스에 삽입합니다.",
               responses = @ApiResponse(responseCode = "201",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = CommonResponse.class))))
    @PostMapping("/insert")
    public ResponseEntity<CommonResponse<String>> insert(@RequestBody @Valid @Parameter(description = "발주 등록을 위한 데이터") POrderInsertDto pOrderInsertDto) {

        String generatedSeqCode = pOrderService.insert(pOrderInsertDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWith(generatedSeqCode));
    }

    @Operation(summary = "발주 수정",
               description = "발주 수정에 알맞은 데이터를 받아 데이터베이스의 데이터를 수정합니다.",
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = CommonResponse.class))))
    @PatchMapping("/modify")
    public ResponseEntity<CommonResponse<String>> modify(@RequestParam @Parameter(description = "수정할 발주의 코드") String pOrderCode,
        @RequestBody @Valid @Parameter(description = "발주 수정을 위한 데이터") POrderModifyDto pOrderModifyDto) {

        String modifiedPOrderCode = pOrderService.modify(pOrderCode, pOrderModifyDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWith(modifiedPOrderCode));
    }

    @Operation(summary = "발주 삭제",
               description = "발주 삭제에 알맞은 데이터를 받아 데이터베이스의 데이터를 삭제합니다.",
               responses = @ApiResponse(responseCode = "200"))
    @DeleteMapping("/delete")
    public ResponseEntity<CommonResponse<String>> delete(@RequestParam(name = "pOrderCodes") @Parameter(description = "삭제할 발주의 코드") List<String> pOrderCodes) {
        pOrderService.delete(pOrderCodes);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWithDefaultMessage());
    }

    @Operation(summary = "발주 pk번호 찾기",
            description = "발주 pk번호를 가져와 포커싱처리를 합니다.",
            responses = @ApiResponse(responseCode = "200"))
    @GetMapping("/searchRecentPK")
    public ResponseEntity<CommonResponse<List<POrder>>> searchRecentPK() {

       List<POrder> searchRecentPK = pOrderService.searchRecentPK();

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(CommonResponse.successWith(searchRecentPK));
    }
}
