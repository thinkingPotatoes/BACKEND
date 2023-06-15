package com.talkingPotatoes.potatoesProject.user.dto.request;

import java.util.List;
import java.util.UUID;

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

	@NotBlank(message = "닉네임은 필수 입력사항입니다.")
	@Pattern(regexp = "[ㄱ-ㅎ가-힣a-zA-Z0-9]{2,9}",
		message = "닉네임은 한글, 영문, 숫자만 가능하며 2 ~ 10자리까지 가능합니다.")
	private String nickname;

	@NotBlank(message = "블로그명은 필수 입력사항입니다.")
	private String title;

	private List<UUID> genreList;
}
