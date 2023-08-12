package com.talkingPotatoes.potatoesProject.user.service.implement;

import com.talkingPotatoes.potatoesProject.user.entity.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.entity.User;
import com.talkingPotatoes.potatoesProject.blog.mapper.UserGenreMapper;
import com.talkingPotatoes.potatoesProject.user.mapper.UserMapper;
import com.talkingPotatoes.potatoesProject.blog.repository.UserGenreRepository;
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
	public UserDto signUp(UserDto userDto) {
		if (userDto.getRole() == null) userDto.setRole(Role.ACTIVE);
		User user = userRepository.save(userMapper.toEntity(userDto));

        return userMapper.toDto(user);
    }
}
