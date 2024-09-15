package com.electric_diary.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.electric_diary.DTO.Request.StudentRequestDTO;
import com.electric_diary.entities.ClassEntity;
import com.electric_diary.entities.ParentEntity;
import com.electric_diary.entities.StudentEntity;
import com.electric_diary.entities.UserEntity;
import com.electric_diary.exception.NotFoundException;
import com.electric_diary.repositories.ClassRepository;
import com.electric_diary.repositories.ParentRepository;
import com.electric_diary.repositories.StudentRepository;
import com.electric_diary.repositories.UserRepository;
import com.electric_diary.services.StudentService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class StudentServiceImpl implements StudentService {
	@PersistenceContext
	protected EntityManager entityManager;

	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final StudentRepository studentRepository;
	private final ClassRepository classRepository;
	private final ParentRepository parentRepository;
	private final UserRepository userRepository;

	public StudentServiceImpl(final StudentRepository studentRepository, final ClassRepository classRepository,
			final ParentRepository parentRepository, final UserRepository userRepository) {
		this.studentRepository = studentRepository;
		this.classRepository = classRepository;
		this.parentRepository = parentRepository;
		this.userRepository = userRepository;
	}

	@Override
	public StudentEntity createStudent(StudentRequestDTO studentRequestDTO) {
		ClassEntity newClass = getClassById(studentRequestDTO.getClassId());
		ParentEntity parent = getParentById(studentRequestDTO.getParentId());
		UserEntity user = getUserById(studentRequestDTO.getUserId());

		StudentEntity student = new StudentEntity();
		student.setFirstName(studentRequestDTO.getFirstName());
		student.setLastName(studentRequestDTO.getLastName());
		student.setNewClass(newClass);
		student.setParent(parent);
		student.setUser(user);
		studentRepository.save(student);
		logger.info("Created student with ID {}.", student.getId());

		return student;
	}

	@Override
	public Iterable<StudentEntity> getAllStudents() {
		logger.info("Fetched all students.");
		return studentRepository.findAll();
	}

	@Override
	public StudentEntity getStudentById(Integer studentId) {
		logger.info("Fetched student with ID {}.", studentId);
		return studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("Student", studentId));
	}

	@Override
	public StudentEntity updateStudent(Integer studentId, StudentRequestDTO studentRequestDTO) {
		StudentEntity student = getStudentById(studentId);

		ClassEntity newClass = getClassById(studentRequestDTO.getClassId());
		ParentEntity parent = getParentById(studentRequestDTO.getParentId());
		UserEntity user = getUserById(studentRequestDTO.getUserId());

		student.setFirstName(studentRequestDTO.getFirstName());
		student.setLastName(studentRequestDTO.getLastName());
		student.setNewClass(newClass);
		student.setParent(parent);
		student.setUser(user);
		studentRepository.save(student);
		logger.info("Updated student with ID {}.", studentId);

		return student;
	}

	@Override
	public StudentEntity deleteStudent(Integer studentId) {
		StudentEntity student = getStudentById(studentId);
		studentRepository.delete(student);
		logger.info("Deleted student with ID {}.", studentId);
		return student;
	}

	private ClassEntity getClassById(Integer classId) {
		return classRepository.findById(classId).orElseThrow(() -> new NotFoundException("User", classId));
	}

	public ParentEntity getParentById(Integer parentId) {
		return parentRepository.findById(parentId).orElseThrow(() -> new NotFoundException("Parent", parentId));
	}

	private UserEntity getUserById(Integer userId) {
		return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User", userId));
	}
}
