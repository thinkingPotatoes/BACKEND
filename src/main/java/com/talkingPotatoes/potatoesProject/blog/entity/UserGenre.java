package com.talkingPotatoes.potatoesProject.blog.entity;

import java.util.UUID;

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
public class UserGenre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@UuidGenerator
	private UUID id;

	@Column(nullable = false)
	private UUID userId;

	@Column(nullable = false)
	private UUID genreId;
}
