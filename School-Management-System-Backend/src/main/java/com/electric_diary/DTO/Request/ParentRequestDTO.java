package com.electric_diary.DTO.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ParentRequestDTO {
	@NotBlank(message = "First name must be provided")
	@Size(min = 2, max = 30, message = "First name must be between {min} and {max} characters long.")
	private String firstName;
	
	@NotBlank(message = "Last name must be provided")
	@Size(min = 2, max = 30, message = "Last name must be between {min} and {max} characters long.")
	private String lastName;
	
	@NotBlank(message = "Email is mandatory")
	@Email(message = "Email should be valid")
	private String email;
	private Integer userId;
}
