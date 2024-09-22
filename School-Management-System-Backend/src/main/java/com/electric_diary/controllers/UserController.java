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

import com.electric_diary.DTO.Request.UserRequestDTO;
import com.electric_diary.entities.UserEntity;
import com.electric_diary.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
	@Autowired
	protected UserService userService;

	@PostMapping
	public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserRequestDTO userDTOBody) {
		return new ResponseEntity<UserEntity>(userService.createUser(userDTOBody), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Iterable<UserEntity>> getAllUsers() {
		return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserEntity> getUserById(@PathVariable Integer userId) {
		return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
	}
	
	@GetMapping("/email/{userEmail}")
	public ResponseEntity<UserEntity> getUserByEmail(@PathVariable String userEmail) {
		return new ResponseEntity<>(userService.getUserByEmail(userEmail), HttpStatus.OK);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<UserEntity> updateUser(@PathVariable Integer userId, @RequestBody UserRequestDTO userDTOBody) {
		return new ResponseEntity<>(userService.updateUser(userId, userDTOBody), HttpStatus.OK);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<UserEntity> deleteUser(@PathVariable Integer userId) {
		return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
	}
}
