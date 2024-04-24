package com.douzon.smartlogistics.domain.item.dao.mapper;

import com.douzon.smartlogistics.domain.entity.Item;
import com.douzon.smartlogistics.domain.item.dto.ItemInsertDto;
import com.douzon.smartlogistics.domain.item.dto.ItemModifyDto;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface ItemMapper {

    List<Item> searchItemList(
        @Param("itemCode") Integer itemCode,
        @Param("itemName") String itemName,
        @Param("createDate") String createDate,
        @Param("createId") String createId,
        @Param("itemPrice") Integer itemPrice);

    @Transactional
    void insert(ItemInsertDto itemInsertDto);

    Optional<Item> retrieve(Integer itemCode);

    @Transactional
    void modify(@Param("itemCode") Integer itemCode, @Param("itemModifyDto") ItemModifyDto itemModifyDto);

    @Transactional
    void delete(@Param("itemCode") Integer itemCode);

    boolean checkExistItem(Integer itemCode);

    Integer findLastPk();
}
