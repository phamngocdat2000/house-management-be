package com.student.searchroom.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.student.searchroom.entity.User;
import com.student.searchroom.entity.VerifyUser;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

@Data
public class VerifyUserResponse {
    private String username;
    private List<String> imagesUrl;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date createdDate = new Date();
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date lastModifiedDate = new Date();
    private String description;
    private VerifyUser.Status status;
    private UserResponse user;

    public static VerifyUserResponse from(VerifyUser verifyUser, User user) {
        VerifyUserResponse result = new VerifyUserResponse();
        BeanUtils.copyProperties(verifyUser, result);
        result.setUser(UserResponse.from(user));
        result.setImagesUrl(verifyUser.getImagesUrl());
        return result;
    }
}
