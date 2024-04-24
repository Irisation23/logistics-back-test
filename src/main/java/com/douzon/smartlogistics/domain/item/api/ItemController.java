package com.douzon.smartlogistics.domain.item.api;

import com.douzon.smartlogistics.domain.entity.Item;
import com.douzon.smartlogistics.domain.item.application.ItemService;
import com.douzon.smartlogistics.domain.item.dto.ItemInsertDto;
import com.douzon.smartlogistics.domain.item.dto.ItemModifyDto;
import com.douzon.smartlogistics.global.common.response.CommonResponse;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
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
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "물품관리 API 명세서")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "품목 리스트 조회",
               description = "품목 리스트 조회 요청을 처리하고 데이터 베이스를 조회해 리스트로 결과를 반환합니다.",
               responses = {@ApiResponse(responseCode = "200",
                                         content = @Content(mediaType = "application/json", schema =
                                         @Schema(implementation = CommonResponse.class)))})
    @GetMapping("/list")
    public ResponseEntity<CommonResponse<List<Item>>> searchItemList(
        @RequestParam(required = false) @Parameter(description = "품목코드") Integer itemCode,
        @RequestParam(required = false, defaultValue = "") @Parameter(description = "품목이름") String itemName,
        @RequestParam(required = false, defaultValue = "") @Parameter(description = "생성날짜") String createDate,
        @RequestParam(required = false, defaultValue = "") @Parameter(description = "생성아이디") String createId,
        @RequestParam(required = false) @Parameter(description = "품목가격") Integer itemPrice) {

        List<Item> itemList = itemService.searchItemList(itemCode, itemName, createDate, createId, itemPrice);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWith(itemList));
    }

    @Operation(summary = "품목 등록",
               description = "품목 등록에 알맞은 데이터를 받아 데이터베이스에 삽입합니다.",
               responses = @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json",
                                                                                 schema = @Schema(implementation = CommonResponse.class))))
    @PostMapping("/insert")
    public ResponseEntity<CommonResponse<Integer>> insert(
        @RequestBody @Valid @Parameter(name = "itemInsertDto", description = "물품 등록을 위한 데이터", required = true) ItemInsertDto itemInsertDto) {
        Integer insertedItemCode = itemService.insert(itemInsertDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWith(insertedItemCode));
    }

    @Operation(summary = "물품 수정",
               description = "물품 수정에 알맞은 데이터를 받아 데이터베이스의 데이터를 수정합니다.",
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = CommonResponse.class))))
    @PatchMapping("/modify/{itemCode}")
    public ResponseEntity<CommonResponse<Integer>> modify(
        @PathVariable @Parameter(description = "수정할 물품의 코드") Integer itemCode,
        @RequestBody @Valid @Parameter(description = "물품 수정을 위한 데이터") ItemModifyDto itemModifyDto) {

        Integer modifiedItemCode = itemService.modify(itemCode, itemModifyDto);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWith(modifiedItemCode));
    }

    @Operation(summary = "물품 삭제",
               description = "물품 삭제에 알맞은 데이터를 받아 데이터베이스의 데이터를 삭제합니다.")
    @DeleteMapping("/delete")
    public ResponseEntity<CommonResponse<String>> delete(
        @RequestParam @Parameter(description = "삭제할 물품의 코드") List<Integer> itemCodes) {

        itemService.delete(itemCodes);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWithDefaultMessage());
    }
}

