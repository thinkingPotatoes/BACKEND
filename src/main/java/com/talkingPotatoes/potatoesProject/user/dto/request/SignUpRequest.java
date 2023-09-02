package com.talkingPotatoes.potatoesProject.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpRequest {

	@NotBlank(message = "아이디는 필수 입력사항입니다.")
	@Email
	private String userId;

	@NotBlank(message = "비밀번호는 필수 입력사항입니다.")
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,16}",
		message = "비밀번호는 영문과 숫자 조합으로 8 ~ 16자리까지 가능합니다.")
	private String password;
}
