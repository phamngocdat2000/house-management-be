package com.student.searchroom.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BanUserRequest {
    @NotBlank(message = "Không được để trống username!")
    private String username;
}
