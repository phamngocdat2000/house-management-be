package com.student.searchroom.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResetPasswordRequest {
    @NotBlank(message = "Không được để trống username!")
    private String username;
    @NotBlank(message = "Không được để trống code!")
    private String code;
    @NotBlank(message = "Không được để trống mật khẩu mới!")
    private String newPassword;
}
