package com.greeny.ecomate.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.greeny.ecomate.exception.TokenNotFoundException;
import com.greeny.ecomate.security.provider.JwtProvider;
import com.greeny.ecomate.utils.jwt.JwtExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException, TokenNotFoundException, JWTVerificationException {

        HttpServletRequest req = (HttpServletRequest) request;

        if(req.getMethod().equals("OPTIONS")) {
            chain.doFilter(request, response);
            return;
        }

        String memberToken = JwtExtractor.extractJwt(req);

        String email = jwtProvider.getEmailFromToken(memberToken);
        Long memberId = jwtProvider.getMemberIdFromToken(memberToken);

        request.setAttribute("memberId", memberId);
        request.setAttribute("email", email);

        Authentication authenticate = jwtProvider.authenticate(new UsernamePasswordAuthenticationToken(email, " "));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        chain.doFilter(request, response);
    }
}
