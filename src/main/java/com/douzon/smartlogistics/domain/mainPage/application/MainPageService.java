package com.douzon.smartlogistics.domain.mainPage.application;

import com.douzon.smartlogistics.domain.entity.MainPage;
import com.douzon.smartlogistics.domain.mainPage.dao.MainPageDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MainPageService {

    private final MainPageDao mainPageDao;
    public List<MainPage> warehouseRankList() {
        return mainPageDao.warehouseRankList();
    }

    public List<MainPage> totalReceivesOrdersList(int year) {
        return mainPageDao.totalReceivesOrdersList(year);
    }

    public List<MainPage> stateValueList() {
        return mainPageDao.stateValueList();
    }
}
