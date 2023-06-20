package com.student.searchroom.solr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.student.searchroom.util.Utils;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Date;


@SolrDocument(solrCoreName = "house")
@Data
public class HouseSolr {
    @Id
    @Indexed(name = "id", type = "string")
    private String id;
    @Indexed(name = "title", type = "string")
    private String title;
    @Indexed(name = "price", type = "long")
    private Long price;
    @Indexed(name = "description", type = "string")
    private String description;
    @Indexed(name = "lat", type = "string")
    private String lat;
    @Indexed(name = "lnp", type = "string")
    private String lnp;
    @Indexed(name = "createdBy", type = "string")
    private String createdBy;
    @Indexed(name = "name", type = "string")
    private String name;
    @Indexed(name = "address", type = "string")
    private String address;
    @Indexed(name = "city", type = "string")
    private String city;
    @Indexed(name = "district", type = "string")
    private String district;
    @Indexed(name = "street", type = "string")
    private String street;
    @Indexed(name = "createdDate", type = "date")
    private Date createdDate;
    @Indexed(name = "lastModifiedDate", type = "date")
    private Date lastModifiedDate;
    @Indexed(name = "imagesUrl", type = "string")
    private String imagesUrl;
    @JsonIgnore
    @Indexed(name = "searchData", type = "string")
    private String searchData;
    @JsonIgnore
    @Indexed(name = "addressAndTitle", type = "string")
    private String addressAndTitle;
    @Indexed(name = "type", type = "string")
    private String type;
    @Indexed(name = "bedroomsDescription", type = "string")
    private String bedroomsDescription;
    @Indexed(name = "numberOfBedrooms", type = "int")
    private Integer numberOfBedrooms;
    @Indexed(name = "numberOfKitchens", type = "int")
    private Integer numberOfKitchens;
    @Indexed(name = "status", type = "int")
    private Integer status;
    @Indexed(name = "numberOfToilets", type = "int")
    private Integer numberOfToilets;
    @Indexed(name = "toiletDescription", type = "string")
    private String toiletDescription;
    @Indexed(name = "kitchensDescription", type = "string")
    private String kitchensDescription;
    @Indexed(name = "area", type = "double")
    private Double area;

    public void createDataSearch() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.address.toLowerCase()).append(" ")
                .append(this.street.toLowerCase()).append(" ")
                .append(this.district.toLowerCase()).append(" ")
                .append(this.city.toLowerCase()).append(" ")
                .append(Utils.toEn(this.address.toLowerCase())).append(" ")
                .append(Utils.toEn(this.street.toLowerCase())).append(" ")
                .append(Utils.toEn(this.district.toLowerCase())).append(" ")
                .append(Utils.toEn(this.city.toLowerCase()));
        this.searchData = builder.toString().replace(" ", "-");
        builder.append(" ").append(this.title.toLowerCase()).append(" ")
                .append(Utils.toEn(this.title.toLowerCase()));
        this.addressAndTitle = builder.toString().replace(" ", "-");
    }

    public String toAddress() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.street).append(", ")
                .append(this.district).append(", ")
                .append(this.city);
        return builder.toString();
    }
}
