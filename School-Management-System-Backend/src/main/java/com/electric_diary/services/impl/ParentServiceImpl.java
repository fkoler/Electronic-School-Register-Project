package com.electric_diary.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.electric_diary.DTO.Request.ParentRequestDTO;
import com.electric_diary.entities.ParentEntity;
import com.electric_diary.entities.StudentEntity;
import com.electric_diary.entities.UserEntity;
import com.electric_diary.exception.NotFoundException;
import com.electric_diary.repositories.ParentRepository;
import com.electric_diary.repositories.StudentRepository;
import com.electric_diary.repositories.UserRepository;
import com.electric_diary.services.ParentService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ParentServiceImpl implements ParentService {
	@PersistenceContext
	protected EntityManager entityManager;

	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final ParentRepository parentRepository;
	private final StudentRepository studentRepository;
	private final UserRepository userRepository;

	public ParentServiceImpl(final ParentRepository parentRepository, final StudentRepository studentRepository,
			final UserRepository userRepository) {
		this.parentRepository = parentRepository;
		this.studentRepository = studentRepository;
		this.userRepository = userRepository;
	}

	@Override
	public ParentEntity createParent(ParentRequestDTO parentRequestDTO) {
		UserEntity user = getUserById(parentRequestDTO.getUserId());

		ParentEntity parent = new ParentEntity();
		parent.setFirstName(parentRequestDTO.getFirstName());
		parent.setLastName(parentRequestDTO.getLastName());
		parent.setEmail(parentRequestDTO.getEmail());
		parent.setUser(user);
		parentRepository.save(parent);
		logger.info("Created parent with ID {}.", parent.getId());

		return parent;
	}

	@Override
	public Iterable<ParentEntity> getAllParents() {
		logger.info("Fetched all parents.");
		return parentRepository.findAll();
	}

	@Override
	public ParentEntity getParentById(Integer parentId) {
		logger.info("Fetched parent with ID {}.", parentId);
		return parentRepository.findById(parentId).orElseThrow(() -> new NotFoundException("Parent", parentId));
	}

	@Override
	public ParentEntity updateParent(Integer parentId, ParentRequestDTO parentRequestDTO) {
		ParentEntity parent = getParentById(parentId);
		UserEntity user = getUserById(parentRequestDTO.getUserId());

		parent.setFirstName(parentRequestDTO.getFirstName());
		parent.setLastName(parentRequestDTO.getLastName());
		parent.setEmail(parentRequestDTO.getEmail());
		parent.setUser(user);
		parentRepository.save(parent);
		logger.info("Updated parent with ID {}.", parentId);

		return parent;
	}

	@Override
	public ParentEntity deleteParent(Integer parentId) {
		ParentEntity parent = getParentById(parentId);
		parentRepository.delete(parent);
		logger.info("Deleted parent with ID {}.", parentId);
		return parent;
	}

	@Override
	public ParentEntity assignStudentToParent(Integer parentId, Integer studentId) {
		ParentEntity parent = getParentById(parentId);
		StudentEntity student = getStudentById(studentId);

		student.setParent(parent);
		studentRepository.save(student);

		return parent;
	}

	private UserEntity getUserById(Integer userId) {
		return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User", userId));
	}

	private StudentEntity getStudentById(Integer studentId) {
		return studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("Student", studentId));
	}
}
