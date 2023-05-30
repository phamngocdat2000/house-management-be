package com.student.searchroom.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class VerifyUser {
    @Id
    private String username;
    @Lob
    private String imagesUrl;
    private Date createdDate = new Date();
    private Date lastModifiedDate = new Date();
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    public enum Status {
        PENDING,
        UPDATE_REQUEST
    }

    public List<String> getImagesUrl() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(this.imagesUrl, new TypeReference<List<String>>() {
                @Override
                public Type getType() {
                    return super.getType();
                }
            });
        } catch (Exception e) {
            return null;
        }
    }
}
