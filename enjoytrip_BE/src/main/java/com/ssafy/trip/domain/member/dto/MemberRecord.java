package com.ssafy.trip.domain.member.dto;

import com.ssafy.trip.domain.auth.model.enums.Role;
import com.ssafy.trip.domain.member.model.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRecord {
    private Long id;
    private String email;
    private String password;
    private String name;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private LocalDateTime blockedAt;
    private LocalDate birthDate;
    private String profileImage;

    public Member toModel() {
        return Member.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .role(role)
                .createdAt(createdAt)
                .birthDate(birthDate)
                .profileImage(profileImage)
                .build();
    }
}
