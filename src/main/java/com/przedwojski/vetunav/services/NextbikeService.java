package com.przedwojski.vetunav.services;

import com.przedwojski.vetunav.domain.app.Station;
import com.przedwojski.vetunav.domain.xml.City;
import com.przedwojski.vetunav.domain.xml.Country;
import com.przedwojski.vetunav.domain.xml.Markers;
import com.przedwojski.vetunav.domain.xml.Place;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NextbikeService {
    private static String URL = "https://nextbike.net/maps/nextbike-live.xml?city=210";

    public List<Station> getData() {
        RestTemplate restTemplate = getRestTemplate();
        Markers markers = restTemplate.getForObject(URL, Markers.class);

        return markers.getCountry()
                .stream()
                .filter(country -> country.getName().equals("VETURILO Poland"))
                .map(Country::getCity)
                .map(City::getPlaces)
                .flatMap(Collection::stream)
                .map(this::mapToStation)
                .collect(Collectors.toList());
    }

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(new ArrayList<HttpMessageConverter<?>>() {{
            add(new Jaxb2RootElementHttpMessageConverter());
        }});
        return restTemplate;
    }

    private Station mapToStation(Place place) {
        Station station = new Station();
        station.setName(place.getName());
        station.setLongitude(place.getLongitude());
        station.setLatitude(place.getLatitude());
        station.setBikeRacks(place.getBikeRacks());
        station.setBikes(place.getBikes());
        station.setFreeRacks(place.getFreeRacks());
        return station;
    }
}
