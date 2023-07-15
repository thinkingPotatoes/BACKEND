package com.talkingPotatoes.potatoesProject.user.entity;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

import com.talkingPotatoes.potatoesProject.common.entity.BaseEntity;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class User extends BaseEntity {
	@Id
	@GeneratedValue
	@UuidGenerator
	private UUID id;

	@Column(unique = true, nullable = false)
	private String userId;

	private String password;

	@Column(unique = true, nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Platform platform;

	private LocalDateTime deletedAt;

	@Column(columnDefinition = "boolean default false")
	private boolean emailChecked;

	public void updateEmailChecked(boolean checked) {
		emailChecked = checked;
	}

	public void continueSignUp(String nickname, String title) {
		this.nickname = nickname;
		this.title = title;
	}
}
