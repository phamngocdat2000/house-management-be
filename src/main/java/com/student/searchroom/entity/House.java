package com.student.searchroom.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.student.searchroom.exception.SearchRoomException;
import com.student.searchroom.model.error.ErrorCode;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class House implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private Long price;
    @Lob
    private String description;
    private String lat;
    private String lnp;
    private String createdBy;
    private String name;
    private String address;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date createdDate = new Date();
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date lastModifiedDate = new Date();
    private String city;
    private String district;
    private String street;
    private String bedroomsDescription;
    private int numberOfBedrooms;
    private String toiletDescription;
    private String kitchensDescription;
    private Double area;
    @Lob
    private String imagesUrl;
    @Enumerated(EnumType.STRING)
    private Type type;

    public static Type getTypeByString(String typeString) {
        if (typeString == null) return null;
        try {
            return Type.valueOf(typeString);
        } catch (Exception e) {
            throw new SearchRoomException(ErrorCode.TYPE_INVALID);
        }
    }

    public enum Type {
        APARTMENT,
        HOUSE_LAND,
        BEDSIT;
    }
}
