package com.electric_diary.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.electric_diary.DTO.Request.ClassRequestDTO;
import com.electric_diary.entities.ClassEntity;
import com.electric_diary.exception.NotFoundException;
import com.electric_diary.repositories.ClassRepository;
import com.electric_diary.services.ClassService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ClassServiceImpl implements ClassService {
	@PersistenceContext
	protected EntityManager entityManager;

	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final ClassRepository classRepository;

	public ClassServiceImpl(final ClassRepository classRepository) {
		this.classRepository = classRepository;
	}

	@Override
	public ClassEntity createClass(ClassRequestDTO classRequestDTO) {
		ClassEntity newClass = new ClassEntity();

		newClass.setName(classRequestDTO.getName());
		classRepository.save(newClass);
		logger.info("Created class with ID {}.", newClass.getId());

		return newClass;
	}

	@Override
	public Iterable<ClassEntity> getAllClasses() {
		logger.info("Fetched all classes.");
		return classRepository.findAll();
	}

	@Override
	public ClassEntity getClassById(Integer classId) {
		logger.info("Fetched class with ID {}.", classId);
		return classRepository.findById(classId).orElseThrow(() -> new NotFoundException("Class", classId));
	}

	@Override
	public ClassEntity updateClass(Integer classId, ClassRequestDTO classRequestDTO) {
		ClassEntity newClass = getClassById(classId);

		newClass.setName(classRequestDTO.getName());
		classRepository.save(newClass);
		logger.info("Updated class with ID {}.", classId);

		return newClass;
	}

	@Override
	public ClassEntity deleteClass(Integer classId) {
		ClassEntity newClass = getClassById(classId);
		classRepository.delete(newClass);
		logger.info("Deleted class with ID {}.", classId);
		return newClass;
	}
}
