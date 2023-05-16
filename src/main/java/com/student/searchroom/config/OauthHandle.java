package com.student.searchroom.config;

import com.student.searchroom.entity.CustomOAuth2User;
import com.student.searchroom.entity.User;
import com.student.searchroom.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OauthHandle implements AuthenticationSuccessHandler {

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private UserService userService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        String email = "";
        String name = "";
        String avatar = "";
        User.Provider provider = User.Provider.LOCAL;
        if (principal instanceof DefaultOidcUser) {
            DefaultOidcUser oauthUser = (DefaultOidcUser) principal;
            email = oauthUser.getAttribute("email");
            name = oauthUser.getAttribute("name");
            avatar = oauthUser.getAttribute("picture");
            provider = User.Provider.GOOGLE;
        } else if (principal instanceof CustomOAuth2User) {
            CustomOAuth2User oauthUser = (CustomOAuth2User) principal;
            email = oauthUser.getEmail();
            name = oauthUser.getName();
            avatar = oauthUser.getAvatar();
            provider = User.Provider.FACEBOOK;
        }

        userService.processOAuthPostLogin(provider.name() + email, name, avatar, provider);

        final String token = jwtToken.generateToken(provider.name() + email);

        httpServletResponse.sendRedirect("http://localhost:3000/login?token=" + token);
    }
}
