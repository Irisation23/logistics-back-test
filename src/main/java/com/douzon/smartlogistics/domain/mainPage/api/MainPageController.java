package com.douzon.smartlogistics.domain.mainPage.api;

import com.douzon.smartlogistics.domain.entity.MainPage;
import com.douzon.smartlogistics.domain.mainPage.application.MainPageService;
import com.douzon.smartlogistics.global.common.response.CommonResponse;
import com.sun.tools.javac.Main;
import io.swagger.annotations.Api;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "메인페이지 api 명세서")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/mainPage")
public class MainPageController {

    private final MainPageService mainPageService;

    @GetMapping("/warehouseRank")
    public ResponseEntity<CommonResponse<List<MainPage>>> searchWarehouseRank()
    {
        List<MainPage> warehouseRankList = mainPageService.warehouseRankList();

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(CommonResponse.successWith(warehouseRankList));
    }

    @GetMapping("/totalReceivesOrders")
    public ResponseEntity<CommonResponse<List<MainPage>>> searchTotalReceivesOrders(@RequestParam(name = "year") int year)
    {
        List<MainPage> totalReceivesOrdersList = mainPageService.totalReceivesOrdersList(year);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(CommonResponse.successWith(totalReceivesOrdersList));
    }

    @GetMapping("/stateValue")
    public ResponseEntity<CommonResponse<List<MainPage>>> searchStateValue() {
        List<MainPage> stateValueList = mainPageService.stateValueList();

    return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(CommonResponse.successWith(stateValueList));

    }
}


