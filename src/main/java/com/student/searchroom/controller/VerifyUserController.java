package com.student.searchroom.controller;

import com.student.searchroom.auth.AuthoritiesConstants;
import com.student.searchroom.entity.VerifyUser;
import com.student.searchroom.model.request.VerifyUserRequest;
import com.student.searchroom.model.response.VerifyUserResponse;
import com.student.searchroom.service.VerifyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api")
public class VerifyUserController {
    @Autowired
    private VerifyUserService verifyUserService;

    @PostMapping("/verify-user")
    public ResponseEntity<VerifyUser> registrationRequest(@RequestBody @Valid VerifyUserRequest request) {
        return ResponseEntity.ok(verifyUserService.registrationVerifyUser(request));
    }

    @PatchMapping("/verify-user")
    public ResponseEntity<VerifyUser> update(@RequestBody @Valid VerifyUserRequest request) {
        return ResponseEntity.ok(verifyUserService.updateVerifyUser(request));
    }

    @GetMapping("/verify-user")
    public ResponseEntity<VerifyUser> get() {
        return ResponseEntity.ok(verifyUserService.getVerifyUser());
    }

    @Secured(AuthoritiesConstants.ROLE_ADMIN)
    @GetMapping("/verifies-user")
    public ResponseEntity<List<VerifyUserResponse>> getByAdmin(@RequestParam(name = "page_index", defaultValue = "1", required = false) int pageIndex,
                                                               @RequestParam(name = "page_size", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(verifyUserService.getVerifyRequestByAdmin(pageIndex, pageSize));
    }

    @Secured(AuthoritiesConstants.ROLE_ADMIN)
    @PostMapping("/verify-user/accept/{username}")
    public ResponseEntity<HttpStatus> acceptVerifyRequest(@PathVariable String username) {
        verifyUserService.acceptVerifyUser(username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Secured(AuthoritiesConstants.ROLE_ADMIN)
    @PostMapping("/verify-user/request-update/{username}")
    public ResponseEntity<HttpStatus> adminRequestUpdateVerifyRequest(@PathVariable String username) {
        verifyUserService.adminRequestUpdate(username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
