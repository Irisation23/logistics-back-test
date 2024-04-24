package com.douzon.smartlogistics.domain.receive.dao.mapper;
import com.douzon.smartlogistics.domain.entity.Receive;
import com.douzon.smartlogistics.domain.receive.dto.ReceiveInsertDto;
import com.douzon.smartlogistics.domain.receive.dto.ReceiveListDto;
import com.douzon.smartlogistics.domain.receive.dto.ReceiveModifyDto;
import com.douzon.smartlogistics.global.common.aop.annotation.TimeTrace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ReceiveMapper {
    @TimeTrace
    List<ReceiveListDto> findReceive(
            @Param("receiveCode") String receiveCode,
            @Param("manager") String manager,
            @Param("createIp") String createIp,
            @Param("createId") String createId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );
    @Transactional
    void insertReceive(ReceiveInsertDto receiveInsertDto);

    @TimeTrace
    Optional<Receive> retrieve(String receiveCode);

    @Transactional
    void deleteReceive(String receiveCode);

    @Transactional
    void modifyReceive(
            @Param("receiveModifyDto") ReceiveModifyDto receiveModifyDto);

}
