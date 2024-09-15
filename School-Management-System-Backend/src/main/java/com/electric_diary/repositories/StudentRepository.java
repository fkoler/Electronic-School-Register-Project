package com.electric_diary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.electric_diary.entities.StudentEntity;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer> {

}
