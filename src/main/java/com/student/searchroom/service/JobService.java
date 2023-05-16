package com.student.searchroom.service;

import com.student.searchroom.model.request.RegisterUserRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Log4j2
public class JobService {
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

    @Value("${app.admin.username}")
    private String usernameAdmin;
    @Value("${app.admin.password}")
    private String passwordAdmin;

    @PostConstruct
    private void registerAdmin() {
        if (userService.findByUsername(this.usernameAdmin).isPresent()) return;
        RegisterUserRequest request = new RegisterUserRequest();
        request.setEmail("admin@gmail.com");
        request.setPhone("0826662323");
        request.setFullName("ADMIN");
        request.setPassword(this.passwordAdmin);
        request.setUsername(this.usernameAdmin);
        userService.registrationUser(request);

        adminService.addAuthorityWithUsername(this.usernameAdmin);
        log.info("Register admin done!");
    }

}
