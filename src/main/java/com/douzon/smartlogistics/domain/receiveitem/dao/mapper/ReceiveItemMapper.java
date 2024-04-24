package com.douzon.smartlogistics.domain.receiveitem.dao.mapper;

import com.douzon.smartlogistics.domain.entity.ReceiveItem;
import com.douzon.smartlogistics.domain.receiveitem.dto.ReceiveItemInsertDto;
import com.douzon.smartlogistics.domain.receiveitem.dto.ReceiveItemListDto;
import com.douzon.smartlogistics.domain.receiveitem.dto.ReceiveItemModifyDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ReceiveItemMapper {

    @Transactional
    void insertReceiveItem(@Param("receiveItem") ReceiveItemInsertDto receiveItem);

    Optional<ReceiveItem> retrieveReceiveItem(String receiveCode, Long receiveItemNo);

    @Transactional
    void deleteReceiveItem(String receiveCode, Long receiveItemNo);

    @Transactional
    void modifyReceiveItem(
            @Param("ReceiveItem")ReceiveItemModifyDto receiveItemModifyDto
            );

    List<ReceiveItemListDto> searchReceiveItem(
            @Param("receiveCode") String receiveCode);

    Double receiveItemCount(
            @Param("pOrderCode") String porderCode,
            @Param("pOrderItemNo") Integer porderItemNo
    );

}
