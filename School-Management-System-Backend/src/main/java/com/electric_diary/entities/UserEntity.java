package com.electric_diary.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "user")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	protected Integer id;

	@Column(nullable = false, name = "name")
	@NotBlank(message = "Name cannot be blank")
	@NotNull(message = "Name name must be provided.")
	@Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.")
	protected String name;

	@NotBlank(message = "Last name cannot be blank")
	@NotNull(message = "Last name must be provided.")
	@Size(min = 2, max = 30, message = "Last name must be between {min} and {max} characters long.")
	@Column(name = "last_name")
	protected String lastName;

	@NotBlank(message = "Password cannot be blank")
	@NotNull(message = "Password must be provided.")
	@Size(min = 2, max = 30, message = "Password must be between {min} and {max} characters long.")
	@Column(name = "password")
	protected String password;

	@Column(unique = true, name = "email")
	protected String email;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "role")
	protected RoleEntity role;
	
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    protected TeacherEntity teacher;
    
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    protected ParentEntity parent;
    
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    protected StudentEntity student;
}