package com.douzon.smartlogistics.domain.porder.dao.mapper;

import com.douzon.smartlogistics.domain.entity.POrder;
import com.douzon.smartlogistics.domain.entity.constant.State;
import com.douzon.smartlogistics.domain.porder.dto.POrderInsertDto;
import com.douzon.smartlogistics.domain.porder.dto.POrderModifyDto;
import com.douzon.smartlogistics.domain.porderitem.dto.POrderItemStateModifyDto;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface POrderMapper {

    List<POrder> searchPOrder(String pOrderCode, String manager, String createId, String createIp, Integer accountNo,
                              State state, String startDate, String endDate, String pOrderDate);

    @Transactional
    void insert(POrderInsertDto pOrderInsertDto);

    Optional<POrder> retrieve(String pOrderCode);

    void modify(@Param("pOrderCode") String retrievePOrderCode,
                @Param("pOrderModifyDto") POrderModifyDto pOrderModifyDto);

    @Transactional
    void delete(List<String> pOrderCode);

    boolean checkExistPOrder(String pOrderCode);

    @Transactional
    void modifyStateToCmp(@Param("pOrderCode") String pOrderCode,
                          @Param("pOrderItemStateModifyDto") POrderItemStateModifyDto pOrderItemStateModifyDto);

    @Transactional
    void modifyStateToIng(@Param("pOrderCode") String pOrderCode,
                          @Param("pOrderItemStateModifyDto") POrderItemStateModifyDto pOrderItemStateModifyDto);

    List<POrder> exceptSearchCmpPOrder(@Param("pOrderCode") String pOrderCode,
                                      @Param("manager") String manager,
                                      @Param("accountNo") Integer accountNo,
                                      @Param("startDate") String startDate,
                                      @Param("endDate") String endDate);


    List<POrder> searchPOrderPK();

    List<POrder> checkPOrder (List<String> porderCode);;




}
