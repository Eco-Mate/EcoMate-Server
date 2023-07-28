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
    public SecurityFilterChain accountFilterChain(HttpSecurity http,
                                                  JwtProvider jwtProvider, ObjectMapper objectMapper) throws Exception {
        return setJwtHttpSecurity(http, objectMapper)
                .requestMatchers()
                .antMatchers("/api/v1/admin/**")
                .antMatchers("/api/v1/auth/members/**")
                .antMatchers("/api/v1/members/**")
                .antMatchers("/api/v1/challenges/**")
                .antMatchers(HttpMethod.POST, "/api/v1/auth/members/new")
                .antMatchers(HttpMethod.GET,"/api/v1/challenges")
                .antMatchers(HttpMethod.GET,"/api/v1/communities/{id}")
                .antMatchers(HttpMethod.GET,"/api/v1/notices/**")
                .antMatchers(HttpMethod.GET,"/api/v1/comments/**")
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .antMatchers("/api/v1/auth/profiles/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/accounts/**").hasAnyRole("USER", "MANAGER")
                .antMatchers("/api/v1/communities/**").hasAnyRole("USER", "MANAGER")
                .antMatchers("/api/v1/notices/**").hasAnyRole("USER", "MANAGER")
                .antMatchers("/api/v1/comments/**").hasAnyRole("USER", "MANAGER")
                .antMatchers("/api/v1/reports/**").hasAnyRole("USER", "MANAGER")
                .and()
                .addFilterAfter(jwtAuthenticationFilter(jwtProvider),
                        JwtExceptionFilter.class)
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
