package com.douzon.smartlogistics.domain.receiveitem.api;
import com.douzon.smartlogistics.domain.receiveitem.application.ReceiveItemService;
import com.douzon.smartlogistics.domain.receiveitem.dto.ReceiveItemDeleteDto;
import com.douzon.smartlogistics.domain.receiveitem.dto.ReceiveItemInsertDto;
import com.douzon.smartlogistics.domain.receiveitem.dto.ReceiveItemListDto;
import com.douzon.smartlogistics.domain.receiveitem.dto.ReceiveItemModifyDto;
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

@Api(tags = "입고물품 관리 API 명세서")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/receive-item")
public class ReceiveItemController {

    private final ReceiveItemService receiveItemService;

    @Operation(summary = "입고상품 조회",
            description = "입고상품 등록에 알맞은 데이터를 받아 데이터베이스에 삽입합니다.",
            responses = @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class))))
    @GetMapping("/list")
    public ResponseEntity<CommonResponse<List<ReceiveItemListDto>>> searchReceiveItem(
            @RequestParam @Valid @Parameter(description = "입고코드") String receiveCode
    ){
        List<ReceiveItemListDto> receiveItemList = receiveItemService.searchReceiveItem(receiveCode);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(CommonResponse.successWith(receiveItemList));
    }

    @Operation(summary = "입고물품 등록",
            description = "입고물품 등록에 알맞은 데이터를 받아 데이터베이스에 삽입합니다.",
            responses = @ApiResponse(responseCode = "201",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class))))
    @PostMapping("/insert")
    public ResponseEntity<CommonResponse<String>> insertReceiveItem(@RequestBody @Valid @Parameter(description = "입고품목 등록을 위한 데이터") ReceiveItemInsertDto receiveItemInsertDto){
        receiveItemService.insertReceiveItem(receiveItemInsertDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(CommonResponse.successWithDefaultMessage());
    }

    @Operation(summary = "입고물품 삭제",
            description = "입고물품 삭제에 알맞은 데이터를 받아 데이터베이스의 데이터를 삭제합니다.",
            responses = @ApiResponse(responseCode = "200"))
    @DeleteMapping("/delete")
    public ResponseEntity<CommonResponse<String>> deleteReceiveItem(
            @RequestBody @Valid @Parameter(description = "  입고코드 및 순번") List<ReceiveItemDeleteDto> receiveDeleteItems
    ) {
        receiveItemService.deleteReceiveItem(receiveDeleteItems);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(CommonResponse.successWithDefaultMessage());
    }

    @Operation(summary = "입고물품 수정",
            description = "입고물품 수정에 알맞은 데이터를 받아 데이터베이스를 수정합니다.",
            responses = @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class))))
    @PatchMapping("/modify")
    public ResponseEntity<CommonResponse<String>> modifyReceiveItem(
            @RequestBody @Valid @Parameter(description = "입고물품 수정을 위한 데이터") ReceiveItemModifyDto receiveItemModifyDto
    ){
        receiveItemService.modifyReceiveItem(receiveItemModifyDto);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(CommonResponse.successWithDefaultMessage());
    }

}
