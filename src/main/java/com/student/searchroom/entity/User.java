package com.student.searchroom.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Data
@Entity
public class User {
    @Id
    private String username;
    private Date createdDate = new Date();
    private Date lastModifiedDate = new Date();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Authority> authorities;
    private String password;
    private String fullName;
    private String avaUrl;
    private String email;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Provider provider;
    private String createdBy;
    private Double ratingValue;
    private Boolean isVerified;
    private boolean isActive;

    public enum Provider {
        LOCAL, GOOGLE, FACEBOOK
    }
}
