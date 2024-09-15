package com.electric_diary.entities;

import com.electric_diary.enums.GradingType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class GradeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Min(value = 1, message = "Grade can't be less than {value}.")
	@Max(value = 5, message = "Grade can't be greater than {value}.")
	@NotNull(message = "Grade name must be provided.")
	private Integer grade;

	@NotNull
	@Enumerated(EnumType.STRING)
	private GradingType gradingType;

	@NotNull
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private StudentEntity student;

	@NotNull
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private TeacherEntity teacher;

	@NotNull
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private SubjectEntity subject;
}
