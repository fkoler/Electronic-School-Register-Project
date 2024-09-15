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

import com.electric_diary.entities.GradeEntity;
import com.electric_diary.enums.GradingType;

@ExtendWith(MockitoExtension.class)
public class GradeRepositoryTests {
	@Mock
	private GradeRepository gradeRepository;

	@Test
	public void GradeRepository_SaveAll_ReturnSavedGrade() {
		// Arrange
		GradeEntity grade = GradeEntity.builder()
				.id(1)
				.grade(5)
				.gradingType(GradingType.HOMEWORK)
				.build();
		
		Mockito.when(gradeRepository.save(grade)).thenReturn(grade);

		// Act
		GradeEntity result = gradeRepository.save(grade);

		// Assert
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getId()).isGreaterThan(0);
	}
	
	@Test
	public void GradeRepository_FindAll_ReturnMoreThanOneGrade() {
	    // Arrange
		GradeEntity firstGrade = GradeEntity.builder()
				.grade(5)
				.gradingType(GradingType.HOMEWORK)
				.build();
	    
		GradeEntity secondGrade = GradeEntity.builder()
				.grade(3)
				.gradingType(GradingType.WRITTEN_EXAMN)
				.build();
	    
	    List<GradeEntity> gradeList = Arrays.asList(firstGrade, secondGrade);

	    Mockito.when(gradeRepository.findAll()).thenReturn(gradeList);
	    
	    // Act
	    Iterable<GradeEntity> result = gradeRepository.findAll();
	    
	    // Assert
	    Assertions.assertThat(result).isNotNull();
	    Assertions.assertThat(result).hasSize(2);
	    Assertions.assertThat(result).containsExactlyInAnyOrder(firstGrade, secondGrade);
	}
	
	@Test
	public void GradeRepository_FindById_ReturnGrade() {
	    // Arrange
		GradeEntity grade = GradeEntity.builder()
				.grade(5)
				.gradingType(GradingType.HOMEWORK)
				.build();
	    
	    Mockito.when(gradeRepository.findById(grade.getId())).thenReturn(Optional.of(grade));
	    
	    // Act
	    Optional<GradeEntity> result = gradeRepository.findById(grade.getId());
	    
	    // Assert
	    Assertions.assertThat(result).isPresent();
	    Assertions.assertThat(result.get()).isEqualTo(grade);
	}
	
	@Test
	public void GradeRepository_UpdateGrade_ReturnsUpdatedGrade() {
	    // Arrange
		GradeEntity grade = GradeEntity.builder()
				.id(1)
				.grade(5)
				.gradingType(GradingType.HOMEWORK)
				.build();
	    
	    Mockito.when(gradeRepository.findById(grade.getId())).thenReturn(Optional.of(grade));
	    GradeEntity resultSave = gradeRepository.findById(grade.getId()).get();
	    
	    resultSave.setGrade(4);
	    resultSave.setGradingType(GradingType.WRITTEN_EXAMN);
	    
		Mockito.when(gradeRepository.save(resultSave)).thenReturn(resultSave);
	    
	    // Act
		GradeEntity updatedGrade = gradeRepository.save(resultSave);
	    
	    // Assert
	    Assertions.assertThat(updatedGrade.getGrade()).isEqualTo(4);
	    Assertions.assertThat(updatedGrade.getGradingType()).isEqualTo(GradingType.WRITTEN_EXAMN);
	}
	
	@Test
	public void GradeRepository_DeleteGrade_ReturnGradeIsEmpty() {
	    // Arrange
		GradeEntity grade = GradeEntity.builder()
				.id(1)
				.grade(5)
				.gradingType(GradingType.HOMEWORK)
				.build();
	    
		gradeRepository.save(grade);
	    
	    // Act
		gradeRepository.delete(grade);
	    Optional<GradeEntity> gradeReturn = gradeRepository.findById(grade.getId());
	    
	    // Assert
	    Assertions.assertThat(gradeReturn).isEmpty();
	}
}
