package com.ssafy.trip.domain.member.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ssafy.trip.domain.auth.model.enums.Role;

import lombok.*;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
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

	public void encodePassword(String encodedPassword) {
		this.password = encodedPassword;
	}

	public void update(String name, String profileImage, LocalDate birthDate) {
		this.name = name;
		this.profileImage = profileImage;
		this.birthDate = birthDate;
	}
	
	public void delete() {
		this.deletedAt = LocalDateTime.now();
	}

	public boolean isDeleted() {
		return this.deletedAt != null;
	}
}
