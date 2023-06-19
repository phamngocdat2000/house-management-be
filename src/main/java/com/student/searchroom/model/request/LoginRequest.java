package com.student.searchroom.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank(message = "Không được để trống username!")
    private String username;
    @NotBlank(message = "Không được để trống mật khẩu!")
    private String password;
}
