package com.douzon.smartlogistics.domain.account.api;

import com.douzon.smartlogistics.domain.account.dto.AccountInsertDto;
import com.douzon.smartlogistics.domain.account.dto.AccountModifyDto;
import com.douzon.smartlogistics.domain.entity.Account;
import com.douzon.smartlogistics.global.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.douzon.smartlogistics.domain.account.application.AccountService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Api(tags = "거래처관리 API 명세서")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "거래처코드 중복 체크",
            description = "멤버 등록 시 아이디 중복을 체크합니다.",
            responses = {@ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json", schema =
                    @Schema(implementation = CommonResponse.class)))})
    @GetMapping("/checkAccountCode/{accountCode}")
    public ResponseEntity<CommonResponse<Boolean>> checkIdDuplication(
            @PathVariable(value = "accountCode") String accountCode) {

        boolean isDuplicate = accountService.checkAccountCode(accountCode);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(CommonResponse.successWith(isDuplicate));
    }

    @Operation(summary = "거래처 리스트 조회",
            description = "거래처 리스트 조회 요청을 처리하고 데이터 베이스를 조회해 리스트로 결과를 반환합니다.",
            responses = {@ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json", schema =
                    @Schema(implementation = CommonResponse.class)))})
    @GetMapping("/list")
    public ResponseEntity<CommonResponse<List<Account>>> searchAccountList(
        @RequestParam(required = false) Integer accountNo,
        @RequestParam(required = false, defaultValue = "") String accountName,
        @RequestParam(required = false, defaultValue = "") String createDate,
        @RequestParam(required = false, defaultValue = "") String createId) {

        List<Account> accoutList = accountService.searchAccoutList(accountNo, accountName, createDate, createId );
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(CommonResponse.successWith(accoutList));
    }

    @Operation(summary = "거래처 등록",
            description = "거래처 등록에 알맞은 데이터를 받아 데이터베이스에 삽입합니다.",
            responses = @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CommonResponse.class))))
    @PostMapping("/insert")
    public ResponseEntity<CommonResponse<AccountInsertDto>> insert(@RequestBody @Valid AccountInsertDto accountInsertDto){
        accountService.insert(accountInsertDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(CommonResponse.successWith(accountInsertDto));
    }

    @Operation(summary = "거래처 수정",
            description = "거래처 수정에 알맞은 데이터를 받아 데이터베이스의 데이터를 수정합니다.",
            responses = @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class))))
    @PatchMapping("/modify")
    public ResponseEntity<CommonResponse<String>> modify(@RequestParam Integer accountNo,
                                                         @RequestBody @Valid AccountModifyDto accountModifyDto) {

        accountService.modify(accountNo, accountModifyDto);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWithDefaultMessage());
    }
    
    @Operation(summary = "거래처 삭제",
            description = "거래처 삭제에 알맞은 데이터를 받아 데이터베이스의 데이터를 삭제합니다.",
            responses = {@ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json", schema =
            @Schema(implementation = CommonResponse.class)))})

    @DeleteMapping("/delete")
    public ResponseEntity<CommonResponse<String>> delete(@RequestBody List<Integer> accountNos) {
        accountService.delete(accountNos);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(CommonResponse.successWithDefaultMessage());
    }
}
