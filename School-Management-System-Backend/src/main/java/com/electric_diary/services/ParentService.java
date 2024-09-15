package com.electric_diary.services;

import com.electric_diary.DTO.Request.ParentRequestDTO;
import com.electric_diary.entities.ParentEntity;

public interface ParentService {
	public ParentEntity createParent(ParentRequestDTO parentRequestDTO);

	public Iterable<ParentEntity> getAllParents();

	public ParentEntity getParentById(Integer parentId);

	public ParentEntity updateParent(Integer parentId, ParentRequestDTO parentRequestDTO);

	public ParentEntity deleteParent(Integer parentId);

	public ParentEntity assignStudentToParent(Integer parentId, Integer studentId);
}
