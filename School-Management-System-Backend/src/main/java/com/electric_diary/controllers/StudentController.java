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

import com.electric_diary.DTO.Request.StudentRequestDTO;
import com.electric_diary.entities.StudentEntity;
import com.electric_diary.security.Views;
import com.electric_diary.services.StudentService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/students")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class StudentController {
	@Autowired
	protected StudentService studentService;

	@PostMapping
	public ResponseEntity<StudentEntity> createStudent(@Valid @RequestBody StudentRequestDTO studentRequestDTO) {
		return new ResponseEntity<>(studentService.createStudent(studentRequestDTO), HttpStatus.OK);
	}

	@GetMapping("/student")
	@JsonView(Views.Student.class)
	public ResponseEntity<Iterable<StudentEntity>> getAllStudentsForStudents() {
		return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
	}

	@GetMapping("/parent")
	@JsonView(Views.Parent.class)
	public ResponseEntity<Iterable<StudentEntity>> getAllStudentsForParent() {
		return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
	}

	@GetMapping("/{studentId}")
	public ResponseEntity<StudentEntity> getStudentById(@PathVariable Integer studentId) {
		return new ResponseEntity<>(studentService.getStudentById(studentId), HttpStatus.OK);
	}

	@PutMapping("/{studentId}")
	public ResponseEntity<StudentEntity> updateStudent(@PathVariable Integer studentId, @RequestBody StudentRequestDTO studentRequestDTO) {
		return new ResponseEntity<>(studentService.updateStudent(studentId, studentRequestDTO), HttpStatus.OK);
	}

	@DeleteMapping("/{studentId}")
	public ResponseEntity<StudentEntity> deleteStudent(@PathVariable Integer studentId) {
		return new ResponseEntity<>(studentService.deleteStudent(studentId), HttpStatus.OK);
	}
}
