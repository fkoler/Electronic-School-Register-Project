package com.electric_diary.entities;

import java.util.HashSet;
import java.util.Set;

import com.electric_diary.security.Views;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class StudentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.Student.class)
	private Integer id;

	@JsonView(Views.Student.class)
	private String firstName;

	@JsonView(Views.Parent.class)
	private String lastName;

	@NotNull
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonView(Views.Parent.class)
	private ClassEntity newClass;

	@NotNull
	@JsonIgnore
	@OneToMany(mappedBy = "student")
	private Set<GradeEntity> grades = new HashSet<GradeEntity>();

	@JsonIgnore
	@JsonView(Views.Parent.class)
	@ManyToMany(mappedBy = "enrolledStudents")
	private Set<SubjectEntity> subjects = new HashSet<>();

	@JsonView(Views.Parent.class)
	@ManyToOne(cascade = CascadeType.ALL)
	@NotNull(message = "Parent name must be provided.")
	@JoinColumn(name = "parent_id", referencedColumnName = "id")
	private ParentEntity parent;
	
	@JsonIgnore
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user", nullable = false)
    protected UserEntity user;
}
