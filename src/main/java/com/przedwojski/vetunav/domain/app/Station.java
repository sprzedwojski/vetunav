package com.przedwojski.vetunav.domain.app;

import lombok.Data;

@Data
public class Station {
    private String name;
    private double longitude;
    private double latitude;
    private int bikes;
    private int bikeRacks;
    private int freeRacks;
}
