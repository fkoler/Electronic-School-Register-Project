package com.electric_diary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.electric_diary.entities.SubjectEntity;

public interface SubjectRepository extends CrudRepository<SubjectEntity, Integer> {

}
