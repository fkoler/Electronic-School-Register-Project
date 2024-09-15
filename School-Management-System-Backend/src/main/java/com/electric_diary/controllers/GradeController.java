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

import com.electric_diary.DTO.Request.GradeRequestDTO;
import com.electric_diary.entities.GradeEntity;
import com.electric_diary.services.GradeService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/grades")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class GradeController {
	@Autowired
	private GradeService gradeService;

	@PostMapping
	public ResponseEntity<GradeEntity> assignGrade(@RequestBody @Valid GradeRequestDTO gradeDTOBody) {
		return new ResponseEntity<>(gradeService.assignGrade(gradeDTOBody), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Iterable<GradeEntity>> getAllGrades() {
		return new ResponseEntity<>(gradeService.getAllGrades(), HttpStatus.OK);
	}

	@GetMapping("/{gradeId}")
	public ResponseEntity<GradeEntity> getGradeById(@PathVariable Integer gradeId) {
		return new ResponseEntity<>(gradeService.getGradeById(gradeId), HttpStatus.OK);
	}

	@PutMapping("/{gradeId}")
	public ResponseEntity<GradeEntity> updateGrade(@PathVariable Integer gradeId, @RequestBody GradeRequestDTO gradeDTOBody) {
		return new ResponseEntity<>(gradeService.updateGrade(gradeId, gradeDTOBody), HttpStatus.OK);
	}

	@DeleteMapping("/{gradeId}")
	public ResponseEntity<GradeEntity> deleteGrade(@PathVariable Integer gradeId) {
		return new ResponseEntity<>(gradeService.deleteGrade(gradeId), HttpStatus.OK);
	}
}