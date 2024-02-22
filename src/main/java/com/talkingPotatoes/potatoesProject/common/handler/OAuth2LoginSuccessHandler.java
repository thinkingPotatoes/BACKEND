package com.talkingPotatoes.potatoesProject.common.handler;

import com.talkingPotatoes.potatoesProject.common.jwt.JwtTokenProvider;
import com.talkingPotatoes.potatoesProject.user.dto.TokenDto;
import com.talkingPotatoes.potatoesProject.user.entity.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        try {

            CustomOAuth2User user = (CustomOAuth2User) authentication.getPrincipal();

            String redirectUrl = loginSuccess(response, user);

            getRedirectStrategy().sendRedirect(request, response, redirectUrl);

        } catch (Exception e) {
            throw e;
        }
    }

    private String loginSuccess(HttpServletResponse response, CustomOAuth2User user) throws IOException {
        TokenDto tokenDto = jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRole());

        response.setStatus(HttpServletResponse.SC_OK);
        addCookie(response, "refreshToken", tokenDto.getRefreshToken(), 1209600000);

        return UriComponentsBuilder.fromUriString("http://3.38.67.125:5100/login/callback")
                .queryParam("accessToken", tokenDto.getAccessToken())
                .build().toUriString();

    }

    public void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);

        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
}