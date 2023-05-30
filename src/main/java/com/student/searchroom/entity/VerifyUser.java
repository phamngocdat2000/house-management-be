package com.student.searchroom.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

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
}
