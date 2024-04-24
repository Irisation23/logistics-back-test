package com.douzon.smartlogistics.domain.member.api;

import com.douzon.smartlogistics.domain.entity.Member;
import com.douzon.smartlogistics.domain.member.application.MemberService;
import com.douzon.smartlogistics.domain.member.dto.LoginDto;
import com.douzon.smartlogistics.global.common.exception.auth.Auth;
import com.douzon.smartlogistics.global.common.response.CommonResponse;
import com.douzon.smartlogistics.global.common.response.ErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Api(tags = "회원관리 API 명세서")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "아이디 중복 체크",
               description = "멤버 등록 시 아이디 중복을 체크합니다.",
               responses = {@ApiResponse(responseCode = "200",
                                         content = @Content(mediaType = "application/json", schema =
                                         @Schema(implementation = CommonResponse.class)))})
    @GetMapping("/checkId/{id}")
    public ResponseEntity<CommonResponse<Boolean>> checkIdDuplication(
        @PathVariable(value = "id") String memberId) {

        boolean isDuplicate = memberService.checkId(memberId);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWith(isDuplicate));
    }

    @Operation(summary = "로그인",
               description = "로그인 요청을 처리하고 데이터 베이스를 조회해 결과를 반환합니다.",
               responses = {@ApiResponse(responseCode = "200",
                                         content = @Content(mediaType = "application/json", schema =
                                         @Schema(implementation = CommonResponse.class)))})
    @PostMapping("/login")
    public ResponseEntity<CommonResponse<Void>> login(@RequestBody LoginDto logintDto, HttpSession session)
        throws UnknownHostException {

        Member member = memberService.memberLogin(logintDto);
        if (member != null) {
            HashMap<String, Object> paramsMap = new HashMap<String, Object>();
            try {
                InetAddress localhost = InetAddress.getLocalHost();
                String ipAddress = localhost.getHostAddress();
                paramsMap.put("ipAddress", ipAddress);
                paramsMap.put("memberNo", member.getMemberNo());

                memberService.saveIpAddress(paramsMap);
                session.setAttribute("session", member.getMemberNo());

                return ResponseEntity.status(HttpStatus.FOUND)
                                     .header("Location", "/api/member/session")
                                     .build();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.error(new ErrorResponse("로그인에 실패하였습니다.")));
    }

    @Operation(summary = "로그아웃",
               description = "로그아웃 요청을 처리하고 데이터 베이스를 조회해 결과를 반환합니다.",
               responses = {@ApiResponse(responseCode = "200",
                                         content = @Content(mediaType = "application/json", schema =
                                         @Schema(implementation = CommonResponse.class)))})
    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<String>> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWith(" Logout successful!!."));
    }


    @Auth
    @Operation(summary = "멤버 리스트 조회",
               description = "멤버 리스트 조회 요청을 처리하고 데이터 베이스를 조회해 리스트로 결과를 반환합니다.",
               responses = {@ApiResponse(responseCode = "200",
                                         content = @Content(mediaType = "application/json", schema =
                                         @Schema(implementation = CommonResponse.class)))})
    @GetMapping("/list")
    public ResponseEntity<CommonResponse<List<Member>>> searchMemberList(
        @RequestParam(required = false) Integer memberNo,
        @RequestParam(required = false, defaultValue = "") String memberId,
        @RequestParam(required = false, defaultValue = "") String createDate) {

        List<Member> memberList = memberService.searchMemberList(memberNo, memberId, createDate);
        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWith(memberList));
    }

    @Auth
    @Operation(summary = "멤버 상세 정보 조회",
               description = "멤버의 상세 정보를 조회합니다.",
               responses = {@ApiResponse(responseCode = "200",
                                         content = @Content(mediaType = "application/json", schema =
                                         @Schema(implementation = CommonResponse.class)))})
    @GetMapping("/detail/{memberNo}")
    public ResponseEntity<CommonResponse<Member>> getMemberDetail(
        @PathVariable(value = "memberNo") Long memberNo) {
        Member memberDetail = memberService.searchMember(memberNo); // 상세 정보 조회 서비스 메서드 호출
        if (memberDetail != null) {
            return ResponseEntity.status(HttpStatus.OK)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(CommonResponse.successWith(memberDetail));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(CommonResponse.error(new ErrorResponse("멤버를 찾을 수 없습니다.")));
        }
    }


    @Auth
    @Operation(summary = "멤버 등록",
               description = "멤버 등록에 알맞은 데이터를 받아 데이터베이스에 삽입합니다.",
               responses = @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json",
                                                                                 schema = @Schema(implementation = CommonResponse.class))))
    @PostMapping("/insert")
    public ResponseEntity<CommonResponse<Member>> insert(@RequestBody @Valid Member member) {
        memberService.insert(member);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWith(member));
    }

    @Auth
    @Operation(summary = "멤버 수정",
               description = "멤버 수정에 알맞은 데이터를 받아 데이터베이스의 데이터를 수정합니다.",
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = CommonResponse.class))))
    @PatchMapping("/modify")
    public ResponseEntity<CommonResponse<String>> modify(@RequestParam Integer memberNo,
        @RequestBody @Valid Member member) {
        memberService.modify(memberNo, member);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWithDefaultMessage());
    }

    @Auth
    @Operation(summary = "멤버 삭제",
               description = "멤버 삭제에 알맞은 데이터를 받아 데이터베이스의 데이터를 삭제합니다.",
               responses = {@ApiResponse(responseCode = "200",
                                         content = @Content(mediaType = "application/json", schema =
                                         @Schema(implementation = CommonResponse.class)))})
    @DeleteMapping("/delete")
    public ResponseEntity<CommonResponse<String>> delete(@RequestBody List<Integer> memberNos) {
        memberService.delete(memberNos);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResponse.successWithDefaultMessage());
    }

    @GetMapping("/session")
    public ResponseEntity<CommonResponse<Member>> getMemberToSession(HttpSession session) throws UnknownHostException {

        Object memberNo = session.getAttribute("session");
        if (memberNo != null) {
            try {
                return ResponseEntity.status(HttpStatus.OK)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .body(CommonResponse.successWith(memberService.searchMember((Long) memberNo)));
            } catch (Exception e) {
                return ResponseEntity.ok()
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .body(CommonResponse.error(new ErrorResponse(e.getMessage())));
            }
        } else {
            return ResponseEntity.ok()
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(CommonResponse.error(new ErrorResponse("세션이 존재하지 않습니다.")));
        }
    }
}



