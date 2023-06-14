package com.talkingPotatoes.potatoesProject.user.entity;

import java.sql.Types;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Genre {

	@Id
	@GeneratedValue
	@UuidGenerator
	@JdbcTypeCode(Types.VARCHAR)
	private UUID id;

	@Column(unique = true, nullable = false)
	private String genre;
}
