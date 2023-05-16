package com.student.searchroom.model.response;

import com.student.searchroom.exception.SearchRoomException;
import com.student.searchroom.model.error.ErrorCode;
import lombok.Data;

@Data
public class ErrorResponse {
    private String code;
    private String description;

    public ErrorResponse(SearchRoomException e) {
        this.code = e.getCode();
        this.description = e.getDescription();
    }

    public ErrorResponse() {
    }

    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.code();
        this.description = errorCode.description();
    }
}
