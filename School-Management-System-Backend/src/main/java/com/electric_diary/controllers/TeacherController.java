package com.electric_diary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electric_diary.DTO.Request.TeacherRequestDTO;
import com.electric_diary.entities.TeacherEntity;
import com.electric_diary.services.TeacherService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/teachers")
public class TeacherController {
	@Autowired
	protected TeacherService teacherService;

	@PostMapping
	public ResponseEntity<TeacherEntity> createTeacher(@Valid @RequestBody TeacherRequestDTO teacherRequestDTO) {
		return new ResponseEntity<>(teacherService.createTeacher(teacherRequestDTO), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Iterable<TeacherEntity>> getAllTeachers() {
		return new ResponseEntity<>(teacherService.getAllTeachers(), HttpStatus.OK);
	}

	@GetMapping("/{teacherId}")
	public ResponseEntity<TeacherEntity> getTeacherById(@PathVariable Integer teacherId) {
		return new ResponseEntity<>(teacherService.getTeacherById(teacherId), HttpStatus.OK);
	}

	@PutMapping("/{teacherId}")
	public ResponseEntity<TeacherEntity> updateTeacher(@PathVariable Integer teacherId, @RequestBody TeacherRequestDTO teacherRequestDTO) {
		return new ResponseEntity<>(teacherService.updateTeacher(teacherId, teacherRequestDTO), HttpStatus.OK);
	}

	@DeleteMapping("/{teacherId}")
	public ResponseEntity<TeacherEntity> deleteTeacher(@PathVariable Integer teacherId) {
		return new ResponseEntity<>(teacherService.deleteTeacher(teacherId), HttpStatus.OK);
	}

	@PutMapping("/{teacherId}/subject/{subjectId}")
	public ResponseEntity<TeacherEntity> teacherTeachesSubject(@PathVariable Integer teacherId, @PathVariable Integer subjectId) {
		return new ResponseEntity<>(teacherService.teacherTeachesSubject(teacherId, subjectId), HttpStatus.OK);
	}
}