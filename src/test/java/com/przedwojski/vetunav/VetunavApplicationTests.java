package com.przedwojski.vetunav;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.przedwojski.vetunav.domain.xml.City;
import com.przedwojski.vetunav.domain.xml.Country;
import com.przedwojski.vetunav.domain.xml.Markers;
import com.przedwojski.vetunav.domain.xml.Place;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.WireMockSpring;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {VetunavApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VetunavApplicationTests {

    @ClassRule
    public static WireMockClassRule wiremock = new WireMockClassRule(WireMockSpring.options().port(6067));

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void existingRepository() {
        String url = "http://localhost:6067/full";
        restTemplate.getRestTemplate().setMessageConverters(new ArrayList<HttpMessageConverter<?>>() {{
            add(new Jaxb2RootElementHttpMessageConverter());
        }});
        ResponseEntity<Markers> response = restTemplate.getForEntity(url, Markers.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Markers markers = response.getBody();
        assertThat(markers.getCountry()).isNotNull();
        assertThat(markers.getCountry().size()).isEqualTo(2);
        assertThat(markers.getCountry().get(0).getName()).isEqualToIgnoringCase("VETURILO Poland");
        
        Country country = markers.getCountry().get(0);
        City city = country.getCity();
        assertThat(city).isNotNull();
        assertThat(city.getName()).isEqualToIgnoringCase("Warszawa");

        List<Place> places = city.getPlaces();
        assertThat(places).isNotNull();
        assertThat(places.size()).isGreaterThan(0);

        Place firstPlace = places.get(0);
        assertThat(firstPlace.getName()).isEqualToIgnoringCase("Dewajtis - UKSW");
        assertThat(firstPlace.getLongitude()).isNotNull();
        assertThat(firstPlace.getLongitude()).isEqualTo(20.9583575);
        assertThat(firstPlace.getLatitude()).isNotNull();
        assertThat(firstPlace.getLatitude()).isEqualTo(52.296298);
        assertThat(firstPlace.getBikes()).isNotNull();
        assertThat(firstPlace.getBikes()).isEqualTo(24);
        assertThat(firstPlace.getBikeRacks()).isNotNull();
        assertThat(firstPlace.getBikeRacks()).isEqualTo(30);
        assertThat(firstPlace.getFreeRacks()).isNotNull();
        assertThat(firstPlace.getFreeRacks()).isEqualTo(6);
    }

}
