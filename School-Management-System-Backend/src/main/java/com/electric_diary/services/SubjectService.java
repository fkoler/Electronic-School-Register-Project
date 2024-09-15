package com.electric_diary.services;

import com.electric_diary.DTO.Request.SubjectRequestDTO;
import com.electric_diary.entities.SubjectEntity;

public interface SubjectService {
	public SubjectEntity createSubject(SubjectRequestDTO subjectRequestDTO);

	public Iterable<SubjectEntity> getAllSubjects();

	public SubjectEntity getSubjectById(Integer subjectId);

	public SubjectEntity updateSubject(Integer subjectId, SubjectRequestDTO subjectRequestDTO);

	public SubjectEntity deleteSubject(Integer subjectId);

	public SubjectEntity enrollStudentToSubject(Integer subjectId, Integer studentId);
}