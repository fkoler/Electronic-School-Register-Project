package com.electric_diary.services;

import com.electric_diary.DTO.Request.GradeRequestDTO;
import com.electric_diary.entities.GradeEntity;

public interface GradeService {
	public GradeEntity assignGrade(GradeRequestDTO gradeRequestDTO);

	public Iterable<GradeEntity> getAllGrades();

	public GradeEntity getGradeById(Integer gradeId);

	public GradeEntity updateGrade(Integer gradeId, GradeRequestDTO gradeRequestDTO);

	public GradeEntity deleteGrade(Integer gradeId);
}
