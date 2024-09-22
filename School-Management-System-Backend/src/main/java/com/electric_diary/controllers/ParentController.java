package com.electric_diary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electric_diary.DTO.Request.ParentRequestDTO;
import com.electric_diary.entities.ParentEntity;
import com.electric_diary.services.ParentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/parents")
public class ParentController {
	@Autowired
	protected ParentService parentService;

	@PostMapping
	public ResponseEntity<ParentEntity> createParent(@Valid @RequestBody ParentRequestDTO parentRequestDTO) {
		return new ResponseEntity<ParentEntity>(parentService.createParent(parentRequestDTO), HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@GetMapping
	public ResponseEntity<Iterable<ParentEntity>> getAllParents() {
		return new ResponseEntity<>(parentService.getAllParents(), HttpStatus.OK);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping("/{parentId}")
	public ResponseEntity<ParentEntity> getParentById(@PathVariable Integer parentId) {
		return new ResponseEntity<>(parentService.getParentById(parentId), HttpStatus.OK);
	}

	@PutMapping("/{parentId}")
	public ResponseEntity<ParentEntity> updateParent(@PathVariable Integer parentId, @RequestBody ParentRequestDTO parentRequestDTO) {
		return new ResponseEntity<>(parentService.updateParent(parentId, parentRequestDTO), HttpStatus.OK);
	}

	@DeleteMapping("/{parentId}")
	public ResponseEntity<ParentEntity> deleteParent(@PathVariable Integer parentId) {
		return new ResponseEntity<>(parentService.deleteParent(parentId), HttpStatus.OK);
	}

	@PutMapping("/{parentId}/students/{studentId}")
	public ResponseEntity<ParentEntity> assignStudentToParent(@PathVariable Integer parentId, @PathVariable Integer studentId) {
		return new ResponseEntity<>(parentService.assignStudentToParent(parentId, studentId), HttpStatus.OK);
	}
}
