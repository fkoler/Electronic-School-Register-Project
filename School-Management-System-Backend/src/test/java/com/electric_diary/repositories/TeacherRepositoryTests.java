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

import com.electric_diary.entities.TeacherEntity;

@ExtendWith(MockitoExtension.class)
public class TeacherRepositoryTests {
	@Mock
	private TeacherRepository teacherRepository;

	@Test
	public void TeacherRepository_SaveAll_ReturnSavedTeacher() {
		// Arrange
		TeacherEntity teacher = TeacherEntity.builder()
				.id(1)
				.firstName("Milan")
				.lastName("Čeliković")
				.build();
		
		Mockito.when(teacherRepository.save(teacher)).thenReturn(teacher);

		// Act
		TeacherEntity result = teacherRepository.save(teacher);

		// Assert
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getId()).isGreaterThan(0);
	}
	
	@Test
	public void TeacherRepository_FindAll_ReturnMoreThanOneTeacher() {
	    // Arrange
		TeacherEntity firstTeacher = TeacherEntity.builder()
				.firstName("Milan")
				.lastName("Čeliković")
				.build();
		
		TeacherEntity secondTeacher = TeacherEntity.builder()
				.firstName("Slavica")
				.lastName("Kordić")
				.build();
	    
	    List<TeacherEntity> teacherList = Arrays.asList(firstTeacher, secondTeacher);

	    Mockito.when(teacherRepository.findAll()).thenReturn(teacherList);
	    
	    // Act
	    Iterable<TeacherEntity> result = teacherRepository.findAll();
	    
	    // Assert
	    Assertions.assertThat(result).isNotNull();
	    Assertions.assertThat(result).hasSize(2);
	    Assertions.assertThat(result).containsExactlyInAnyOrder(firstTeacher, secondTeacher);
	}
	
	@Test
	public void TeacherRepository_FindById_ReturnTeacher() {
	    // Arrange
		TeacherEntity teacher = TeacherEntity.builder()
				.firstName("Milan")
				.lastName("Čeliković")
				.build();
	    
	    Mockito.when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));
	    
	    // Act
	    Optional<TeacherEntity> result = teacherRepository.findById(teacher.getId());
	    
	    // Assert
	    Assertions.assertThat(result).isPresent();
	    Assertions.assertThat(result.get()).isEqualTo(teacher);
	}
	
	@Test
	public void TeacherRepository_UpdateTeacher_ReturnsUpdatedTeacher() {
	    // Arrange
		TeacherEntity teacher = TeacherEntity.builder()
				.id(1)
				.firstName("Milan")
				.lastName("Čeliković")
				.build();
	    
	    Mockito.when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));
	    TeacherEntity resultSave = teacherRepository.findById(teacher.getId()).get();
	    
	    resultSave.setFirstName("Dinu");
	    resultSave.setLastName("Dragan");
	    
		Mockito.when(teacherRepository.save(resultSave)).thenReturn(resultSave);
	    
	    // Act
		TeacherEntity updatedTeacher = teacherRepository.save(resultSave);
	    
	    // Assert
	    Assertions.assertThat(updatedTeacher.getFirstName()).isEqualTo("Dinu");
	    Assertions.assertThat(updatedTeacher.getLastName()).isEqualTo("Dragan");
	}
	
	@Test
	public void TeacherRepository_DeleteTeacher_ReturnTeacherIsEmpty() {
	    // Arrange
		TeacherEntity teacher = TeacherEntity.builder()
				.id(1)
				.firstName("Milan")
				.lastName("Čeliković")
				.build();
	    
		teacherRepository.save(teacher);
	    
	    // Act
		teacherRepository.delete(teacher);
	    Optional<TeacherEntity> teacherReturn = teacherRepository.findById(teacher.getId());
	    
	    // Assert
	    Assertions.assertThat(teacherReturn).isEmpty();
	}
}
