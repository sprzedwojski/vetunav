package com.przedwojski.vetunav.controllers;

import com.przedwojski.vetunav.domain.app.Station;
import com.przedwojski.vetunav.services.NextbikeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StationsController {
    
    private NextbikeService nextbikeService;

    public StationsController(NextbikeService nextbikeService) {
        this.nextbikeService = nextbikeService;
    }

    @RequestMapping(path = "/stations")
    public List<Station> getStations() {
        return nextbikeService.getData();
    }
    
}
