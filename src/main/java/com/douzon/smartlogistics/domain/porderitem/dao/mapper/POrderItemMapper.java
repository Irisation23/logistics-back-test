package com.douzon.smartlogistics.domain.porderitem.dao.mapper;

import com.douzon.smartlogistics.domain.entity.POrderItem;
import com.douzon.smartlogistics.domain.porderitem.dto.POrderItemInsertDto;
import com.douzon.smartlogistics.domain.porderitem.dto.POrderItemModifyDto;
import com.douzon.smartlogistics.domain.porderitem.dto.POrderItemStateModifyDto;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface POrderItemMapper {

    boolean checkPOrderCode(String pOrderCode);
    String selectPkNumber(String pOrderCode);
    @Transactional
    void insert(@Param("pOrderItem") POrderItemInsertDto pOrderItem);

    @Transactional
    void delete(List<String> pOrderCode);

    @Transactional
    void modify(@Param("pOrderItemNo") Integer pOrderItemNo,
        @Param("pOrderItemModifyDto") POrderItemModifyDto pOrderItemModifyDto);

    Optional<POrderItem> retrieve(@Param("pOrderItemNo") Integer pOrderItemNo,
        @Param("pOrderCode") String pOrderCode);

    List<POrderItem> searchPOrderItemList(@Param("pOrderCode") String pOrderCode);

    @Transactional
    void deletePOrderItem(@Param("noIngStatePOrdersItem") List<Integer> noIngStatePOrdersItems,
        @Param("pOrderCode") String pOrderCode);

    @Transactional
    void modifyState(@Param("pOrderItemNo") Integer pOrderItemNo,
        @Param("pOrderItemStateModifyDto") POrderItemStateModifyDto pOrderItemStateModifyDto);

    List<POrderItem> checkPOrderItems(@Param("pOrderItemNo") List<Integer> pOrderItemNo,
                                      @Param("pOrderCode") String pOrderCode);
    int searchPOrderItemRemainder(
            @Param("porderCode") String porderCode,
            @Param("porderItemNo") Integer porderItemNo
    );

    Double pOrderItemCount(
            @Param("porderCode") String porderCode,
            @Param("porderItemNo") Integer porderItemNo
    );

    Integer searchPOrderItemStateCount(
            @Param("porderCode") String porderCode
    );

    List<POrderItem> exceptSearchCmpPorderItemList(
            @Param("pOrderCode") String pOrderCode
    );
}
