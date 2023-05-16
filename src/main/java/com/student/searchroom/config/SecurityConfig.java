package com.student.searchroom.config;

import com.student.searchroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService jwtUserDetailsService;
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;
    @Autowired
    private OauthHandle oauthHandle;
    @Autowired
    private JwtToken jwtToken;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/auth/login", "/api/user", "/api/auth/change-password", "/api/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/posts/**", "/api/post/*", "/api/search", "/api/search/**", "/api/address/**"
                        , "/api/comment/post/*", "/api/rating/user/*", "/api/user/*").permitAll()
                .antMatchers("/api/user/login", "/api/test/*", "/ws/*", "/ws", "/ws/**").permitAll()
                .anyRequest().authenticated()
                .and().apply(securityConfigurerAdapter())
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().formLogin().permitAll()
                .and().oauth2Login().loginPage("/oauth_login")
                .userInfoEndpoint().userService(customOAuth2UserService)
                .and().successHandler(oauthHandle)
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private JwtConfigurer securityConfigurerAdapter() {
        return new JwtConfigurer(jwtUserDetailsService, jwtToken);
    }

}
