package com.student.searchroom.controller;

import com.student.searchroom.auth.AuthoritiesConstants;
import com.student.searchroom.model.request.BanUserRequest;
import com.student.searchroom.model.request.SetAuthorityRequest;
import com.student.searchroom.model.response.UserResponse;
import com.student.searchroom.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Secured(AuthoritiesConstants.ROLE_ADMIN)
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getUsersAndPaging(@RequestParam(name = "page_index", required = false, defaultValue = "1") int pageIndex,
                                                                @RequestParam(name = "page_size", required = false, defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(adminService.getUserAndPaging(pageIndex, pageSize));
    }


    @Secured(AuthoritiesConstants.ROLE_ADMIN)
    @PostMapping("/authority")
    public ResponseEntity<List<UserResponse>> setAuthority(@RequestBody @Valid SetAuthorityRequest request) {
        adminService.addAuthorityWithUsername(request.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Secured(AuthoritiesConstants.ROLE_ADMIN)
    @PostMapping("/ban")
    public ResponseEntity<UserResponse> banUser(@RequestBody @Valid BanUserRequest request) {
        return ResponseEntity.ok(adminService.banUser(request.getUsername()));
    }
}
