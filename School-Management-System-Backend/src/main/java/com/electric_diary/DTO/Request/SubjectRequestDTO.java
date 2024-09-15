package com.electric_diary.DTO.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubjectRequestDTO {
	private String name;
	private Integer weeklyFund;
}
