package com.electric_diary.services;

import com.electric_diary.DTO.Request.UserRequestDTO;
import com.electric_diary.entities.UserEntity;

public interface UserService {
	public UserEntity createUser(UserRequestDTO userDTOBody);

	public Iterable<UserEntity> getAllUsers();

	public UserEntity getUserById(Integer userId);
	
	public UserEntity getUserByEmail(String userEmail);

	public UserEntity updateUser(Integer userId, UserRequestDTO userDTOBody);

	public UserEntity deleteUser(Integer userId);
}
