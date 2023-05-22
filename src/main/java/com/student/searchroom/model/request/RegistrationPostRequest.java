package com.student.searchroom.model.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.searchroom.entity.House;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RegistrationPostRequest {
    @NotBlank(message = "title is required!")
    private String title;
    @NotNull(message = "price is required!")
    private Long price;
    @NotBlank(message = "description is required!")
    private String description;
    @NotBlank(message = "lat is required!")
    private String lat;
    @NotNull(message = "lnp is required!")
    private String lnp;
    private String name;
    @NotBlank(message = "address is required!")
    private String address;
    @NotBlank(message = "city is required!")
    private String city;
    @NotBlank(message = "district is required!")
    private String district;
    @NotBlank(message = "street is required!")
    private String street;
    @NotBlank(message = "type is required!")
    private String type;
    private Double area;
    private String bedroomsDescription;
    private Integer numberOfBedrooms;
    private Integer numberOfKitchens;
    private Integer numberOfToilets;
    private String toiletDescription;
    private String kitchensDescription;
    private List<String> imagesUrl;
    public House toHouse() {
        House result = new House();
        BeanUtils.copyProperties(this, result);
        ObjectMapper mapper = new ObjectMapper();
        if (this.imagesUrl != null) {
            try {
                result.setImagesUrl(mapper.writeValueAsString(imagesUrl));
            } catch (Exception e) {

            }
        }
        return result;
    }
}
