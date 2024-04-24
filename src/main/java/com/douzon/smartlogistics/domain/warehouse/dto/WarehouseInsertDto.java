package com.douzon.smartlogistics.domain.warehouse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class WarehouseInsertDto {

    @JsonProperty(value = "warehouseName")
    private String warehouseName;
    private String createIp;
    private String createId;
}
