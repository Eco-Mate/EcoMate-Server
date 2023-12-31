package com.greeny.ecomate.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greeny.ecomate.exception.TokenNotFoundException;
import com.greeny.ecomate.utils.api.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {

        HttpServletResponse res = (HttpServletResponse) response;

        try {
            filterChain.doFilter(request, response);
        }catch(JWTVerificationException e) {
            logger.error(e.getMessage());

            String body = objectMapper
                    .writeValueAsString(ApiUtil.error(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰"));

            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(body);
        } catch (TokenNotFoundException e) {
            String body = objectMapper
                    .writeValueAsString(ApiUtil.error(HttpServletResponse.SC_BAD_REQUEST, e.getMessage()));

            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(body);
        }

    }
}
