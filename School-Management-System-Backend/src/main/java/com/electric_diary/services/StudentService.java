package com.electric_diary.services;

import com.electric_diary.DTO.Request.StudentRequestDTO;
import com.electric_diary.entities.StudentEntity;

public interface StudentService {
	public StudentEntity createStudent(StudentRequestDTO studentRequestDTO);

	public Iterable<StudentEntity> getAllStudents();

	public StudentEntity getStudentById(Integer studentId);

	public StudentEntity updateStudent(Integer studentId, StudentRequestDTO studentRequestDTO);

	public StudentEntity deleteStudent(Integer studentId);
}
