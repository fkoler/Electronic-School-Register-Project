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

import com.electric_diary.DTO.Request.SubjectRequestDTO;
import com.electric_diary.entities.SubjectEntity;
import com.electric_diary.services.SubjectService;

@RestController
@RequestMapping(path = "/api/v1/subjects")
public class SubjectController {
	@Autowired
	protected SubjectService subjectService;

	@PostMapping
	public ResponseEntity<SubjectEntity> createSubject(@RequestBody SubjectRequestDTO subjectRequestDTO) {
		return new ResponseEntity<>(subjectService.createSubject(subjectRequestDTO), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Iterable<SubjectEntity>> getAllSubjects() {
		return new ResponseEntity<>(subjectService.getAllSubjects(), HttpStatus.OK);
	}

	@GetMapping("/{subjectId}")
	public ResponseEntity<SubjectEntity> getSubjectById(@PathVariable Integer subjectId) {
		return new ResponseEntity<>(subjectService.getSubjectById(subjectId), HttpStatus.OK);
	}

	@PutMapping("/{subjectId}")
	public ResponseEntity<SubjectEntity> updateSubject(@PathVariable Integer subjectId, @RequestBody SubjectRequestDTO subjectRequestDTO) {
		return new ResponseEntity<>(subjectService.updateSubject(subjectId, subjectRequestDTO), HttpStatus.OK);
	}

	@DeleteMapping("/{subjectId}")
	public ResponseEntity<SubjectEntity> deleteSubject(@PathVariable Integer subjectId) {
		return new ResponseEntity<>(subjectService.deleteSubject(subjectId), HttpStatus.OK);
	}

	@PutMapping("/{subjectId}/students/{studentId}")
	public ResponseEntity<SubjectEntity> enrollStudentToSubject(@PathVariable Integer subjectId, @PathVariable Integer studentId) {
		return new ResponseEntity<>(subjectService.enrollStudentToSubject(subjectId, studentId), HttpStatus.OK);
	}
}
