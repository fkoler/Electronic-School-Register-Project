package com.electric_diary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.electric_diary.entities.TeacherEntity;

public interface TeacherRepository extends CrudRepository<TeacherEntity, Integer> {

}
