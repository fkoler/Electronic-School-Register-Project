package com.electric_diary.services;

import com.electric_diary.DTO.Request.ClassRequestDTO;
import com.electric_diary.entities.ClassEntity;

public interface ClassService {
	public ClassEntity createClass(ClassRequestDTO classRequestDTO);

	public Iterable<ClassEntity> getAllClasses();

	public ClassEntity getClassById(Integer classId);

	public ClassEntity updateClass(Integer classId, ClassRequestDTO classRequestDTO);

	public ClassEntity deleteClass(Integer classId);
}
