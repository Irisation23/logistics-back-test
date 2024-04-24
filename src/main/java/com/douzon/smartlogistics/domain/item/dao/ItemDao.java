package com.douzon.smartlogistics.domain.item.dao;

import com.douzon.smartlogistics.domain.entity.Item;
import com.douzon.smartlogistics.domain.item.dao.mapper.ItemMapper;
import com.douzon.smartlogistics.domain.item.dto.ItemInsertDto;
import com.douzon.smartlogistics.domain.item.dto.ItemModifyDto;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ItemDao {

    private final ItemMapper itemMapper;

    public List<Item> searchItemList(Integer itemCode, String itemName, String createDate, String createId,
        Integer itemPrice) {

        return itemMapper.searchItemList(itemCode, itemName, createDate, createId, itemPrice);
    }

    @Transactional
    public Integer insert(ItemInsertDto itemInsertDto) {
        itemMapper.insert(itemInsertDto);
        return itemMapper.findLastPk();
    }

    @Transactional
    public Integer modify(Integer itemCode, ItemModifyDto itemModifyDto) {
        Integer retrieveItemCode = retrieveItem(itemCode);

        itemMapper.modify(retrieveItemCode, itemModifyDto);

        return retrieveItemCode;
    }


    @Transactional
    public void delete(List<Integer> itemCodes) {
        for (Integer itemCode : itemCodes) {
            Integer retrieveItemCode = retrieveItem(itemCode);
            itemMapper.delete(retrieveItemCode);
        }
    }

    private Integer retrieveItem(Integer itemCode) {
        return itemMapper.retrieve(itemCode).orElseThrow(() -> {
            throw new NoSuchElementException("해당 아이템은 존재하지 않습니다.");
        }).getItemCode();
    }
}
