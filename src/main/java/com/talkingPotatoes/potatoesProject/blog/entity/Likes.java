package com.talkingPotatoes.potatoesProject.blog.entity;

import com.talkingPotatoes.potatoesProject.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(LikesId.class)
public class Likes extends BaseEntity {

    @Id
    private UUID userId;

    @Id
    private UUID articleId;

    private boolean clicked;
}

