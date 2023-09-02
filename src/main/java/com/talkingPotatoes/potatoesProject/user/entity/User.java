package com.talkingPotatoes.potatoesProject.user.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.talkingPotatoes.potatoesProject.common.entity.BaseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
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
@DynamicInsert
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public void updateRole(Role role) {
        this.role = role;
    }
    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateWithdraw() {
        this.deletedAt = LocalDateTime.now();
    }

    public void updateTitle(String title) {
        if (title.isBlank()) {
            this.title = this.nickname + "'s Filog";
        } else {
            this.title = title;
        }
    }

}
