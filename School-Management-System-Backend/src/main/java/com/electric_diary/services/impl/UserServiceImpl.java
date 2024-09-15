package com.electric_diary.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.electric_diary.DTO.Request.UserRequestDTO;
import com.electric_diary.entities.RoleEntity;
import com.electric_diary.entities.UserEntity;
import com.electric_diary.exception.NotFoundException;
import com.electric_diary.repositories.RoleRepository;
import com.electric_diary.repositories.UserRepository;
import com.electric_diary.services.UserService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class UserServiceImpl implements UserService {
	@PersistenceContext
	protected EntityManager entityManager;

	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	public UserServiceImpl(final UserRepository userRepository, final RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public UserEntity createUser(UserRequestDTO userDTOBody) {
		RoleEntity role = getRoleById(userDTOBody.getRoleId());

		UserEntity user = new UserEntity();
		user.setName(userDTOBody.getName());
		user.setLastName(userDTOBody.getLastName());
		user.setPassword("{noop}" + userDTOBody.getPassword());
		user.setEmail(userDTOBody.getEmail());
		user.setRole(role);
		userRepository.save(user);
		logger.info("Created user with ID {}.", user.getId());

		return user;
	}

	@Override
	public Iterable<UserEntity> getAllUsers() {
		logger.info("Fetched all users.");
		return userRepository.findAll();
	}

	@Override
	public UserEntity getUserById(Integer userId) {
		logger.info("Fetched user with ID {}.", userId);
		return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User", userId));
	}

	@Override
	public UserEntity updateUser(Integer userId, UserRequestDTO userDTOBody) {
		UserEntity user = getUserById(userId);
		RoleEntity role = getRoleById(userDTOBody.getRoleId());

		user.setName(userDTOBody.getName());
		user.setLastName(userDTOBody.getLastName());
		user.setPassword(userDTOBody.getPassword());
		user.setEmail(userDTOBody.getEmail());
		user.setRole(role);
		userRepository.save(user);
		logger.info("Updated user with ID {}.", userId);

		return user;
	}

	@Override
	public UserEntity deleteUser(Integer userId) {
		UserEntity user = getUserById(userId);
		userRepository.delete(user);
		logger.info("Deleted user with ID {}.", userId);
		return user;
	}

	private RoleEntity getRoleById(Integer roleId) {
		return roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException("Role", roleId));
	}
}
