package com.student.searchroom.config;

import com.google.gson.Gson;
import com.student.searchroom.exception.BannedUserException;
import com.student.searchroom.model.error.ErrorCode;
import com.student.searchroom.model.response.ErrorResponse;
import com.student.searchroom.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@Log4j2
public class JwtFilter extends GenericFilterBean {

    private UserService jwtUserDetailsService;
    private JwtToken jwtToken;
    private Gson gson = new Gson();

    public JwtFilter(UserService jwtUserDetailsService, JwtToken jwtToken) {
        this.jwtToken = jwtToken;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            if (jwtToken.equals("null"))
                jwtToken = null;
            try {
                username = this.jwtToken.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            } catch (Exception e) {
                System.out.println("invalid token!");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = null;
            try {
                userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
            } catch (BannedUserException e) {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().println(gson.toJson(new ErrorResponse(ErrorCode.BANNED_USER)));
                response.getWriter().close();
                return;
            }
            log.info("user details {}", userDetails);

            if (this.jwtToken.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
