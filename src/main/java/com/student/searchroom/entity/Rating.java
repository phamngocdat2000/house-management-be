package com.student.searchroom.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.Date;

@Data
@Entity
public class Rating {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String createdBy;
    private int ratingValue;
    private Date createdAt;
    private Date lastUpdateAt;
    @Lob
    private String ratingContent;
}
