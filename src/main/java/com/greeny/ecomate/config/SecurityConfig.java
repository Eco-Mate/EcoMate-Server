package com.greeny.ecomate.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greeny.ecomate.exception.handler.JwtAccessDeniedHandler;
import com.greeny.ecomate.exception.handler.JwtNotAuthenticatedHandler;
import com.greeny.ecomate.security.filter.JwtAuthenticationFilter;
import com.greeny.ecomate.security.filter.JwtExceptionFilter;
import com.greeny.ecomate.security.provider.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    JwtAuthenticationFilter jwtAuthenticationFilter(JwtProvider jwtProvider) {
        return new JwtAuthenticationFilter(jwtProvider);
    }

    JwtExceptionFilter jwtExceptionFilter(ObjectMapper objectMapper) {
        return new JwtExceptionFilter(objectMapper);
    }

    @Bean
    public SecurityFilterChain memberFilterChain(HttpSecurity http,
                                                  JwtProvider jwtProvider, ObjectMapper objectMapper) throws Exception {
        return setJwtHttpSecurity(http, objectMapper)
                .requestMatchers()
                .antMatchers("/v1/challenges/**")
                .antMatchers("/v1/myChallenges/**")
                .antMatchers("/v1/members/**")
                .antMatchers("/v1/boards/**")
                .antMatchers("/v1/board-saves/**")
                .antMatchers("/v1/comments/**")
                .antMatchers("/v1/chat-rooms/**")
                .antMatchers("/v1/chats/**")
                .antMatchers("/v1/follows/**")
                .antMatchers("/v1/fcm/**")
                .and()
                .authorizeRequests()
                .antMatchers("/v1/challenges/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/v1/myChallenges/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/v1/members/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/v1/boards/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/v1/board-saves/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/v1/comments/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/v1/chat-rooms/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/v1/chats/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("v1/follows/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/v1/fcm/**").hasAnyRole("USER", "ADMIN")
                .and()
                .addFilterAfter(jwtAuthenticationFilter(jwtProvider), JwtExceptionFilter.class)
                .build();
    }

    private HttpSecurity setJwtHttpSecurity(HttpSecurity http, ObjectMapper objectMapper) throws Exception{
        return http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new JwtNotAuthenticatedHandler(new ObjectMapper()))
                .accessDeniedHandler(new JwtAccessDeniedHandler(new ObjectMapper()))
                .and()
                .addFilterBefore(jwtExceptionFilter(objectMapper), UsernamePasswordAuthenticationFilter.class);
    }

}
