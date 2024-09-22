package com.electric_diary.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.electric_diary.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
	Optional<UserEntity> findByEmail(String userEmail);
}
