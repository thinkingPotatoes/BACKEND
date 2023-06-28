package com.talkingPotatoes.potatoesProject.blog.entity;

import com.talkingPotatoes.potatoesProject.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Types;
import java.util.UUID;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;

    @Column(unique = true, nullable = false)
    private UUID articleId;

    @Column(unique = true, nullable = false)
    private UUID userId;

    private String content;
}
