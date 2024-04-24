package com.douzon.smartlogistics.domain.porderitem.api;

import com.douzon.smartlogistics.domain.entity.POrderItem;
import com.douzon.smartlogistics.domain.porderitem.application.POrderItemService;
import com.douzon.smartlogistics.domain.porderitem.dto.POrderItemInsertDto;
import com.douzon.smartlogistics.domain.porderitem.dto.POrderItemModifyDto;
import com.douzon.smartlogistics.domain.porderitem.dto.POrderItemStateModifyDto;
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
import org.springframework.web.bind.annotation.*;



@Api(tags = "발주물품 관리 API 명세서")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/porder-item")
@Slf4j
public class POrderItemController {

    private final POrderItemService pOrderItemService;

    @Operation(summary = "발주물품 등록",
               description = "발주물품 등록에 알맞은 데이터를 받아 데이터베이스에 삽입합니다.",
               responses = @ApiResponse(responseCode = "201",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = CommonResponse.class))))
    @PostMapping("/insert")
    public ResponseEntity<CommonResponse<Integer>> insert(@RequestBody @Valid @Parameter(description = "발주품목 등록을 위한 "
        + "데이터") POrderItemInsertDto pOrderItemInsertDto) {

        Integer createdPOrderItemNo = pOrderItemService.insert(pOrderItemInsertDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWith(createdPOrderItemNo));
    }

    @Operation(summary = "발주물품 수정",
               description = "발주물품 수정에 알맞은 데이터를 받아 데이터베이스를 수정합니다.",
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = CommonResponse.class))))

    @PatchMapping("/modify")
    public ResponseEntity<CommonResponse<Integer>> modify(
        @RequestParam @Parameter(description = "발주물품 번호") Integer pOrderItemNo,
        @RequestBody @Valid @Parameter(description = "발주물품 수정을 위한 데이터") POrderItemModifyDto pOrderItemModifyDto) {

        pOrderItemService.modify(pOrderItemNo, pOrderItemModifyDto);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWith(pOrderItemNo));
    }
    @Operation(summary = "발주물품 상태 수정",
               description = "발주물품 상태를 알맞은 데이터를 받아 데이터베이스를 수정합니다.",
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = CommonResponse.class))))
    @PatchMapping("/modify/{pOrderItemNo}")
    public ResponseEntity<CommonResponse<Integer>> modifyState(@PathVariable Integer pOrderItemNo,
        @RequestBody @Valid @Parameter(description = "발주물품 상태 수정을 위한 데이터") POrderItemStateModifyDto pOrderItemStateModifyDto) {
        Integer modifiedPOrderItemNo = pOrderItemService.modifyState(pOrderItemNo, pOrderItemStateModifyDto);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWith(modifiedPOrderItemNo));
    }

    @Operation(summary = "발주물품 리스트 조회",
               description = "발주물품 리스트 조회 요청을 처리하고 데이터베이스를 조회해",
               responses = {@ApiResponse(responseCode = "200",
                                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommonResponse.class)
                                         ))})
    @GetMapping("/list")
    public ResponseEntity<CommonResponse<List<POrderItem>>> searchPOrderItemList(
        @RequestParam @Parameter(description = "발주코드") String pOrderCode,
        @RequestParam(required = false, defaultValue = "") @Parameter(description = "요청페이지")  String type) {

        List<POrderItem> pOrderItems = pOrderItemService.searchPOrderItemList(pOrderCode, type);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWith(pOrderItems));
    }

    @Operation(summary = "발주물품 삭제",
               description = "발주물품 삭제에 알맞은 데이터를 받아 데이터베이스의 데이터를 삭제합니다.",
               responses = @ApiResponse(responseCode = "200"))

    @DeleteMapping("/delete")
    public ResponseEntity<CommonResponse<String>> delete(
        @RequestParam @Parameter(description = "발주물품번호") List<Integer> pOrderItemNo,
        @RequestParam @Parameter(description = "발주코드") String pOrderCode) {

        pOrderItemService.delete(pOrderItemNo, pOrderCode);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWithDefaultMessage());
    }

    @Operation(summary = "발주 물품 잔량 조회",
            description = "발주 물품 중 선택된 데이터의 잔량을 조회해 반환합니다.",
            responses = {@ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommonResponse.class)))})
    @GetMapping("/list/remainder")
    public ResponseEntity<CommonResponse<Integer>> searchPOrderRemainder(
            @RequestParam @Valid @Parameter(description = "기준 발주번호") String porderCode,
            @RequestParam @Valid @Parameter(description = "기준 발주순번") Integer porderItemNo
    ){
        int availableCount = pOrderItemService.searchPOrderItemRemainder(porderCode,porderItemNo);;
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(CommonResponse.successWith(availableCount));
    }
}
