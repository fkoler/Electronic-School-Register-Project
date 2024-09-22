package com.electric_diary.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.electric_diary.DTO.Request.TeacherRequestDTO;
import com.electric_diary.entities.ClassEntity;
import com.electric_diary.entities.SubjectEntity;
import com.electric_diary.entities.TeacherEntity;
import com.electric_diary.entities.UserEntity;
import com.electric_diary.exception.NotFoundException;
import com.electric_diary.repositories.ClassRepository;
import com.electric_diary.repositories.SubjectRepository;
import com.electric_diary.repositories.TeacherRepository;
import com.electric_diary.repositories.UserRepository;
import com.electric_diary.services.TeacherService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class TeacherServiceImpl implements TeacherService {
	@PersistenceContext
	protected EntityManager entityManager;

	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final TeacherRepository teacherRepository;
	private final SubjectRepository subjectRepository;
	private final ClassRepository classRepository;
	private final UserRepository userRepository;

	public TeacherServiceImpl(final TeacherRepository teacherRepository, final SubjectRepository subjectRepository,
			final ClassRepository classRepository, final UserRepository userRepository) {
		this.teacherRepository = teacherRepository;
		this.subjectRepository = subjectRepository;
		this.classRepository = classRepository;
		this.userRepository = userRepository;
	}

	@Override
	public TeacherEntity createTeacher(TeacherRequestDTO teacherRequestDTO) {
	    ClassEntity newClass = getClassById(teacherRequestDTO.getClassId());
	    UserEntity user = getUserById(teacherRequestDTO.getUserId());

	    TeacherEntity teacher = new TeacherEntity();
	    teacher.setFirstName(teacherRequestDTO.getFirstName());
	    teacher.setLastName(teacherRequestDTO.getLastName());
	    teacher.setNewClass(newClass);;
	    teacher.setUser(user);

	    // Add subject only if subjectId is provided
	    if (teacherRequestDTO.getSubjectId() != null) {
	        SubjectEntity subject = getSubjectById(teacherRequestDTO.getSubjectId());
	        teacher.addSubjects(subject);  // Associate the teacher with a subject
	    }

	    teacherRepository.save(teacher);
	    logger.info("Created teacher with ID {}.", teacher.getId());

	    return teacher;
	}


	@Override
	public Iterable<TeacherEntity> getAllTeachers() {
		logger.info("Fetched all teachers");
		return teacherRepository.findAll();
	}

	@Override
	public TeacherEntity getTeacherById(Integer teacherId) {
		logger.info("Fetched teacher with ID {}.", teacherId);
		return teacherRepository.findById(teacherId).orElseThrow(() -> new NotFoundException("Teacher", teacherId));
	}

	@Override
	public TeacherEntity updateTeacher(Integer teacherId, TeacherRequestDTO teacherRequestDTO) {
		TeacherEntity teacher = getTeacherById(teacherId);

		ClassEntity newClass = getClassById(teacherRequestDTO.getClassId());
		UserEntity user = getUserById(teacherRequestDTO.getUserId());

		teacher.setFirstName(teacherRequestDTO.getFirstName());
		teacher.setLastName(teacherRequestDTO.getLastName());
		teacher.setNewClass(newClass);
		teacher.setUser(user);
		teacherRepository.save(teacher);
		logger.info("Updated teacher with ID {}.", teacherId);

		return teacher;
	}

	@Override
	public TeacherEntity deleteTeacher(Integer teacherId) {
		TeacherEntity teacher = getTeacherById(teacherId);
		teacherRepository.delete(teacher);
		logger.info("Deleted teacher with ID {}.", teacherId);
		return teacher;
	}

	@Override
	public TeacherEntity teacherTeachesSubject(Integer teacherId, TeacherRequestDTO teacherRequestDTO) {
		TeacherEntity teacher = getTeacherById(teacherId);
		SubjectEntity subject = getSubjectById(teacherRequestDTO.getSubjectId());

		teacher.addSubjects(subject);
		teacherRepository.save(teacher);

		return teacher;
	}

	public ClassEntity getClassById(Integer classId) {
		return classRepository.findById(classId).orElseThrow(() -> new NotFoundException("Class", classId));
	}

	public UserEntity getUserById(Integer userId) {
		return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User", userId));
	}

	public SubjectEntity getSubjectById(Integer subjectId) {
		return subjectRepository.findById(subjectId).orElseThrow(() -> new NotFoundException("Subject", subjectId));
	}
}
