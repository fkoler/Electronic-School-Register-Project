package com.electric_diary.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.electric_diary.DTO.Request.RoleRequestDTO;
import com.electric_diary.entities.RoleEntity;
import com.electric_diary.exception.NotFoundException;
import com.electric_diary.repositories.RoleRepository;
import com.electric_diary.services.RoleService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class RoleServiceImpl implements RoleService {
	@PersistenceContext
	protected EntityManager entityManager;

	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final RoleRepository roleRepository;

	public RoleServiceImpl(final RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public RoleEntity createRole(RoleRequestDTO roleRequestDTO) {
		RoleEntity role = new RoleEntity();
		
		role.setName(roleRequestDTO.getName());
		roleRepository.save(role);
		logger.info("Created role with ID {}.", role.getId());
		
		return role;
	}

	@Override
	public Iterable<RoleEntity> getAllRoles() {
		logger.info("Fetched all roles.");
		return roleRepository.findAll();
	}

	@Override
	public RoleEntity getRoleById(Integer roleId) {
		logger.info("Fetched role with ID {}.", roleId);
		return roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException("Role", roleId));
	}

	@Override
	public RoleEntity updateRole(Integer roleId, RoleRequestDTO roleRequestDTO) {
		RoleEntity role = getRoleById(roleId);

		role.setName(roleRequestDTO.getName());
		roleRepository.save(role);
		logger.info("Updated role with ID {}.", roleId);

		return role;
	}

	@Override
	public RoleEntity deleteRole(Integer roleId) {
		RoleEntity role = getRoleById(roleId);
		roleRepository.delete(role);
		logger.info("Deleted role with ID {}.", roleId);
		return role;
	}
}
