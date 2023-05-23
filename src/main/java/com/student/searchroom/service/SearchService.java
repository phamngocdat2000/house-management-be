package com.student.searchroom.service;

import com.student.searchroom.entity.House;
import com.student.searchroom.exception.SearchRoomException;
import com.student.searchroom.model.Coordinate;
import com.student.searchroom.model.error.ErrorCode;
import com.student.searchroom.model.response.HouseResponse;
import com.student.searchroom.solr.entity.HouseSolr;
import com.student.searchroom.solr.repository.HouseSolrRepository;
import com.student.searchroom.util.Calculator;
import com.student.searchroom.util.FilterUtils;
import com.student.searchroom.util.KeywordGenerator;
import com.student.searchroom.util.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class SearchService {
    private final HouseSolrRepository houseSolrRepository;

    public SearchService(HouseSolrRepository houseSolrRepository) {
        this.houseSolrRepository = houseSolrRepository;
    }

    public List<String> searchAddress(String keyword, Long minPrice, Long maxPrice, Double lat, Double lnp, Double distance, String[] types, Integer[] numberOfBedrooms) {
        keyword = keyword.toLowerCase();
        validateRequest(lat, lnp, distance);
        List<HouseSolr> housesSolr = houseSolrRepository.search(KeywordGenerator.genStringQuerySearchAddress(keyword, minPrice, maxPrice, types, numberOfBedrooms));
        List<HouseSolr> housesSolrResult = FilterUtils.filterByDistance(housesSolr, lat, lnp, distance);
        Set<String> address = housesSolrResult.stream().map(HouseSolr::toAddress).collect(Collectors.toSet());
        return new ArrayList<>(makeResponse(keyword, address));
    }

    private Set<String> makeResponse(String keyword, Set<String> address) {
        Set<String> result = new HashSet<>();
        for (String addressToHandle : address) {
            String addressEn = Utils.toEn(addressToHandle.toLowerCase());
            String keywordEn = Utils.toEn(keyword);
            int indexOfKeyword = addressEn.indexOf(keywordEn);
            if (indexOfKeyword < 0) {
                result.add(addressToHandle);
                continue;
            }
            String temp = addressToHandle.substring(0, indexOfKeyword);
            int x = temp.lastIndexOf(",");
            if (x < 0) {
                result.add(addressToHandle);
            } else {
                result.add(addressToHandle.substring(x + 2));
            }
        }
        return result;
    }

    public Map<String, Object> searchHouses(String address, Long minPrice, Long maxPrice, Double lat, Double lnp, Double distance, String[] types, Integer[] numberOfBedrooms) {

        validateRequest(lat, lnp, distance);
        address = address.replace(",", "");
        List<HouseSolr> housesSolr = houseSolrRepository.search(KeywordGenerator.genStringQuerySearchAddress(address, minPrice, maxPrice, types, numberOfBedrooms));
        return convertHouseSolrToResponse(FilterUtils.filterByDistance(housesSolr, lat, lnp, distance));
    }

    private Map<String, Object> convertHouseSolrToResponse(List<HouseSolr> housesSolr) {
        Coordinate centerPoint = Calculator.calculateCenterCoordinate(housesSolr);
        List<HouseResponse> housesResponse = housesSolr.stream().map(HouseResponse::from).collect(Collectors.toList());
        Map<String, Object> result = new HashMap<>();
        result.put("centerPoint", centerPoint);
        result.put("houses", housesResponse);
        return result;
    }

    private List<HouseResponse> convertToResponse(List<House> houses) {
        return houses.stream().map(HouseResponse::from).collect(Collectors.toList());
    }


    public Map<String, Object> searchHousesByAddressAndTitle(String address, Long minPrice, Long maxPrice, Double lat, Double lnp, Double distance, String[] types, Integer[] numberOfBedrooms) {

        validateRequest(lat, lnp, distance);
        List<HouseSolr> housesSolr =
                houseSolrRepository.search(KeywordGenerator.genStringQuerySearchAddressAndTitle(address, minPrice, maxPrice, types, numberOfBedrooms));
        return convertHouseSolrToResponse(FilterUtils.filterByDistance(housesSolr, lat, lnp, distance));
    }


    private void validateRequest(Double lat, Double lnp, Double distance) {
        if (distance != null && distance > 0) {
            if (lat == null || lnp == null)
                throw new SearchRoomException(ErrorCode.BAD_REQUEST);
        }
    }


}
