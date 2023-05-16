package com.student.searchroom.service;

import com.student.searchroom.config.JwtToken;
import com.student.searchroom.entity.CodeForgotPass;
import com.student.searchroom.entity.User;
import com.student.searchroom.exception.SearchRoomException;
import com.student.searchroom.model.error.ErrorCode;
import com.student.searchroom.model.request.ChangePasswordRequest;
import com.student.searchroom.model.request.LoginRequest;
import com.student.searchroom.model.request.ResetPasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtToken jwtToken;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CodeForgotPassService codeForgotPassService;
    @Autowired
    private EmailService emailService;


    public void changePassword(ChangePasswordRequest request) {
        User userToChange = userService.getByUsername(request.getUsername());
        authenticate(request.getUsername(), request.getOldPassword());
        userToChange.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userService.save(userToChange);
    }

    public String validateUsernamePasswordAndGenToken(LoginRequest loginRequest) {
        authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        return jwtToken.generateToken(loginRequest.getUsername());
    }
    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new SearchRoomException(ErrorCode.BANNED_USER);
        } catch (BadCredentialsException e) {
            throw new SearchRoomException(ErrorCode.INVALID_USERNAME_OR_PASSWORD);
        }
    }

    public void forgotPasswordByUsername(String username) {
        User user = userService.getByUsername(username);
        CodeForgotPass codeForgotPass = codeForgotPassService.generateCode(username);
        emailService.sendMailForgotPassword(codeForgotPass.getCode(), user.getEmail(), username);
    }

    public void resetPassword(ResetPasswordRequest request) {
        User userToChange = userService.getByUsername(request.getUsername());
        codeForgotPassService.validateCode(request.getUsername(), request.getCode());
        userToChange.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userService.save(userToChange);

    }
}
