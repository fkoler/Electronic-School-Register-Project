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

import com.electric_diary.entities.SubjectEntity;

@ExtendWith(MockitoExtension.class)
public class SubjectRepositoryTests {
	@Mock
	private SubjectRepository subjectRepository;

	@Test
	public void SubjectRepository_SaveAll_ReturnSavedSubject() {
		// Arrange
		SubjectEntity subject = SubjectEntity.builder()
				.id(1)
				.name("Mathetmatics")
				.weeklyFund(50)
				.build();
		
		Mockito.when(subjectRepository.save(subject)).thenReturn(subject);

		// Act
		SubjectEntity result = subjectRepository.save(subject);

		// Assert
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getId()).isGreaterThan(0);
	}
	
	@Test
	public void SubjectRepository_FindAll_ReturnMoreThanOneSubject() {
	    // Arrange
		SubjectEntity firstSubject = SubjectEntity.builder()
				.name("Mathetmatics")
				.weeklyFund(50)
				.build();
		
		SubjectEntity secondSubject = SubjectEntity.builder()
				.name("Backend")
				.weeklyFund(100)
				.build();
	    
	    List<SubjectEntity> subjectList = Arrays.asList(firstSubject, secondSubject);

	    Mockito.when(subjectRepository.findAll()).thenReturn(subjectList);
	    
	    // Act
	    Iterable<SubjectEntity> result = subjectRepository.findAll();
	    
	    // Assert
	    Assertions.assertThat(result).isNotNull();
	    Assertions.assertThat(result).hasSize(2);
	    Assertions.assertThat(result).containsExactlyInAnyOrder(firstSubject, secondSubject);
	}
	
	@Test
	public void SubjectRepository_FindById_ReturnSubject() {
	    // Arrange
		SubjectEntity subject = SubjectEntity.builder()
				.name("Mathetmatics")
				.weeklyFund(50)
				.build();
	    
	    Mockito.when(subjectRepository.findById(subject.getId())).thenReturn(Optional.of(subject));
	    
	    // Act
	    Optional<SubjectEntity> result = subjectRepository.findById(subject.getId());
	    
	    // Assert
	    Assertions.assertThat(result).isPresent();
	    Assertions.assertThat(result.get()).isEqualTo(subject);
	}
	
	@Test
	public void SubjectRepository_UpdateSubject_ReturnsUpdatedSubject() {
	    // Arrange
		SubjectEntity subject = SubjectEntity.builder()
				.id(1)
				.name("Mathetmatics")
				.weeklyFund(50)
				.build();
	    
	    Mockito.when(subjectRepository.findById(subject.getId())).thenReturn(Optional.of(subject));
	    SubjectEntity resultSave = subjectRepository.findById(subject.getId()).get();
	    
	    resultSave.setName("Programming");
	    resultSave.setWeeklyFund(69);
	    
		Mockito.when(subjectRepository.save(resultSave)).thenReturn(resultSave);
	    
	    // Act
		SubjectEntity updatedSubject = subjectRepository.save(resultSave);
	    
	    // Assert
	    Assertions.assertThat(updatedSubject.getName()).isEqualTo("Programming");
	    Assertions.assertThat(updatedSubject.getWeeklyFund()).isEqualTo(69);
	}
	
	@Test
	public void SubjectRepository_DeleteSubject_ReturnSubjectIsEmpty() {
	    // Arrange
		SubjectEntity subject = SubjectEntity.builder()
				.id(1)
				.name("Mathetmatics")
				.weeklyFund(50)
				.build();
	    
		subjectRepository.save(subject);
	    
	    // Act
		subjectRepository.delete(subject);
	    Optional<SubjectEntity> subjectReturn = subjectRepository.findById(subject.getId());
	    
	    // Assert
	    Assertions.assertThat(subjectReturn).isEmpty();
	}
}
