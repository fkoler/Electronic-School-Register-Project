package com.electric_diary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.electric_diary.entities.GradeEntity;

public interface GradeRepository extends CrudRepository<GradeEntity, Integer> {

}
