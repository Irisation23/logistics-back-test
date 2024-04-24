package com.douzon.smartlogistics.domain.mainPage.dao;

import com.douzon.smartlogistics.domain.entity.MainPage;
import com.douzon.smartlogistics.domain.mainPage.dao.mapper.MainPageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MainPageDao {

    private final MainPageMapper mainPageMapper;

    public List<MainPage> warehouseRankList() {
        return mainPageMapper.warehouseRankList();
    }

    public List<MainPage> totalReceivesOrdersList(int year) {
        return mainPageMapper.totalReceivesOrdersList(year);
    }

    public List<MainPage> stateValueList() {
        return mainPageMapper.stateValueList();
    }
}
