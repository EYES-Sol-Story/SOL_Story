package com.eyes.solstory.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRes extends LoginUser {
	private LoginUser loginUser;
	private boolean loginResult;

}
