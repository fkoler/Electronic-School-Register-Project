package com.electric_diary.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
public class SubjectEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;
	
	@Min(0)
	private Integer weeklyFund;

	@NotNull
	@JsonIgnore
	@OneToMany(mappedBy = "subject", fetch = FetchType.EAGER)
	private Set<GradeEntity> grades = new HashSet<GradeEntity>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "student_enrolled", joinColumns = @JoinColumn(name = "subject_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
	private Set<StudentEntity> enrolledStudents = new HashSet<>();

	@ManyToMany(mappedBy = "subjects", fetch = FetchType.EAGER)
	private Set<TeacherEntity> teachers = new HashSet<>();

	public void enrolStudents(StudentEntity student) {
		enrolledStudents.add(student);
	}

	public void addTeacher(TeacherEntity teacher) {
		teachers.add(teacher);
		teacher.getSubjects().add(this);
	}
}
