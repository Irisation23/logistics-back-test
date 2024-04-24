package com.douzon.smartlogistics.domain.warehouse.dao;

import com.douzon.smartlogistics.domain.entity.Warehouse;
import com.douzon.smartlogistics.domain.warehouse.dao.mapper.WarehouseMapper;
import com.douzon.smartlogistics.domain.warehouse.dto.WarehouseInsertDto;
import com.douzon.smartlogistics.domain.warehouse.dto.WarehouseModifyDto;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class WarehouseDao {

    private final WarehouseMapper warehouseMapper;
    public List<Warehouse> warehouseSectionList(Integer warehouseNo, String warehouseName){
           return warehouseMapper.warehouseList(warehouseNo, warehouseName);
    }

    @Transactional
    public void modify(Integer warehouseNo, WarehouseModifyDto warehouseModifyDto) {
        if (!checkExistWarehouse(warehouseNo)) {
            throw new NoSuchElementException("해당 창고번호는 존재하지 않습니다.");
        }

        warehouseMapper.modify(warehouseNo, warehouseModifyDto);
    }

    @Transactional
    public void delete(Integer warehouseNo) {
        if (!checkExistWarehouse(warehouseNo)) {
            throw new NoSuchElementException("해당 창고번호는 존재하지 않습니다.");
        }

        warehouseMapper.delete(warehouseNo);
    }

    @Transactional
    public void insert(WarehouseInsertDto warehouseInsertDto) {
        warehouseMapper.insert(warehouseInsertDto);
    }

    private boolean checkExistWarehouse(Integer warehouseNo) {
        return warehouseMapper.checkExistWarehouse(warehouseNo);
    }

    @Transactional
    public void deleteReceiveWarehouse(String receiveCode){
        warehouseMapper.deleteReceiveWarehouse(receiveCode);
    }

}
