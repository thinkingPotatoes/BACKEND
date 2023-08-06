package com.talkingPotatoes.potatoesProject.user.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.talkingPotatoes.potatoesProject.common.entity.BaseEntity;
import io.jsonwebtoken.Claims;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

	public void updatePassword(String password) {
		if (password != null) {
			this.password = password;
		}
	}

	public void updateEmailChecked(boolean checked) {
		emailChecked = checked;
	}

	public void continueSignUp(String nickname, String title) {
		this.nickname = nickname;
		this.title = title;
	}

	public User(Claims claims) {
		this.id = UUID.fromString(claims.get("id").toString());
		this.userId = claims.get("userId").toString();
		this.role = Role.valueOf(claims.get("role").toString());
	}
}
