package com.epiis.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epiis.app.JwtService;
import com.epiis.app.dataaccess.UsuarioRepository;
import com.epiis.app.entity.Usuario;

@RestController
@RequestMapping("auth")
public class AuthController {
	private final JwtService jwtService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public AuthController(
			JwtService jwtService) {
		this.jwtService = jwtService;
	}

	record LoginRequest(String userName, String password) {
	}

	record AuthResponse(String token, String userName, String nombre, String rol) {
	}

	@PostMapping(path = "login", consumes = "multipart/form-data")
	public ResponseEntity<AuthResponse> login(@ModelAttribute LoginRequest request) {
		try {
			Usuario usuario = this.usuarioRepository.findByUserNameAndPassword(
					request.userName(),
					request.password());

			if (usuario != null) {
				Map<String, String> userDetails = new HashMap<>();
				userDetails.put("userName", usuario.getUserName());
				userDetails.put("nombre", usuario.getNombre());
				userDetails.put("rol", usuario.getRol());

				String jwtToken = jwtService.generateToken(userDetails);

				return ResponseEntity.ok(new AuthResponse(
						jwtToken,
						usuario.getUserName(),
						usuario.getNombre(),
						usuario.getRol()));
			} else {
				return ResponseEntity.status(401).build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(401).build();
		}
	}
}