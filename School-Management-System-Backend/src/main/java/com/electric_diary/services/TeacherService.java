package com.electric_diary.services;

import com.electric_diary.DTO.Request.TeacherRequestDTO;
import com.electric_diary.entities.TeacherEntity;

public interface TeacherService {
	public TeacherEntity createTeacher(TeacherRequestDTO teacherRequestDTO);

	public Iterable<TeacherEntity> getAllTeachers();

	public TeacherEntity getTeacherById(Integer teacherId);

	public TeacherEntity updateTeacher(Integer teacherId, TeacherRequestDTO teacherRequestDTO);

	public TeacherEntity deleteTeacher(Integer teacherId);

	public TeacherEntity teacherTeachesSubject(Integer teacherId, TeacherRequestDTO teacherRequestDTO);
}
