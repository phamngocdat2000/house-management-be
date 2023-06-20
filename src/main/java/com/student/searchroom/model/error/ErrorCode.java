package com.student.searchroom.model.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    ACCESS_DENIED("CD-010", "Xin lỗi, bạn không có quyền truy cập!", HttpStatus.FORBIDDEN),
    BAD_REQUEST("CD-010", "Yêu cầu của bạn chưa đúng!!", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("CD-11", "Không tìm thấy người dùng!", HttpStatus.NOT_FOUND),
    BANNED_PERFORM_ACTION("CD-CLIENT-403", "Banded perform action!", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("UNAUTHORIZED", "Phiên làm việc hết hạn!", HttpStatus.UNAUTHORIZED),
    INTERNAL_SERVER("INTERNAL-SERVER", "internal server!", HttpStatus.INTERNAL_SERVER_ERROR),
    POST_NOT_FOUND("POST_NOT_FOUND", "Không tìm thấy bài viết!", HttpStatus.NOT_FOUND),
    MINI_TEST_NOT_FOUND("MINI-TEST-NOT-FOUND", "mini test not found!", HttpStatus.NOT_FOUND),
    CONFLICT_USERNAME("APP-20", "username bị trùng!", HttpStatus.CONFLICT),
    BANNED_USER("APP-21", "User is banned", HttpStatus.FORBIDDEN),
    INVALID_USERNAME_OR_PASSWORD("APP-22", "Sai username hoặc mật khẩu!", HttpStatus.FORBIDDEN),
    CITY_INVALID("APP-23", "Sai tên thành phố!", HttpStatus.BAD_REQUEST),
    DISTRICT_INVALID("APP-24", "Sai tên Quận!", HttpStatus.BAD_REQUEST),
    STREET_INVALID("APP-25", "Sai tên đường!", HttpStatus.BAD_REQUEST),
    TYPE_INVALID("APP-26", "type là 'APARTMENT' hoặc 'HOUSE_LAND'", HttpStatus.BAD_REQUEST),
    PARENT_COMMENT_NOT_FOUND("APP-27", "comment cha không tìm thấy", HttpStatus.NOT_FOUND),
    COMMENT_NOT_FOUND("APP-28", "Không tìm thấy comment!", HttpStatus.NOT_FOUND),
    FORBIDDEN("APP-29", "Bạn không có quyền thực hiện hành động này!", HttpStatus.FORBIDDEN),
    RATING_ALREADY_EXIST("APP-30", "Đánh giá đã tồn tại!", HttpStatus.BAD_REQUEST),
    CODE_INVALID("APP-31", "Code không hợp lệ!", HttpStatus.BAD_REQUEST),
    CODE_IS_EXPIRE("APP-32", "Code đã hết hạn!", HttpStatus.BAD_REQUEST),
    ADDRESS_INVALID("APP-33", "Địa chỉ không đúng!", HttpStatus.BAD_REQUEST),
    NEED_VERIFY_ACCOUNT("APP-34", "Bạn cần gửi yêu cầu xác thực cho admin duyệt để được đăng bài!", HttpStatus.BAD_REQUEST),
    ACCOUNT_IS_VERIFIED("APP-35", "Tài khoản đã được xác thực!", HttpStatus.BAD_REQUEST),
    VERIFY_REQUEST_NOT_FOUND("APP-36", "verify request not found!", HttpStatus.NOT_FOUND),
    IMAGES_IS_REQUIRED("APP-37", "Yêu cầu thêm ảnh!", HttpStatus.BAD_REQUEST),
    VERIFY_USER_REQUEST_EXISTED("APP-38", "Yêu cầu đã tồn tại!", HttpStatus.CONFLICT),
    POST_STATUS_INVALID("APP-39", "Trạng thái của post chỉ được là 0 hoặc 1", HttpStatus.BAD_REQUEST);


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

