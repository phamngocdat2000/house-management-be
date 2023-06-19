package com.student.searchroom.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePasswordRequest {
    @NotBlank(message = "Không được để trống username!")
    private String username;
    @NotBlank(message = "Không được để trống mật khẩu cũ!")
    private String oldPassword;
    @NotBlank(message = "Không được để trống mật khẩu mới!")
    private String newPassword;
}
