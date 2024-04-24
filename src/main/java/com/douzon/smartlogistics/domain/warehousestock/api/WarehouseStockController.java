package com.douzon.smartlogistics.domain.warehousestock.api;

import com.douzon.smartlogistics.domain.warehousestock.application.WarehouseStockService;
import com.douzon.smartlogistics.domain.warehousestock.dto.WarehouseStockResponseDto;
import com.douzon.smartlogistics.domain.warehousestock.dto.WarehouseStockSumResponseDto;
import com.douzon.smartlogistics.global.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "창고(재고)조회 api 명세서")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/warehouse-stock")
public class WarehouseStockController {

    private final WarehouseStockService warehouseStockService;

    @Operation(summary = "창고(재고) 리스트 조회",
               description = "창고(재고) 리스트 조회 요청을 처리하고 데이터 베이스를 조회해 리스트로 결과를 반환합니다.",
               responses = {@ApiResponse(responseCode = "200",
                                         content = @Content(mediaType = "application/json",
                                                            schema = @Schema(implementation = CommonResponse.class)))})
//    @GetMapping("/list")
    public ResponseEntity<CommonResponse<List<WarehouseStockResponseDto>>> searchWarehouseStockList(
        @RequestParam(required = false) @Parameter(description = "창고재고번호") Long warehouseStockNo,
        @RequestParam(required = false) @Parameter(description = "창고번호") Integer warehouseNo,
        @RequestParam(required = false) @Parameter(description = "창고이름") String warehouseName,
        @RequestParam(required = false, defaultValue = "") @Parameter(description = "입고코드") String receiveCode,
        @RequestParam(required = false) @Parameter(description = "입고순번") Integer receiveItemNo,
        @RequestParam(required = false) @Parameter(description = "물품번호") Integer itemCode,
        @RequestParam(required = false, defaultValue = "") @Parameter(description = "물품명칭") String itemName,
        @RequestParam(required = false, defaultValue = "") @Parameter(description = "시작일") String startDate,
        @RequestParam(required = false, defaultValue = "") @Parameter(description = "마감일") String endDate
    ) {
        List<WarehouseStockResponseDto> warehouseStockList = warehouseStockService.searchWarehouseStockList(
            warehouseStockNo,
            warehouseNo, receiveCode, receiveItemNo, itemCode, itemName, startDate, endDate, warehouseName);
        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWith(warehouseStockList));
    }

    @Operation(summary = "창고(재고) 리스트 조회",
               description = "창고(재고) 리스트 조회 요청을 처리하고 데이터 베이스를 조회해 리스트로 결과를 반환합니다.",
               responses = {@ApiResponse(responseCode = "200",
                                         content = @Content(mediaType = "application/json",
                                                            schema = @Schema(implementation = CommonResponse.class)))})
    @GetMapping("/list")
    public ResponseEntity<CommonResponse<List<WarehouseStockSumResponseDto>>> searchWarehouseStocks(
        @RequestParam(required = false, defaultValue = "") @Parameter(description = "창고이름") String warehouseName,
        @RequestParam(required = false, defaultValue = "") @Parameter(description = "품목이름") String itemName,
        @RequestParam(required = false, defaultValue = "") @Parameter(description = "담당자이름") String manager
    ) {
        List<WarehouseStockSumResponseDto> warehouseStocks = warehouseStockService.searchWarehouseStocks(warehouseName,
            itemName, manager);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWith(warehouseStocks));
    }
}

