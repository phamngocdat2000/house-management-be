package com.student.searchroom.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.student.searchroom.entity.Rating;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class RatingResponse {
    private Long id;
    private String username;
    private int ratingValue;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date lastUpdateAt;
    private String ratingContent;
    private UserResponse createdByInfo;

    public static RatingResponse from(Rating rating, UserResponse createdByInfo) {
        RatingResponse result = new RatingResponse();
        BeanUtils.copyProperties(rating, result);
        result.setCreatedByInfo(createdByInfo);
        return result;
    }
}
