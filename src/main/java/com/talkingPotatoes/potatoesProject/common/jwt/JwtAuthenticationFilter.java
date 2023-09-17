package com.talkingPotatoes.potatoesProject.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talkingPotatoes.potatoesProject.common.exception.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    /**
     * JWT 토큰 검증
     * 만료된 토큰이 발견되었을 때, 만료된 토큰 응답 발생
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            Claims claims = jwtTokenProvider.resolveToken((HttpServletRequest) request);
            if (claims != null)
                SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(claims));
            filterChain.doFilter(request, response);
        } catch (SignatureException e) {
            throw new TokenValidFailedException("유효하지 않은 토큰입니다.");
        } catch (MalformedJwtException e) {
            throw new TokenValidFailedException("손상된 토큰입니다.");
        } catch (DecodingException e) {
            throw new TokenValidFailedException("잘못된 인증입니다.");
        } catch (ExpiredJwtException e) {
            throw new TokenValidFailedException("만료된 토큰입니다.");
        } catch (ServletException e) {
            throw new TokenValidFailedException("서블릿 오류입니다.");
        }
    }
}