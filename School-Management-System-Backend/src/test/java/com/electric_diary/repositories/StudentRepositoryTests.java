package com.electric_diary.repositories;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.electric_diary.entities.ClassEntity;
import com.electric_diary.entities.StudentEntity;

@ExtendWith(MockitoExtension.class)
public class StudentRepositoryTests {
	@Mock
	private StudentRepository studentRepository;
	
	public ClassEntity firstClass = new ClassEntity();
	public ClassEntity secondClass = new ClassEntity();

	@Test
	public void StudentRepository_SaveAll_ReturnSavedStudent() {
		// Arrange
		StudentEntity student = StudentEntity.builder()
				.id(1)
				.firstName("Nikola")
				.lastName("Vetnić")
				.newClass(firstClass)
				.build();
		
		Mockito.when(studentRepository.save(student)).thenReturn(student);

		// Act
		StudentEntity result = studentRepository.save(student);

		// Assert
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getId()).isGreaterThan(0);
	}
	
	@Test
	public void StudentRepository_FindAll_ReturnMoreThanOneStudent() {
	    // Arrange
		StudentEntity firstStudent = StudentEntity.builder()
				.firstName("Nikola")
				.lastName("Vetnić")
				.newClass(firstClass)
				.build();
		
		StudentEntity secondStudent = StudentEntity.builder()
				.firstName("Nikola")
				.lastName("Dmitrašinović")
				.newClass(secondClass)
				.build();
	    
	    List<StudentEntity> studentList = Arrays.asList(firstStudent, secondStudent);

	    Mockito.when(studentRepository.findAll()).thenReturn(studentList);
	    
	    // Act
	    Iterable<StudentEntity> result = studentRepository.findAll();
	    
	    // Assert
	    Assertions.assertThat(result).isNotNull();
	    Assertions.assertThat(result).hasSize(2);
	    Assertions.assertThat(result).containsExactlyInAnyOrder(firstStudent, secondStudent);
	}
	
	@Test
	public void StudentRepository_FindById_ReturnStudent() {
	    // Arrange
		StudentEntity student = StudentEntity.builder()
				.firstName("Nikola")
				.lastName("Vetnić")
				.newClass(firstClass)
				.build();
	    
	    Mockito.when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
	    
	    // Act
	    Optional<StudentEntity> result = studentRepository.findById(student.getId());
	    
	    // Assert
	    Assertions.assertThat(result).isPresent();
	    Assertions.assertThat(result.get()).isEqualTo(student);
	}
	
	@Test
	public void StudentRepository_UpdateStudent_ReturnsUpdatedStudent() {
	    // Arrange
		StudentEntity student = StudentEntity.builder()
				.id(1)
				.firstName("Nikola")
				.lastName("Vetnić")
				.newClass(firstClass)
				.build();
	    
	    Mockito.when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
	    StudentEntity resultSave = studentRepository.findById(student.getId()).get();
	    
	    resultSave.setFirstName("Daniel");
	    resultSave.setLastName("Divjaković");
	    
		Mockito.when(studentRepository.save(resultSave)).thenReturn(resultSave);
	    
	    // Act
		StudentEntity updatedStudent = studentRepository.save(resultSave);
	    
	    // Assert
	    Assertions.assertThat(updatedStudent.getFirstName()).isEqualTo("Daniel");
	    Assertions.assertThat(updatedStudent.getLastName()).isEqualTo("Divjaković");
	}
	
	@Test
	public void StudentRepository_DeleteStudent_ReturnStudentIsEmpty() {
	    // Arrange
		StudentEntity student = StudentEntity.builder()
				.id(1)
				.firstName("Nikola")
				.lastName("Vetnić")
				.newClass(firstClass)
				.build();
	    
		studentRepository.save(student);
	    
	    // Act
		studentRepository.delete(student);
	    Optional<StudentEntity> studentReturn = studentRepository.findById(student.getId());
	    
	    // Assert
	    Assertions.assertThat(studentReturn).isEmpty();
	}
}
