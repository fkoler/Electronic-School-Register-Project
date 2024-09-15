package com.electric_diary.exception;

@SuppressWarnings("serial")
public class NotFoundException extends RuntimeException {
	private String entityType;
	private Integer entityId;

	public NotFoundException(String entityType, Integer entityId) {
		this.entityType = entityType;
		this.entityId = entityId;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getEntityType() {
		return entityType;
	}

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
}
