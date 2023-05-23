package com.student.searchroom.controller;

import com.student.searchroom.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/search")
public class  SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/address")
    public ResponseEntity<List<String>> searchAddress(@RequestParam(required = false, defaultValue = "") String keyword,
                                                      @RequestParam(name = "min_price", required = false) Long minPrice,
                                                      @RequestParam(name = "max_price", required = false) Long maxPrice,
                                                      @RequestParam(required = false) Double lat,
                                                      @RequestParam(required = false) Double lnp,
                                                      @RequestParam(required = false) Double distance,
                                                      @RequestParam(required = false) String[] types,
                                                      @RequestParam(required = false, name = "room") Integer[] numberOfBedrooms) {

        return ResponseEntity.ok(searchService.searchAddress(keyword, minPrice, maxPrice, lat, lnp, distance, types, numberOfBedrooms));
    }

    @GetMapping("/houses")
    public ResponseEntity<Map<String, Object>> searchHouses(@RequestParam(required = false, defaultValue = "", name = "keyword") String address,
                                                            @RequestParam(name = "min_price", required = false) Long minPrice,
                                                            @RequestParam(name = "max_price", required = false) Long maxPrice,
                                                            @RequestParam(required = false) Double lat,
                                                            @RequestParam(required = false) Double lnp,
                                                            @RequestParam(required = false) Double distance,
                                                            @RequestParam(required = false) String[] types,
                                                            @RequestParam(required = false, name = "room") Integer[] numberOfBedrooms) {
        return ResponseEntity.ok(searchService.searchHouses(address, minPrice, maxPrice, lat, lnp, distance, types, numberOfBedrooms));
    }

    @GetMapping("/houses/address-title")
    public ResponseEntity<Map<String, Object>> searchHousesByAddressAndTitle(@RequestParam(required = false, defaultValue = "", name = "keyword") String address,
                                                                             @RequestParam(name = "min_price", required = false) Long minPrice,
                                                                             @RequestParam(name = "max_price", required = false) Long maxPrice,
                                                                             @RequestParam(required = false) Double lat,
                                                                             @RequestParam(required = false) Double lnp,
                                                                             @RequestParam(required = false) Double distance,
                                                                             @RequestParam(required = false) String[] types,
                                                                             @RequestParam(required = false, name = "room") Integer[] numberOfBedrooms) {
        return ResponseEntity.ok(searchService.searchHousesByAddressAndTitle(address, minPrice, maxPrice, lat, lnp, distance, types, numberOfBedrooms));
    }
}
