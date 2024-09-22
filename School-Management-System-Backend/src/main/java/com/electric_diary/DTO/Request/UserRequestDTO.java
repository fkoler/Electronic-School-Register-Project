package com.electric_diary.DTO.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDTO {
	private String email;
	private String lastName;
	private String name;
	private String password;
	private Integer role;
}
