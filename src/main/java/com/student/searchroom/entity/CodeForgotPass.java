package com.student.searchroom.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class CodeForgotPass {
    @Id
    private String username;
    private String code;
    private Long expireDate;
}
