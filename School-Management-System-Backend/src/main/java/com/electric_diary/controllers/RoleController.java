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

import com.electric_diary.DTO.Request.RoleRequestDTO;
import com.electric_diary.entities.RoleEntity;
import com.electric_diary.services.RoleService;

@RestController
@RequestMapping(path = "/api/v1/roles")
public class RoleController {
	@Autowired
	protected RoleService roleService;

	@PostMapping
	public ResponseEntity<RoleEntity> createParent(@RequestBody RoleRequestDTO roleRequestDTO) {
		return new ResponseEntity<RoleEntity>(roleService.createRole(roleRequestDTO), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Iterable<RoleEntity>> getAllRoles() {
		return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
	}

	@GetMapping("/{roleId}")
	public ResponseEntity<RoleEntity> getRoleById(@PathVariable Integer roleId) {
		return new ResponseEntity<>(roleService.getRoleById(roleId), HttpStatus.OK);
	}

	@PutMapping("/{roleId}")
	public ResponseEntity<RoleEntity> updateRole(@PathVariable Integer roleId, @RequestBody RoleRequestDTO roleRequestDTO) {
		return new ResponseEntity<>(roleService.updateRole(roleId, roleRequestDTO), HttpStatus.OK);
	}

	@DeleteMapping("/{roleId}")
	public ResponseEntity<RoleEntity> deleteRole(@PathVariable Integer roleId) {
		return new ResponseEntity<>(roleService.deleteRole(roleId), HttpStatus.OK);
	}
}
