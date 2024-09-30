package com.speed.speed_frota.modules.mobile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FleetDTO {

    private Integer id;

    private String md5;

    private String description;

    private String provider;

    private Float price;

    private Integer vehicle_id;

    private String obs;
    
    private String sent;
}
