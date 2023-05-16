package com.student.searchroom.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class Authority implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToOne()
    @JoinColumn(name = "username")
    private User user;

    public enum Role {
        ROLE_ADMIN("ROLE_ADMIN"),
        ROLE_USER("ROLE_USER"),
        ROLE_SALE("ROLE_SALE");
        private String value;
        private Role(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
