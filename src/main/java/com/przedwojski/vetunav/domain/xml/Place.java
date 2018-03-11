package com.przedwojski.vetunav.domain.xml;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class Place {
    @XmlAttribute
    private String name;
    @XmlAttribute(name = "lng")
    private double longitude;
    @XmlAttribute(name = "lat")
    private double latitude;
    @XmlAttribute
    private int bikes;
    @XmlAttribute(name = "bike_racks")
    private int bikeRacks;
    @XmlAttribute(name = "free_racks")
    private int freeRacks;
    
}
