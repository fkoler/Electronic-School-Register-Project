package com.electric_diary.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class TeacherEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull(message = "First name must be provided.")
	@Size(min = 2, max = 30, message = "First name must be between {min} and {max} characters long.")
	private String firstName;

	@NotNull(message = "Last name must be provided.")
	@Size(min = 2, max = 30, message = "Last name must be between {min} and {max} characters long.")
	private String lastName;

	@JsonIgnore
	@OneToMany(mappedBy = "teacher")
	private Set<GradeEntity> grades = new HashSet<GradeEntity>();

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "teacher_subject", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
	private Set<SubjectEntity> subjects = new HashSet<>();

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private ClassEntity newClass;
	
	@JsonIgnore
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user", nullable = false)
    protected UserEntity user;

	public void addSubjects(SubjectEntity subject) {
		subjects.add(subject);
		subject.getTeachers().add(this);
	}
}
