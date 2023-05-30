package com.student.searchroom.model.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    ACCESS_DENIED("CD-010", "Xin lỗi, bạn không có quyền truy cập!", HttpStatus.FORBIDDEN),
    BAD_REQUEST("CD-010", "Yêu cầu của bạn chưa đúng!!", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("CD-11", "User not found!", HttpStatus.NOT_FOUND),
    BANNED_PERFORM_ACTION("CD-CLIENT-403", "Banded perform action!", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("UNAUTHORIZED", "Login session expired!", HttpStatus.UNAUTHORIZED),
    INTERNAL_SERVER("INTERNAL-SERVER", "internal server!", HttpStatus.INTERNAL_SERVER_ERROR),
    POST_NOT_FOUND("POST_NOT_FOUND", "Post not found!", HttpStatus.NOT_FOUND),
    MINI_TEST_NOT_FOUND("MINI-TEST-NOT-FOUND", "mini test not found!", HttpStatus.NOT_FOUND),
    CONFLICT_USERNAME("APP-20", "Conflict username!", HttpStatus.CONFLICT),
    BANNED_USER("APP-21", "User is banned", HttpStatus.FORBIDDEN),
    INVALID_USERNAME_OR_PASSWORD("APP-22", "Invalid username or password!", HttpStatus.FORBIDDEN),
    CITY_INVALID("APP-23", "city is invalid!", HttpStatus.BAD_REQUEST),
    DISTRICT_INVALID("APP-24", "district is invalid!", HttpStatus.BAD_REQUEST),
    STREET_INVALID("APP-25", "street is invalid!", HttpStatus.BAD_REQUEST),
    TYPE_INVALID("APP-26", "type is 'APARTMENT' OR 'HOUSE_LAND'", HttpStatus.BAD_REQUEST),
    PARENT_COMMENT_NOT_FOUND("APP-27", "parent comment not fount", HttpStatus.NOT_FOUND),
    COMMENT_NOT_FOUND("APP-28", "comment not found!", HttpStatus.NOT_FOUND),
    FORBIDDEN("APP-29", "you do not have permission!", HttpStatus.FORBIDDEN),
    RATING_ALREADY_EXIST("APP-30", "Rating already exist!", HttpStatus.BAD_REQUEST),
    CODE_INVALID("APP-31", "Code invalid!", HttpStatus.BAD_REQUEST),
    CODE_IS_EXPIRE("APP-32", "Code is expire!", HttpStatus.BAD_REQUEST),
    ADDRESS_INVALID("APP-33", "address invalid!", HttpStatus.BAD_REQUEST),
    NEED_VERIFY_ACCOUNT("APP-34", "Bạn cần gửi yêu cầu xác thực cho admin duyệt để được đăng bài!", HttpStatus.BAD_REQUEST),
    ACCOUNT_IS_VERIFIED("APP-35", "Tài khoản đã được xác thực!", HttpStatus.BAD_REQUEST),
    VERIFY_REQUEST_NOT_FOUND("APP-36", "verify request not found!", HttpStatus.NOT_FOUND),
    IMAGES_IS_REQUIRED("APP-37", "Yêu cầu thêm ảnh!", HttpStatus.BAD_REQUEST);


    private String code;
    private String description;
    private HttpStatus httpStatus;

    ErrorCode(String code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }

    public String code() {
        return this.code;
    }

    public String description() {
        return this.description;
    }

    public HttpStatus httpStatus() {
        return this.httpStatus;
    }
}

