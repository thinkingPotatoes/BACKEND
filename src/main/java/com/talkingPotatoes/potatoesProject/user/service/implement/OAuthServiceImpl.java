package com.talkingPotatoes.potatoesProject.user.service.implement;

import com.talkingPotatoes.potatoesProject.common.exception.NotFoundException;
import com.talkingPotatoes.potatoesProject.user.dto.Auth;
import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.dto.UserGenreDto;
import com.talkingPotatoes.potatoesProject.user.entity.Platform;
import com.talkingPotatoes.potatoesProject.user.entity.User;
import com.talkingPotatoes.potatoesProject.user.mapper.UserGenreMapper;
import com.talkingPotatoes.potatoesProject.user.mapper.UserMapper;
import com.talkingPotatoes.potatoesProject.user.oauth.GoogleOAuth;
import com.talkingPotatoes.potatoesProject.user.repository.UserGenreRepository;
import com.talkingPotatoes.potatoesProject.user.repository.UserRepository;
import com.talkingPotatoes.potatoesProject.user.service.OAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OAuthServiceImpl implements OAuthService {

    private final UserRepository userRepository;
    private final UserGenreRepository userGenreRepository;

    private final UserMapper userMapper;
    private final UserGenreMapper userGenreMapper;

    private final GoogleOAuth googleOAuth;

    private final HttpServletResponse response;

    @Override
    public void request(Platform socialLoginPath) throws IOException {
        String redirectURL;

        switch (socialLoginPath) {
            case GOOGLE:
                redirectURL = googleOAuth.getOauthRedirectURL();
                log.info("OAuthServiceImpl:: request google redirect " + redirectURL);
                break;
            case NAVER:
                redirectURL = null;
                log.info("OAuthServiceImpl:: request naver redirect " + redirectURL);
                break;
            case KAKAO:
                redirectURL = null;
                log.info("OAuthServiceImpl:: request kakao redirect " + redirectURL);
                break;
            default:
                throw new NotFoundException("알 수 없는 소셜 로그인 형식입니다.");
        }

        log.info("OAuthServiceImpl:: request finish");

        response.sendRedirect(redirectURL);
    }

    @Override
    public Auth oAuthLogin(Platform socialLoginPath, String code) {
        log.info("OAuthServiceImpl:: request start");

        switch (socialLoginPath) {
            case GOOGLE: {

                break;
            }
            case NAVER: {
                break;
            }
            case KAKAO: {
                break;
            }
            default:
                throw new NotFoundException("알 수 없는 소셜 로그인 형식입니다.");
        }

        log.info("OAuthServiceImpl:: request start");
        return null;
    }

    @Override
    @Transactional
    public void oAuthContinueSignUp(UserDto userDto, List<UserGenreDto> userGenreDtoList) {
        log.info("OAuthServiceImpl:: oAuthContinueSignUp start");

        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new NotFoundException("등록된 회원이 아닙니다."));
        user.continueSignUp(userDto.getNickname(), userDto.getTitle());
        userRepository.save(user);
        log.info("OAuthServiceImpl:: oAuthContinueSignUp " + user.getId() + " saved");

        for (UserGenreDto dto : userGenreDtoList) {
            dto.setUserId(user.getId());
        }

        userGenreRepository.saveAll(userGenreMapper.toEntity(userGenreDtoList));
        log.info("OAuthServiceImpl:: oAuthContinueSignUp " + user.getId() + " userGenre saved");

        log.info("OAuthServiceImpl:: oAuthContinueSignUp finish");
    }
}
