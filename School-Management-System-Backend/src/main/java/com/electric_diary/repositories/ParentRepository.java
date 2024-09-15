package com.electric_diary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.electric_diary.entities.ParentEntity;

public interface ParentRepository extends CrudRepository<ParentEntity, Integer> {
	ParentEntity findByStudentsId(Integer studentId);
}