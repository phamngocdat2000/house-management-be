package com.student.searchroom.model.request;

import com.student.searchroom.entity.User;
import com.student.searchroom.util.Utils;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class UpdateUserRequest {
    private String fullName;
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "invalid email!")
    private String email;
    @Pattern(regexp = "^[0-9\\-\\+]{9,15}$", message = "invalid phone number!")
    private String phone;
    private Boolean isActive;
    private String avaUrl;


    public void updateUser(User user) {
        Utils.copyPropertiesNotNull(this, user);
        user.setLastModifiedDate(new Date());
        if (isActive != null)
            user.setActive(this.isActive);
    }
}
