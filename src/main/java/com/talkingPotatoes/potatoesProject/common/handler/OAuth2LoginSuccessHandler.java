package com.talkingPotatoes.potatoesProject.common.handler;

import com.talkingPotatoes.potatoesProject.common.jwt.JwtTokenProvider;
import com.talkingPotatoes.potatoesProject.user.dto.TokenDto;
import com.talkingPotatoes.potatoesProject.user.entity.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        try {

            CustomOAuth2User user = (CustomOAuth2User) authentication.getPrincipal();

            loginSuccess(response, user);
        } catch (Exception e) {
            throw e;
        }
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User user) throws IOException {
        TokenDto tokenDto = jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRole());

        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Authorization", tokenDto.getAccessToken());

        ResponseCookie cookie = ResponseCookie.from("refreshToken", tokenDto.getRefreshToken())
                .maxAge(1209600000)
                .path("/")
                .secure(false)
                .httpOnly(true)
                .build();

        response.setHeader("Set-Cookie", cookie.toString());

    }
}