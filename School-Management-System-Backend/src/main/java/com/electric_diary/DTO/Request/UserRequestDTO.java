package com.electric_diary.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDTO {
	@NotBlank(message = "Name cannot be blank")
	@NotNull(message = "Name name must be provided.")
	@Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.")
	private String name;
	
	@NotBlank(message = "Last name cannot be blank")
	@NotNull(message = "Last name must be provided.")
	@Size(min = 2, max = 30, message = "Last name must be between {min} and {max} characters long.")
	private String lastName;
	
	@NotBlank(message = "Password cannot be blank")
	@NotNull(message = "Password must be provided.")
	@Size(min = 2, max = 30, message = "Password must be between {min} and {max} characters long.")
	private String password;
	private String email;
	private Integer roleId;
}
