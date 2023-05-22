package com.student.searchroom.service;

import com.student.searchroom.entity.House;
import com.student.searchroom.exception.SearchRoomException;
import com.student.searchroom.model.error.ErrorCode;
import com.student.searchroom.model.request.RegistrationPostRequest;
import com.student.searchroom.model.request.UpdatePostRequest;
import com.student.searchroom.model.response.HouseResponse;
import com.student.searchroom.repository.HouseRepository;
import com.student.searchroom.security.SecurityUtil;
import com.student.searchroom.solr.entity.HouseSolr;
import com.student.searchroom.solr.repository.HouseSolrRepository;
import com.student.searchroom.util.FilterUtils;
import com.student.searchroom.util.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class PostService {
    @Autowired
    private HouseSolrRepository houseSolrRepository;
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private AddressService addressService;



    public HouseResponse registrationPost(RegistrationPostRequest request) {
        House toSave = request.toHouse();
        String currentUser = SecurityUtil.getCurrentUsername();

        toSave.setCreatedBy(currentUser);
        try {
            toSave.setType(House.Type.valueOf(request.getType()));
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw new SearchRoomException(ErrorCode.TYPE_INVALID);
        }
        validateAddressInPost(toSave);
        House result = houseRepository.save(toSave);
        indexHouseToSolr(result);
        return HouseResponse.from(result);
    }

    private void validateAddressInPost(House house) {
        if (house.getCity() == null) return;
        if (!addressService.getAllCity().contains(house.getCity()))
            throw new SearchRoomException(ErrorCode.CITY_INVALID);
        if (!addressService.getDistrictByCity(house.getCity()).contains(house.getDistrict()))
            throw new SearchRoomException(ErrorCode.DISTRICT_INVALID);
        if (!addressService.getStreetByDistrict(house.getDistrict()).contains(house.getStreet()))
            throw new SearchRoomException(ErrorCode.STREET_INVALID);
        validateDetailAddressInPost(house);
    }

    private void validateDetailAddressInPost(House house) {
        StringBuilder sourceBuilder = new StringBuilder();
        sourceBuilder.append(house.getDistrict())
                .append(" ")
                .append(house.getStreet())
                .append(" ")
                .append(house.getCity());
        String source = sourceBuilder.toString();
        source = Utils.toEn(source);
        source = source.toLowerCase();
        String address = house.getAddress();
        String addressToEqual = address.replace(",", "");
        addressToEqual = Utils.toEn(addressToEqual);
        addressToEqual = addressToEqual.toLowerCase();
        String[] addressWords = addressToEqual.split(" ");
        int point = 0;
        for (int i = 0; i < addressWords.length; i ++) {
            if (source.contains(addressWords[i]))
                point ++;
        }
        if (point < 4)
            throw new SearchRoomException(ErrorCode.ADDRESS_INVALID);
    }

    private void indexHouseToSolr(House toIndexed) {
        HouseSolr houseSolr = new HouseSolr();
        houseSolr.setId(toIndexed.getId() + "");
        BeanUtils.copyProperties(toIndexed, houseSolr);
        houseSolr.createDataSearch();
        houseSolr.setType(toIndexed.getType().toString());
        houseSolrRepository.save(houseSolr);
    }

    public HouseResponse updatePost(UpdatePostRequest request, Long postId) {
        String currentNick = SecurityUtil.getCurrentUsername();
        House houseToUpdate = houseRepository.findById(postId).orElseThrow(() ->
                new SearchRoomException(ErrorCode.POST_NOT_FOUND));
        if (!houseToUpdate.getCreatedBy().equals(currentNick)) throw new SearchRoomException(ErrorCode.BAD_REQUEST);
        request.updateHouse(houseToUpdate);
        validateAddressInPost(houseToUpdate);
        House result = houseRepository.save(houseToUpdate);
        indexHouseToSolr(result);
        return HouseResponse.from(result);
    }


    public List<HouseResponse> getByDistance(double lat, double lnp, double distance) {
        Iterable<HouseSolr> houseSolrIterable = houseSolrRepository.findAll();
        List<HouseSolr> houses = new ArrayList<HouseSolr>();
        houseSolrIterable.forEach(houseSolr -> houses.add(houseSolr));
        return convertHouseSolrToResponse(FilterUtils.filterByDistance(houses, lat, lnp, distance));
    }

    private List<HouseResponse> convertHouseSolrToResponse(List<HouseSolr> housesSolr) {
        return housesSolr.stream().map(HouseResponse::from).collect(Collectors.toList());
    }

    private List<HouseResponse> convertToResponse(List<House> houses) {
        return houses.stream().map(HouseResponse::from).collect(Collectors.toList());
    }




    public List<HouseResponse> getAllByCreator(String username, int pageIndex, int pageSize) {
        Pageable paging = PageRequest.of(pageIndex - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        return convertToResponse(houseRepository.findAllByCreatedBy(username, paging).getContent());
    }

    public HouseResponse getById(Long id) {
        return HouseResponse.from(houseRepository.findById(id).orElseThrow(() ->
                new SearchRoomException(ErrorCode.POST_NOT_FOUND)));
    }
}
