package com.talkingPotatoes.potatoesProject.user.service.implement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.dto.UserGenreDto;
import com.talkingPotatoes.potatoesProject.user.entity.User;
import com.talkingPotatoes.potatoesProject.user.mapper.UserGenreMapper;
import com.talkingPotatoes.potatoesProject.user.mapper.UserMapper;
import com.talkingPotatoes.potatoesProject.user.repository.UserGenreRepository;
import com.talkingPotatoes.potatoesProject.user.repository.UserRepository;
import com.talkingPotatoes.potatoesProject.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserGenreRepository userGenreRepository;
	private final UserMapper userMapper;
	private final UserGenreMapper userGenreMapper;

	@Override
	@Transactional
	public UserDto signUp(UserDto userDto,
                          List<UserGenreDto> userGenreDtoList) {
		User user = userRepository.save(userMapper.toEntity(userDto));
		log.info(String.valueOf(user.getId()));
		for (UserGenreDto dto : userGenreDtoList) {
			dto.setUserId(user.getId());
		}

		userGenreRepository.saveAll(userGenreMapper.toEntity(userGenreDtoList));

        return userMapper.toDto(user);
    }
}
