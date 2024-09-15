package com.electric_diary.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.electric_diary.DTO.Request.SubjectRequestDTO;
import com.electric_diary.entities.StudentEntity;
import com.electric_diary.entities.SubjectEntity;
import com.electric_diary.exception.NotFoundException;
import com.electric_diary.repositories.StudentRepository;
import com.electric_diary.repositories.SubjectRepository;
import com.electric_diary.services.SubjectService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class SubjectServiceImpl implements SubjectService {
	@PersistenceContext
	protected EntityManager entityManager;

	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final SubjectRepository subjectRepository;
	private final StudentRepository studentRepository;

	public SubjectServiceImpl(final SubjectRepository subjectRepository, final StudentRepository studentRepository) {
		this.subjectRepository = subjectRepository;
		this.studentRepository = studentRepository;
	}

	@Override
	public SubjectEntity createSubject(SubjectRequestDTO subjectRequestDTO) {
		SubjectEntity subject = new SubjectEntity();

		subject.setName(subjectRequestDTO.getName());
		subject.setWeeklyFund(subjectRequestDTO.getWeeklyFund());
		subjectRepository.save(subject);
		logger.info("Created subject with ID {}.", subject.getId());

		return subject;
	}

	@Override
	public Iterable<SubjectEntity> getAllSubjects() {
		logger.info("Fetched all subjects.");
		return subjectRepository.findAll();
	}

	@Override
	public SubjectEntity getSubjectById(Integer subjectId) {
		logger.info("Fetched subject with ID {}.", subjectId);
		return subjectRepository.findById(subjectId).orElseThrow(() -> new NotFoundException("Subject", subjectId));
	}

	@Override
	public SubjectEntity updateSubject(Integer subjectId, SubjectRequestDTO subjectRequestDTO) {
		SubjectEntity subject = getSubjectById(subjectId);

		subject.setName(subjectRequestDTO.getName());
		subject.setWeeklyFund(subjectRequestDTO.getWeeklyFund());
		subjectRepository.save(subject);
		logger.info("Updated subject with ID {}.", subjectId);

		return subject;
	}

	@Override
	public SubjectEntity deleteSubject(Integer subjectId) {
		SubjectEntity subject = getSubjectById(subjectId);
		subjectRepository.delete(subject);
		logger.info("Deleted subject with ID {}.", subjectId);
		return subject;
	}

	@Override
	public SubjectEntity enrollStudentToSubject(Integer subjectId, Integer studentId) {
		SubjectEntity subject = getSubjectById(subjectId);
		StudentEntity student = getStudentById(studentId);

		subject.enrolStudents(student);
		subjectRepository.save(subject);

		return subject;
	}

	public StudentEntity getStudentById(Integer studentId) {
		return studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("Student", studentId));
	}
}
