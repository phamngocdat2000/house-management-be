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
    @NotBlank(message = "Không được để trống tiêu đề!")
    private String title;
    @NotNull(message = "Phải nhập giá!")
    private Long price;
    @NotBlank(message = "Không được để trống mô tả!")
    private String description;
    @NotBlank(message = "Yêu cầu kinh độ!")
    private String lat;
    @NotNull(message = "Yêu cầu vĩ độ!")
    private String lnp;
    private String name;
    @NotBlank(message = "Không được để trống địa chỉ chi tiết!")
    private String address;
    @NotBlank(message = "Không được để trống tên thành phố!")
    private String city;
    @NotBlank(message = "Không được để trống tên Quận, Huyện!")
    private String district;
    @NotBlank(message = "Không được để trống tên Phường, Xã!")
    private String street;
    @NotBlank(message = "Yêu cầu kiểu nhà!")
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
