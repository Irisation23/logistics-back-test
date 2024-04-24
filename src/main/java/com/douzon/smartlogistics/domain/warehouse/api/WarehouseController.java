package com.douzon.smartlogistics.domain.warehouse.api;


import com.douzon.smartlogistics.domain.entity.Warehouse;
import com.douzon.smartlogistics.domain.warehouse.application.WarehouseService;
import com.douzon.smartlogistics.domain.warehouse.dto.WarehouseInsertDto;
import com.douzon.smartlogistics.domain.warehouse.dto.WarehouseModifyDto;
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
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "창고 api 명세서")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @Operation(summary = "창고명 조회",
               description = "창고 조회 요청을 처리하고 데이터 베이스를 조회해 리스트로 결과를 반환합니다.",
               responses = {@ApiResponse(responseCode = "200",
                                         content = @Content(mediaType = "application/json", schema =
                                         @Schema(implementation = CommonResponse.class)))})
    @GetMapping("/list")
    public ResponseEntity<CommonResponse<List<Warehouse>>> searchWarehouseList(
        @RequestParam(required = false, defaultValue = "") @Parameter(description = "창고번호") Integer warehouseNo,
        @RequestParam(required = false, defaultValue = "") @Parameter(description = "창고이름") String warehouseName
    ) {
        List<Warehouse> warehouseSectionList = warehouseService.warehouseSectionList(warehouseNo, warehouseName);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWith(warehouseSectionList));
    }

    @Operation(summary = "창고등록",
               description = "창고등록에 알맞은 데이터를 받아 데이터베이스에 삽입합니다.",
               responses = @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json",
                                                                                 schema = @Schema(implementation = CommonResponse.class))))
    @PostMapping("/insert")
    public ResponseEntity<CommonResponse<String>> insert(
        @RequestBody @Parameter(description = "창고구역추가") WarehouseInsertDto warehouseInsertDto
    ) {

        warehouseService.insert(warehouseInsertDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWithDefaultMessage());
    }

    @Operation(summary = "창고수정",
               description = "창고수정에 알맞은 데이터를 받아 데이터베이스의 데이터를 수정합니다.",
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = CommonResponse.class))))
    @PatchMapping("/modify/{warehouseNo}")
    public ResponseEntity<CommonResponse<String>> modify(@PathVariable Integer warehouseNo,
        @RequestBody(required = false) @Parameter(description = "창고수정을 위한 데이터") WarehouseModifyDto warehouseModifyDto
        ) {

        warehouseService.modify(warehouseNo, warehouseModifyDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWithDefaultMessage());
    }

    @Operation(summary = "창고삭제",
               description = "창고삭제에 알맞은 데이터를 받아 데이터베이스의 데이터를 삭제합니다.")
    @DeleteMapping("/delete/{warehouseNo}")
    public ResponseEntity<CommonResponse<String>> delete(@PathVariable Integer warehouseNo) {

        warehouseService.delete(warehouseNo);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWithDefaultMessage());
    }
}
