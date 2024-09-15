package com.electric_diary.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.electric_diary.DTO.Request.GradeRequestDTO;
import com.electric_diary.entities.EmailEntity;
import com.electric_diary.entities.GradeEntity;
import com.electric_diary.entities.ParentEntity;
import com.electric_diary.entities.StudentEntity;
import com.electric_diary.entities.SubjectEntity;
import com.electric_diary.entities.TeacherEntity;
import com.electric_diary.exception.NotFoundException;
import com.electric_diary.repositories.GradeRepository;
import com.electric_diary.repositories.ParentRepository;
import com.electric_diary.repositories.StudentRepository;
import com.electric_diary.repositories.SubjectRepository;
import com.electric_diary.repositories.TeacherRepository;
import com.electric_diary.services.EmailService;
import com.electric_diary.services.GradeService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class GradeServiceImpl implements GradeService {
	@PersistenceContext
	protected EntityManager entityManager;

	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final GradeRepository gradeRepository;
	private final StudentRepository studentRepository;
	private final TeacherRepository teacherRepository;
	private final SubjectRepository subjectRepository;
	private final ParentRepository parentRepositroy;
	private final EmailService emailService;

	public GradeServiceImpl(final GradeRepository gradeRepository, final StudentRepository studentRepository,
			final TeacherRepository teacherRepository, final SubjectRepository subjectRepository,
			final ParentRepository parentRepositroy, final EmailService emailService) {
		this.gradeRepository = gradeRepository;
		this.studentRepository = studentRepository;
		this.teacherRepository = teacherRepository;
		this.subjectRepository = subjectRepository;
		this.parentRepositroy = parentRepositroy;
		this.emailService = emailService;
	}

	@Override
	public GradeEntity assignGrade(GradeRequestDTO gradeRequestDTO) {
		StudentEntity student = getStudentById(gradeRequestDTO.getStudentId());
		TeacherEntity teacher = getTeacherById(gradeRequestDTO.getTeacherId());
		SubjectEntity subject = getSubjectById(gradeRequestDTO.getSubjectId());
		Integer grade = gradeRequestDTO.getGrade();

		GradeEntity newGrade = new GradeEntity();
		newGrade.setStudent(student);
		newGrade.setTeacher(teacher);
		newGrade.setSubject(subject);
		newGrade.setGrade(grade);
		newGrade.setGradingType(gradeRequestDTO.getGradingType());
		gradeRepository.save(newGrade);
		logger.info("Created grade with ID {}.", teacher.getId());

		sendNewGradeEmailToParent(gradeRequestDTO.getStudentId(), grade, student, teacher, subject);
		logger.info("Sent email to parent.");

		return newGrade;
	}

	@Override
	public Iterable<GradeEntity> getAllGrades() {
		logger.info("Fetched all grades.");
		return gradeRepository.findAll();
	}

	@Override
	public GradeEntity getGradeById(Integer gradeId) {
		logger.info("Fetched grade with ID {}.", gradeId);
		return gradeRepository.findById(gradeId).orElseThrow(() -> new NotFoundException("Grade", gradeId));
	}

	@Override
	public GradeEntity updateGrade(Integer gradeId, GradeRequestDTO gradeDTOBody) {
		GradeEntity newGrade = getGradeById(gradeId);

		StudentEntity student = getStudentById(gradeDTOBody.getStudentId());
		TeacherEntity teacher = getTeacherById(gradeDTOBody.getTeacherId());
		SubjectEntity subject = getSubjectById(gradeDTOBody.getSubjectId());
		Integer grade = gradeDTOBody.getGrade();

		newGrade.setStudent(student);
		newGrade.setTeacher(teacher);
		newGrade.setSubject(subject);
		newGrade.setGrade(grade);
		newGrade.setGradingType(gradeDTOBody.getGradingType());
		gradeRepository.save(newGrade);
		logger.info("Updated grade with ID {}.", gradeId);

		sendUpdatedGradeEmailToParent(gradeDTOBody.getStudentId(), grade, student, teacher, subject);
		logger.info("Sent updated email to parent.");

		return newGrade;
	}
	
	@Override
	public GradeEntity deleteGrade(Integer gradeId) {
		GradeEntity grade = getGradeById(gradeId);
		gradeRepository.delete(grade);
		logger.info("Deleted grade with ID {}.", gradeId);
		return grade;
	}

	private StudentEntity getStudentById(Integer studentId) {
		return studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("Student", studentId));
	}

	private SubjectEntity getSubjectById(Integer subjectId) {
		return subjectRepository.findById(subjectId).orElseThrow(() -> new NotFoundException("Subject", subjectId));
	}

	private TeacherEntity getTeacherById(Integer teacherId) {
		return teacherRepository.findById(teacherId).orElseThrow(() -> new NotFoundException("Teacher", teacherId));
	}

	private void sendNewGradeEmailToParent(Integer studentId, Integer grade, StudentEntity student,
			TeacherEntity teacher, SubjectEntity subject) {
		sendEmailToParent(studentId, grade, student, teacher, subject, "Your child got a new grade");
	}

	private void sendUpdatedGradeEmailToParent(Integer studentId, Integer grade, StudentEntity student,
			TeacherEntity teacher, SubjectEntity subject) {
		sendEmailToParent(studentId, grade, student, teacher, subject, "Your child's grade has been updated");
	}

	private void sendEmailToParent(Integer studentId, Integer grade, StudentEntity student, TeacherEntity teacher,
			SubjectEntity subject, String emailSubject) {
		ParentEntity parent = parentRepositroy.findByStudentsId(studentId);
		if (parent != null && parent.getEmail() != null) {
			EmailEntity emailObject = new EmailEntity();
			emailObject.setTo(parent.getEmail());
			emailObject.setSubject(emailSubject);
			emailObject.setText("Dear " + parent.getFirstName() + ",\n\n" + "Your child " + student.getFirstName()
					+ " has received a grade of " + grade + " in " + subject.getName() + ".\n\n" + "Best regards,\n"
					+ teacher.getFirstName() + " " + teacher.getLastName());
			emailService.sendSimpleMessage(emailObject);
		}
	}
}
