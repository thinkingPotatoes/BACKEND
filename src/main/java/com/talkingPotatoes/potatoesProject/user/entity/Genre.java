package com.talkingPotatoes.potatoesProject.user.entity;

import java.sql.Types;
import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Genre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@UuidGenerator
	private UUID id;

	@Column(unique = true, nullable = false)
	private String genre;
}
