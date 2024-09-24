package com.electric_diary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electric_diary.DTO.Request.ClassRequestDTO;
import com.electric_diary.entities.ClassEntity;
import com.electric_diary.services.ClassService;

@RestController
@RequestMapping(path = "/api/v1/classes")
@CrossOrigin(origins = "http://localhost:3000")
public class ClassController {
	@Autowired
	protected ClassService classService;

	@PostMapping
	public ResponseEntity<ClassEntity> createClass(@RequestBody ClassRequestDTO classRequestDTO) {
		return new ResponseEntity<>(classService.createClass(classRequestDTO), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Iterable<ClassEntity>> getAllClasses() {
		return new ResponseEntity<>(classService.getAllClasses(), HttpStatus.OK);
	}

	@GetMapping("/{classId}")
	public ResponseEntity<ClassEntity> getClassById(@PathVariable Integer classId) {
		return new ResponseEntity<>(classService.getClassById(classId), HttpStatus.OK);
	}

	@PutMapping("/{classId}")
	public ResponseEntity<ClassEntity> updateClass(@PathVariable Integer classId, @RequestBody ClassRequestDTO classRequestDTO) {
		return new ResponseEntity<>(classService.updateClass(classId, classRequestDTO), HttpStatus.OK);
	}

	@DeleteMapping("/{classId}")
	public ResponseEntity<ClassEntity> deleteClass(@PathVariable Integer classId) {
		return new ResponseEntity<>(classService.deleteClass(classId), HttpStatus.OK);
	}
}
