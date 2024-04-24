package com.douzon.smartlogistics.domain.receive.application;
import com.douzon.smartlogistics.domain.entity.Receive;
import com.douzon.smartlogistics.domain.entity.constant.SeqCode;
import com.douzon.smartlogistics.domain.receive.dao.ReceiveDao;
import com.douzon.smartlogistics.domain.receive.dto.ReceiveInsertDto;
import com.douzon.smartlogistics.domain.receive.dto.ReceiveListDto;
import com.douzon.smartlogistics.domain.receive.dto.ReceiveModifyDto;
import com.douzon.smartlogistics.domain.warehouse.dao.WarehouseDao;
import com.douzon.smartlogistics.global.common.util.AutoSeqGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiveService {
    private final ReceiveDao receiveDao;
    private final WarehouseDao warehouseDao;

    public List<ReceiveListDto> findReceive(String receiveCode, String manager, String createIp, String createId, String startDate, String endDate) {
        if (startDate != null && !startDate.isEmpty()) {
            startDate += " 00:00:00";
        }

        if (endDate != null && !endDate.isEmpty()) {
            endDate += " 23:59:59";
        }

        return receiveDao.findReceive(receiveCode, manager, createIp, createId, startDate, endDate);
    }

    @Transactional
    public String insertReceive(ReceiveInsertDto receiveInsertDto){
        receiveInsertDto.setReceiveCode(AutoSeqGenerator.generate(SeqCode.RV));
        receiveDao.insertReceive(receiveInsertDto);
        return receiveInsertDto.getReceiveCode();
    }

    @Transactional
    public void deleteReceive(List<String> receiveCodes) {
        for (String receiveCode: receiveCodes) {
            receiveDao.deleteReceive(receiveCode);
            warehouseDao.deleteReceiveWarehouse(receiveCode);
        }
    }

    @Transactional
    public String modifyReceive(ReceiveModifyDto receiveModifyDto) {
        receiveDao.modifyReceive(receiveModifyDto);
        return receiveModifyDto.getReceiveCode();
    }
}
