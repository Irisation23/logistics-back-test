package com.douzon.smartlogistics.domain.warehouse.application;

import com.douzon.smartlogistics.domain.entity.Warehouse;
import com.douzon.smartlogistics.domain.warehouse.dao.WarehouseDao;
import com.douzon.smartlogistics.domain.warehouse.dto.WarehouseInsertDto;
import com.douzon.smartlogistics.domain.warehouse.dto.WarehouseModifyDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class WarehouseService {

    private final WarehouseDao warehouseDao;

    public List<Warehouse> warehouseSectionList(Integer warehouseNo, String warehouseName) {
        return warehouseDao.warehouseSectionList(warehouseNo, warehouseName);
    }

    @Transactional
    public void modify(Integer warehouseNo, WarehouseModifyDto warehouseModifyDto) {
        warehouseDao.modify(warehouseNo, warehouseModifyDto);
    }

    @Transactional
    public void delete(Integer warehouseNo) {
        warehouseDao.delete(warehouseNo);
    }

    @Transactional
    public void insert(WarehouseInsertDto warehouseInsertDto) {
        warehouseDao.insert(warehouseInsertDto);
    }
}
