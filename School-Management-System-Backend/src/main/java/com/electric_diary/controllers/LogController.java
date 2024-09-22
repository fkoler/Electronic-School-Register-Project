package com.electric_diary.controllers;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping(path = "/api/v1/logs")
public class LogController {
	@Secured("ROLE_ADMIN")
	@GetMapping("/download")
	public ResponseEntity<FileSystemResource> downloadLogs() {

		File logFile = new File("logs/spring-boot-logging.log");
		if (!logFile.exists()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		FileSystemResource resource = new FileSystemResource(logFile);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + logFile.getName());

		return ResponseEntity.ok().headers(headers).body(resource);
	}
}
