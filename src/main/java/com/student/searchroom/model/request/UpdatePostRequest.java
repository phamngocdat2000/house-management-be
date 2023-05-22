package com.student.searchroom.model.request;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.searchroom.entity.House;
import com.student.searchroom.util.Utils;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UpdatePostRequest {
    private String title;
    private Long price;
    private String description;
    private String lat;
    private String lnp;
    private String name;
    private String address;
    private String city;
    private String district;
    private String street;
    private String bedroomsDescription;
    private Integer numberOfBedrooms;
    private Integer numberOfKitchens;
    private Integer numberOfToilets;
    private String toiletDescription;
    private String kitchensDescription;
    private Double area;
    private List<String> imagesUrl;

    public void updateHouse(House house) {
        house.setLastModifiedDate(new Date());
        ObjectMapper mapper = new ObjectMapper();
        Utils.copyPropertiesNotNull(this, house);
        if (this.imagesUrl != null && !imagesUrl.isEmpty()) {
            try {
                house.setImagesUrl(mapper.writeValueAsString(this.imagesUrl));
            } catch (Exception e) {

            }
        }
    }
}
