package com.student.searchroom.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.searchroom.model.address.City;
import com.student.searchroom.model.address.District;
import com.student.searchroom.model.address.Street;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class AddressService {

    private List<City> cities;
    private List<District> allDistrict;

    @PostConstruct
    private void loadData() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = AddressService.class.getResourceAsStream("/address/dvhcvn.json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            this.cities = objectMapper.readValue(inputStream, new TypeReference<List<City>>() {});
            this.allDistrict = new ArrayList<>();
            for (City city : this.cities) {
                this.allDistrict.addAll(city.getDistricts());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public List<String> getAllCity() {
        return this.cities.stream().map(City::getName).collect(Collectors.toList());
    }

    public List<String> getDistrictByCity(String cityName) {
        for (City city : this.cities) {
            if (city.getName().equals(cityName)) {
                return city.getDistricts().stream().map(District::getName).collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }

    public List<String> getStreetByDistrict(String districtName) {
        for (District district : this.allDistrict) {
            if (district.getName().equals(districtName))
                return district.getStreets().stream().map(Street::getName).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
