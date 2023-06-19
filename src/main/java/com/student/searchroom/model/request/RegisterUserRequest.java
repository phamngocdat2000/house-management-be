package com.student.searchroom.model.request;

import com.student.searchroom.entity.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class RegisterUserRequest {
    @NotBlank(message = "Không được để trống username!")
    private String username;
    @NotBlank(message = "Không được để trống mật khẩu!")
    private String password;
    @NotBlank(message = "Không được để trống họ tên!")
    private String fullName;
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email không đúng định dạng!")
    @NotBlank(message = "Không được để trống email!")
    private String email;
    @Pattern(regexp = "^[0-9\\-\\+]{9,15}$", message = "Số điện thoại không đúng định dạng!")
    @NotBlank(message = "Không được để trống số điện thoại!")
    private String phone;
    private String avaUrl;

    public User toUser() {
        User user = new User();
        user.setUsername(this.username);
        user.setFullName(this.fullName);
        user.setActive(true);
        user.setEmail(this.email);
        user.setPhone(this.phone);
        user.setAvaUrl(this.avaUrl);
        user.setIsVerified(false);
        user.setProvider(User.Provider.LOCAL);
        return user;
    }
}
