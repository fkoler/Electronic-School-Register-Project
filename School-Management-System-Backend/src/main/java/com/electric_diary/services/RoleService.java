package com.electric_diary.services;

import com.electric_diary.DTO.Request.RoleRequestDTO;
import com.electric_diary.entities.RoleEntity;

public interface RoleService {
	public RoleEntity createRole(RoleRequestDTO roleRequestDTO);

	public Iterable<RoleEntity> getAllRoles();

	public RoleEntity getRoleById(Integer roleId);

	public RoleEntity updateRole(Integer roleId, RoleRequestDTO roleRequestDTO);

	public RoleEntity deleteRole(Integer roleId);
}
