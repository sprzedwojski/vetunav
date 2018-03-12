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
    public static final String COUNTRY_NAME = "VETURILO Poland";
    private static String URL = "https://nextbike.net/maps/nextbike-live.xml?city=210";
//    private static String URL = 
//        "https://2754ba2c-1f68-4323-a672-7603b87bb844.mock.pstmn.io/nextbike";

    public List<Station> getData() {
        RestTemplate restTemplate = getRestTemplate();
        Markers markers = restTemplate.getForObject(URL, Markers.class);

        return markers.getCountry()
                .stream()
                .filter(country -> country.getName().equals(COUNTRY_NAME))
                .map(Country::getCity)
                .map(City::getPlaces)
                .flatMap(Collection::stream)
                .map(this::mapToStation)
                .limit(1)
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
