package com.student.searchroom.model.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class RatingRequest {
    @NotNull(message = "ratingValue is required!")
    @Max(value = 5, message = "ratingValue max = 5")
    @Min(value = 0, message = "ratingValue min = 0")
    private int ratingValue;
    private String ratingContent;
}
