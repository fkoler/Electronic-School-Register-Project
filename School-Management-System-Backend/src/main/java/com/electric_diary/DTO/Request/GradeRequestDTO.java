package com.electric_diary.DTO.Request;

import com.electric_diary.enums.GradingType;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GradeRequestDTO {
	@Min(value = 1, message = "Grade can't be less than {value}.")
	@Max(value = 5, message = "Grade can't be greater than {value}.")
	private Integer grade;
	private Integer studentId;
	private Integer teacherId;
	private Integer subjectId;
	private GradingType gradingType;
}
