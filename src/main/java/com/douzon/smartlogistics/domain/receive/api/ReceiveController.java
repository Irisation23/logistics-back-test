package com.douzon.smartlogistics.domain.receive.api;

import com.douzon.smartlogistics.domain.receive.application.ReceiveService;
import com.douzon.smartlogistics.domain.receive.dto.ReceiveInsertDto;
import com.douzon.smartlogistics.domain.receive.dto.ReceiveListDto;
import com.douzon.smartlogistics.domain.receive.dto.ReceiveModifyDto;
import com.douzon.smartlogistics.global.common.aop.annotation.TimeOut;
import com.douzon.smartlogistics.global.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "입고관리 API 명세서")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/receive")
public class ReceiveController {

    private final ReceiveService receiveService;

    @Operation(summary = "입고 리스트 조회",
            description = "입고 리스트 조회 요청을 처리하고 데이터베이스를 조회해 리스트로 결과를 반환합니다.",
            responses = {@ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommonResponse.class)
                    ))})
    @TimeOut
    @GetMapping("/list")
    public ResponseEntity<CommonResponse<List<ReceiveListDto>>> findReceive(
            @RequestParam(required = false, defaultValue = "") @Parameter(description = "입고코드") String receiveCode,
            @RequestParam(required = false, defaultValue = "") @Parameter(description = "담당자") String manager,
            @RequestParam(required = false, defaultValue = "") @Parameter(description = "조회 시작날짜") String startDate,
            @RequestParam(required = false, defaultValue = "") @Parameter(description = "조회 마감날짜") String endDate,
            @RequestParam(required = false, defaultValue = "") @Parameter(description = "작성자 ip") String createIp,
            @RequestParam(required = false, defaultValue = "") @Parameter(description = "작성자 id") String createId
    ) {
        List<ReceiveListDto> receiveList = receiveService.findReceive(
                receiveCode, manager, createIp, createId, startDate, endDate);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(CommonResponse.successWith(receiveList));
    }
    @Operation(summary = "입고 등록",
            description = "입고 등록에 알맞은 데이터를 받아 데이터베이스에 삽입합니다.",
            responses = @ApiResponse(responseCode = "201",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class))))
    @PostMapping("/insert")
    public ResponseEntity<CommonResponse<String>> insertReceive(@RequestBody @Valid @Parameter(description = "입고 등록을 위한 데이터") ReceiveInsertDto receiveInsertDto) {
        String receiveCode = receiveService.insertReceive(receiveInsertDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(CommonResponse.successWith(receiveCode));
    }

    @Operation(summary = "입고 삭제",
            description = "입고 삭제에 알맞은 데이터를 받아 데이터베이스의 데이터를 삭제합니다.",
            responses = @ApiResponse(responseCode = "200"))
    @DeleteMapping("/delete")
    public ResponseEntity<CommonResponse<String>> deleteReceive(@RequestBody @Valid @Parameter(description = "삭제할 입고의 코드") List<String> receiveCodes) {
        receiveService.deleteReceive(receiveCodes);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(CommonResponse.successWithDefaultMessage());
    }


    @Operation(summary = "입고 수정",
            description = "입고 수정에 알맞은 데이터를 받아 데이터베이스의 데이터를 수정합니다.",
            responses = @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class))))
    @PatchMapping("/modify")
    public ResponseEntity<CommonResponse<String>> modifyReceive(
            @RequestBody @Valid @Parameter(description = "입고 수정을 위한 데이터") ReceiveModifyDto receiveModifyDto
    ){
        String receiveCode = receiveService.modifyReceive(receiveModifyDto);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(CommonResponse.successWith(receiveCode));
    }
}
