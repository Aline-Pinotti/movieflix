package com.devsuperior.movieflix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.movieflix.dto.EmailDTO;
import com.devsuperior.movieflix.dto.NewPasswordDTO;
import com.devsuperior.movieflix.services.AuthService;

import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@GetMapping(value = "/test")
	public void getMethodName() {
		System.out.println("Success!");
	}
	

	@PostMapping(value = "/recover-token")
	public ResponseEntity<Void> createRecoverToken(@Valid @RequestBody EmailDTO dto) {
		System.out.println("Creating token...");
		authService.createRecoverToken(dto);
		System.out.println("Token created successfully!");
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/new-password")
	public ResponseEntity<Void> saveNewPassword(@Valid @RequestBody NewPasswordDTO dto) {
		authService.saveNewPassword(dto);
		return ResponseEntity.noContent().build();
	}
}
