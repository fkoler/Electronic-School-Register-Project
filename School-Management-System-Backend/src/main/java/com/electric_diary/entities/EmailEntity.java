package com.electric_diary.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailEntity {
	private String to;
	private String subject;
	private String text;
}