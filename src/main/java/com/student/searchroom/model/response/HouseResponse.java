package com.student.searchroom.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.searchroom.entity.House;
import com.student.searchroom.solr.entity.HouseSolr;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

@Data
public class HouseResponse {
    private Long id;
    private String title;
    private Long price;
    private String description;
    private String lat;
    private String lnp;
    private String createdBy;
    private String name;
    private String address;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date createdDate;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date lastModifiedDate;
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
    private House.Type type;

    public static HouseResponse from(House house) {
        HouseResponse result = new HouseResponse();
        ObjectMapper mapper = new ObjectMapper();
        BeanUtils.copyProperties(house, result);
        if (house.getImagesUrl() != null) {
            try {
                result.setImagesUrl(mapper.readValue(house.getImagesUrl(), new TypeReference<List<String>>() {
                    @Override
                    public Type getType() {
                        return super.getType();
                    }
                }));
            } catch (Exception e) {

            }
        }
        return result;
    }

    public static HouseResponse from(HouseSolr houseSolr) {
        HouseResponse result = new HouseResponse();
        ObjectMapper mapper = new ObjectMapper();
        BeanUtils.copyProperties(houseSolr, result);
        result.setId(Long.valueOf(houseSolr.getId()));
        result.setType(House.Type.valueOf(houseSolr.getType()));
        if (houseSolr.getImagesUrl() != null) {
            try {
                result.setImagesUrl(mapper.readValue(houseSolr.getImagesUrl(), new TypeReference<List<String>>() {
                    @Override
                    public Type getType() {
                        return super.getType();
                    }
                }));
            } catch (Exception e) {

            }
        }
        return result;
    }
}
