package com.talkingPotatoes.potatoesProject.user.entity;

import java.sql.Types;
import java.util.UUID;

import com.talkingPotatoes.potatoesProject.common.entity.BaseEntity;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserGenre extends BaseEntity {

	@Id
	@GeneratedValue
	@UuidGenerator
	@JdbcTypeCode(Types.VARCHAR)
	private UUID id;

	@Column(nullable = false)
	@JdbcTypeCode(Types.VARCHAR)
	private UUID userId;

	@Column(nullable = false)
	@JdbcTypeCode(Types.VARCHAR)
	private UUID genreId;
}
