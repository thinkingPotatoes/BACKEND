package com.talkingPotatoes.potatoesProject.user.service.implement;

import com.talkingPotatoes.potatoesProject.common.exception.NotFoundException;
import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.entity.Role;
import com.talkingPotatoes.potatoesProject.user.entity.User;
import com.talkingPotatoes.potatoesProject.user.mapper.UserMapper;
import com.talkingPotatoes.potatoesProject.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talkingPotatoes.potatoesProject.common.util.EmailUtil;
import com.talkingPotatoes.potatoesProject.user.service.EmailService;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final EmailUtil emailUtil;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    @Transactional
    public void sendSignUpMessage(UserDto toDto) throws Exception {
        String message = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <title>Document</title>\n" +
                "    <style>\n" +
                "      @import url(\"https://fonts.cdnfonts.com/css/sf-pro-display\");\n" +
                "\n" +
                "      @font-face {\n" +
                "        font-family: \"Pretendard-SemiBold\";\n" +
                "        src: url(\"https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-SemiBold.woff\")\n" +
                "          format(\"woff\");\n" +
                "        font-weight: 600;\n" +
                "        font-style: normal;\n" +
                "      }\n" +
                "\n" +
                "      @font-face {\n" +
                "        font-family: \"Pretendard-Medium\";\n" +
                "        src: url(\"https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Medium.woff\")\n" +
                "          format(\"woff\");\n" +
                "        font-weight: 600;\n" +
                "        font-style: normal;\n" +
                "      }\n" +
                "\n" +
                "      @font-face {\n" +
                "        font-family: \"Pretendard-Regular\";\n" +
                "        src: url(\"https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff\")\n" +
                "          format(\"woff\");\n" +
                "        font-weight: 400;\n" +
                "        font-style: normal;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <table width=\"600px\" height=\"534px\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "      <tr>\n" +
                "        <td>\n" +
                "          <table width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "            <tr>\n" +
                "              <td style=\"padding: 10px 24px 10px 24px; background: #111213\">\n" +
                "                <div style=\"display: flex; align-items: flex-end; gap: 16.67px\">\n" +
                "                  <div\n" +
                "                    style=\"\n" +
                "                      width: 60px;\n" +
                "                      height: 60px;\n" +
                "                      position: relative;\n" +
                "                      display: flex;\n" +
                "                      align-items: flex-start;\n" +
                "                    \"\n" +
                "                  >\n" +
                "                    <svg\n" +
                "                      xmlns=\"http://www.w3.org/2000/svg\"\n" +
                "                      width=\"60\"\n" +
                "                      height=\"60\"\n" +
                "                      viewBox=\"0 0 60 60\"\n" +
                "                      fill=\"none\"\n" +
                "                    >\n" +
                "                      <path\n" +
                "                        d=\"M14.1292 43.1538L16.015 40.5024L6.09375 38.9024L11.3898 32.4345L6.28266 30.0439L11.5156 27.4858V26.97L6.09375 20.8558L16.9274 20.0391L14.1603 15.9961L25.3409 16.2777L25.3241 16.2336L25.6454 16.2854L45.5832 16.7876L43.8521 19.2215L53.8477 20.8335L48.4258 27.455V27.4887L53.715 29.9645L48.4258 32.5501V32.7851L53.8477 38.8994L42.675 39.7417L45.5521 43.9453L14.1292 43.1538Z\"\n" +
                "                        fill=\"url(#paint0_linear_710_7932)\"\n" +
                "                      />\n" +
                "                      <defs>\n" +
                "                        <linearGradient\n" +
                "                          id=\"paint0_linear_710_7932\"\n" +
                "                          x1=\"5.84035\"\n" +
                "                          y1=\"17.0237\"\n" +
                "                          x2=\"55.4832\"\n" +
                "                          y2=\"40.211\"\n" +
                "                          gradientUnits=\"userSpaceOnUse\"\n" +
                "                        >\n" +
                "                          <stop stop-color=\"#00FFC2\" />\n" +
                "                          <stop offset=\"1\" stop-color=\"#E335FF\" />\n" +
                "                        </linearGradient>\n" +
                "                      </defs>\n" +
                "                    </svg>\n" +
                "                  </div>\n" +
                "                  <div\n" +
                "                    style=\"\n" +
                "                      flex: 1 1 0;\n" +
                "                      color: #c3c3c6;\n" +
                "                      font-size: 40px;\n" +
                "                      font-family: 'SF Pro Display', sans-serif;\n" +
                "                      font-weight: 400;\n" +
                "                      line-height: 52px;\n" +
                "                      word-wrap: break-word;\n" +
                "                    \"\n" +
                "                  >\n" +
                "                    Filmo\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "        <td>\n" +
                "          <table width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "            <tr>\n" +
                "              <td style=\"padding: 0 24px 0 24px; background: #f2f4f6\">\n" +
                "                <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                  <tr>\n" +
                "                    <td\n" +
                "                      style=\"\n" +
                "                        height: 369px;\n" +
                "                        display: flex;\n" +
                "                        flex-direction: column;\n" +
                "                        justify-content: flex-start;\n" +
                "                        align-items: center;\n" +
                "                        gap: 20px;\n" +
                "                      \"\n" +
                "                    >\n" +
                "                      <div\n" +
                "                        style=\"\n" +
                "                          width: 360px;\n" +
                "                          height: 317px;\n" +
                "                          padding: 40px;\n" +
                "                          background: white;\n" +
                "                          border-radius: 20px;\n" +
                "                          display: flex;\n" +
                "                          flex-direction: column;\n" +
                "                          justify-content: flex-start;\n" +
                "                          align-items: center;\n" +
                "                          gap: 40px;\n" +
                "                        \"\n" +
                "                      >\n" +
                "                        <div\n" +
                "                          style=\"\n" +
                "                            align-self: stretch;\n" +
                "                            text-align: center;\n" +
                "                            color: black;\n" +
                "                            font-size: 32px;\n" +
                "                            font-family: 'Pretendard-SemiBold';\n" +
                "                            letter-spacing: -0.32px;\n" +
                "                            line-height: 41.6px;\n" +
                "                            word-wrap: break-word;\n" +
                "                          \"\n" +
                "                        >\n" +
                "                          이메일 주소 인증\n" +
                "                        </div>\n" +
                "                        <div\n" +
                "                          style=\"\n" +
                "                            align-self: stretch;\n" +
                "                            text-align: center;\n" +
                "                            color: black;\n" +
                "                            font-size: 16px;\n" +
                "                            font-family: Pretendard-Regular;\n" +
                "                            font-weight: 400;\n" +
                "                            line-height: 20.8px;\n" +
                "                            word-wrap: break-word;\n" +
                "                          \"\n" +
                "                        >\n" +
                "                          Filmo 서비스 이용을 위해 이메일 주소 인증을\n" +
                "                          요청해요.<br />아래 버튼을 클릭하여 이메일 주소를\n" +
                "                          인증해 주시고,<br />Filmo 회원가입을 완료해 주세요!\n" +
                "                        </div>\n" +
                "                        <a\n" +
                "                          href='http://3.38.67.125:5100/register/success?token=" + emailUtil.createToken(toDto) + "'" +
                "                          style=\"\n" +
                "                            text-decoration: none;\n" +
                "                            width: 320px;\n" +
                "                            height: 52px;\n" +
                "                            padding: 8px;\n" +
                "                            background: #9087f4;\n" +
                "                            border-radius: 8px;\n" +
                "                            display: flex;\n" +
                "                            justify-content: center;\n" +
                "                            align-items: center;\n" +
                "                            gap: 8px;\n" +
                "                            border: none;\n" +
                "                            box-shadow: none;\n" +
                "                            overflow: visible;\n" +
                "                            cursor: pointer;\n" +
                "                          \"\n" +
                "                        >\n" +
                "                          <div\n" +
                "                            style=\"\n" +
                "                              text-align: center;\n" +
                "                              color: white;\n" +
                "                              font-size: 16px;\n" +
                "                              font-family: Pretendard-Medium;\n" +
                "                              font-weight: 500;\n" +
                "                              line-height: 20.8px;\n" +
                "                              word-wrap: break-word;\n" +
                "                            \"\n" +
                "                          >\n" +
                "                            이메일 주소를 인증합니다\n" +
                "                          </div>\n" +
                "                        </a>\n" +
                "                      </div>\n" +
                "                      <div\n" +
                "                        style=\"\n" +
                "                          width: 480px;\n" +
                "                          color: #4d4d59;\n" +
                "                          font-size: 12px;\n" +
                "                          font-family: Pretendard-Medium;\n" +
                "                          font-weight: 500;\n" +
                "                          line-height: 15.6px;\n" +
                "                          word-wrap: break-word;\n" +
                "                        \"\n" +
                "                      >\n" +
                "                        이메일 인증 제한 시간은 1시간이에요. 제한 시간 내로 메일\n" +
                "                        인증을 못 하셨다면 Filmo에서 다시 시도해 주세요.\n" +
                "                      </div>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </table>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>";

        emailUtil.createMessage(toDto.getUserId(), message);
    }

    @Override
    @Transactional
    public void verifyForSignUp(String token) {
        String id = emailUtil.checkToken(token);

        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("회원을 찾지 못하였습니다."));
        user.updateRole(Role.ACTIVE);

        userRepository.save(user);
    }

    @Override
    public void sendEmailForSignUp(String userId) throws Exception {
        sendSignUpMessage(userMapper.toDto(userRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("회원을 찾지 못하였습니다."))));
    }

    @Override
    @Transactional
    public void sendEmailForPassword(String email) throws Exception {
        sendResetPasswordMessage(userMapper.toDto(userRepository.findByUserId(email).orElseThrow(() -> new NotFoundException("회원을 찾지 못하였습니다."))));
    }

    private void sendResetPasswordMessage(UserDto toDto) throws Exception {
        String message = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <title>Document</title>\n" +
                "    <style>\n" +
                "      @import url(\"https://fonts.cdnfonts.com/css/sf-pro-display\");\n" +
                "\n" +
                "      @font-face {\n" +
                "        font-family: \"Pretendard-SemiBold\";\n" +
                "        src: url(\"https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-SemiBold.woff\")\n" +
                "          format(\"woff\");\n" +
                "        font-weight: 600;\n" +
                "        font-style: normal;\n" +
                "      }\n" +
                "\n" +
                "      @font-face {\n" +
                "        font-family: \"Pretendard-Medium\";\n" +
                "        src: url(\"https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Medium.woff\")\n" +
                "          format(\"woff\");\n" +
                "        font-weight: 600;\n" +
                "        font-style: normal;\n" +
                "      }\n" +
                "\n" +
                "      @font-face {\n" +
                "        font-family: \"Pretendard-Regular\";\n" +
                "        src: url(\"https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff\")\n" +
                "          format(\"woff\");\n" +
                "        font-weight: 400;\n" +
                "        font-style: normal;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <table width=\"600px\" height=\"534px\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "      <tr>\n" +
                "        <td>\n" +
                "          <table width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "            <tr>\n" +
                "              <td style=\"padding: 10px 24px 10px 24px; background: #111213\">\n" +
                "                <div style=\"display: flex; align-items: flex-end; gap: 16.67px\">\n" +
                "                  <div\n" +
                "                    style=\"\n" +
                "                      width: 60px;\n" +
                "                      height: 60px;\n" +
                "                      position: relative;\n" +
                "                      display: flex;\n" +
                "                      align-items: flex-start;\n" +
                "                    \"\n" +
                "                  >\n" +
                "                    <svg\n" +
                "                      xmlns=\"http://www.w3.org/2000/svg\"\n" +
                "                      width=\"60\"\n" +
                "                      height=\"60\"\n" +
                "                      viewBox=\"0 0 60 60\"\n" +
                "                      fill=\"none\"\n" +
                "                    >\n" +
                "                      <path\n" +
                "                        d=\"M14.1292 43.1538L16.015 40.5024L6.09375 38.9024L11.3898 32.4345L6.28266 30.0439L11.5156 27.4858V26.97L6.09375 20.8558L16.9274 20.0391L14.1603 15.9961L25.3409 16.2777L25.3241 16.2336L25.6454 16.2854L45.5832 16.7876L43.8521 19.2215L53.8477 20.8335L48.4258 27.455V27.4887L53.715 29.9645L48.4258 32.5501V32.7851L53.8477 38.8994L42.675 39.7417L45.5521 43.9453L14.1292 43.1538Z\"\n" +
                "                        fill=\"url(#paint0_linear_710_7932)\"\n" +
                "                      />\n" +
                "                      <defs>\n" +
                "                        <linearGradient\n" +
                "                          id=\"paint0_linear_710_7932\"\n" +
                "                          x1=\"5.84035\"\n" +
                "                          y1=\"17.0237\"\n" +
                "                          x2=\"55.4832\"\n" +
                "                          y2=\"40.211\"\n" +
                "                          gradientUnits=\"userSpaceOnUse\"\n" +
                "                        >\n" +
                "                          <stop stop-color=\"#00FFC2\" />\n" +
                "                          <stop offset=\"1\" stop-color=\"#E335FF\" />\n" +
                "                        </linearGradient>\n" +
                "                      </defs>\n" +
                "                    </svg>\n" +
                "                  </div>\n" +
                "                  <div\n" +
                "                    style=\"\n" +
                "                      flex: 1 1 0;\n" +
                "                      color: #c3c3c6;\n" +
                "                      font-size: 40px;\n" +
                "                      font-family: 'SF Pro Display', sans-serif;\n" +
                "                      font-weight: 400;\n" +
                "                      line-height: 52px;\n" +
                "                      word-wrap: break-word;\n" +
                "                    \"\n" +
                "                  >\n" +
                "                    Filmo\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "        <td>\n" +
                "          <table width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "            <tr>\n" +
                "              <td style=\"padding: 0 24px 0 24px; background: #f2f4f6\">\n" +
                "                <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                  <tr>\n" +
                "                    <td\n" +
                "                      style=\"\n" +
                "                        height: 369px;\n" +
                "                        display: flex;\n" +
                "                        flex-direction: column;\n" +
                "                        justify-content: flex-start;\n" +
                "                        align-items: center;\n" +
                "                        gap: 20px;\n" +
                "                      \"\n" +
                "                    >\n" +
                "                      <div\n" +
                "                        style=\"\n" +
                "                          width: 360px;\n" +
                "                          height: 317px;\n" +
                "                          padding: 40px;\n" +
                "                          background: white;\n" +
                "                          border-radius: 20px;\n" +
                "                          display: flex;\n" +
                "                          flex-direction: column;\n" +
                "                          justify-content: flex-start;\n" +
                "                          align-items: center;\n" +
                "                          gap: 40px;\n" +
                "                        \"\n" +
                "                      >\n" +
                "                        <div\n" +
                "                          style=\"\n" +
                "                            align-self: stretch;\n" +
                "                            text-align: center;\n" +
                "                            color: black;\n" +
                "                            font-size: 32px;\n" +
                "                            font-family: 'Pretendard-SemiBold';\n" +
                "                            letter-spacing: -0.32px;\n" +
                "                            line-height: 41.6px;\n" +
                "                            word-wrap: break-word;\n" +
                "                          \"\n" +
                "                        >\n" +
                "                          비밀번호 재설정\n" +
                "                        </div>\n" +
                "                        <div\n" +
                "                          style=\"\n" +
                "                            align-self: stretch;\n" +
                "                            text-align: center;\n" +
                "                            color: black;\n" +
                "                            font-size: 16px;\n" +
                "                            font-family: Pretendard-Regular;\n" +
                "                            font-weight: 400;\n" +
                "                            line-height: 20.8px;\n" +
                "                            word-wrap: break-word;\n" +
                "                          \"\n" +
                "                        >\n" +
                "                          Filmo에서 회원님의 비밀번호 재설정 링크를 보냈어요!<br />\n" +
                "                          회원님이 요청한 경우에만, 아래 버튼을 눌러\n" +
                "                          비밀번호를<br />\n" +
                "                          새로 설정해 주세요!\n" +
                "                        </div>\n" +
                "                        <a\n" +
                "                          href='http://3.38.67.125:5100/password/reset?token=" + emailUtil.createToken(toDto) + "'" +
                "                          style=\"\n" +
                "                            text-decoration: none;\n" +
                "                            width: 320px;\n" +
                "                            height: 52px;\n" +
                "                            padding: 8px;\n" +
                "                            background: #9087f4;\n" +
                "                            border-radius: 8px;\n" +
                "                            display: flex;\n" +
                "                            justify-content: center;\n" +
                "                            align-items: center;\n" +
                "                            gap: 8px;\n" +
                "                            border: none;\n" +
                "                            box-shadow: none;\n" +
                "                            overflow: visible;\n" +
                "                            cursor: pointer;\n" +
                "                          \"\n" +
                "                        >\n" +
                "                          <div\n" +
                "                            style=\"\n" +
                "                              text-align: center;\n" +
                "                              color: white;\n" +
                "                              font-size: 16px;\n" +
                "                              font-family: Pretendard-Medium;\n" +
                "                              font-weight: 500;\n" +
                "                              line-height: 20.8px;\n" +
                "                              word-wrap: break-word;\n" +
                "                            \"\n" +
                "                          >\n" +
                "                            비밀번호 재설정하기\n" +
                "                          </div>\n" +
                "                        </a>\n" +
                "                      </div>\n" +
                "                      <div\n" +
                "                        style=\"\n" +
                "                          width: 480px;\n" +
                "                          color: #4d4d59;\n" +
                "                          font-size: 12px;\n" +
                "                          font-family: Pretendard-Medium;\n" +
                "                          font-weight: 500;\n" +
                "                          line-height: 15.6px;\n" +
                "                          word-wrap: break-word;\n" +
                "                        \"\n" +
                "                      >\n" +
                "                        만약 본인이 요청한 것이 아닌 경우, 본 메일을 무시해\n" +
                "                        주시기 바랍니다.<br />\n" +
                "                        비밀번호 재설정 제한 시간은 1시간이에요. 제한 시간 내\n" +
                "                        못하셨다면, Filmo > 설정 > 비밀번호 변경에서 다시 시도해 주세요.\n" +
                "                      </div>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </table>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>";

        emailUtil.createMessage(toDto.getUserId(), message);
    }

    @Override
    @Transactional
    public void verifyForPassword(String token, String password) {
        String id = emailUtil.checkToken(token);

        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("회원을 찾지 못하였습니다."));
        user.updatePassword(password);

        userRepository.save(user);
    }
}