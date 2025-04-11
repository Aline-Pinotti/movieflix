package com.devsuperior.movieflix.services;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.EmailDTO;
import com.devsuperior.movieflix.dto.NewPasswordDTO;
import com.devsuperior.movieflix.entities.PasswordRecover;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.PasswordRecoverRepository;
import com.devsuperior.movieflix.repositories.UserRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class AuthService {	
	
	@Value("${spring.mail.username}")
	private String defaultSender;

	@Value("${email.password-recover.uri}")
	private String recoverUri;

	@Value("${email.password-recover.token.minutes}")
	private Long tokenMinutes;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private PasswordRecoverRepository passwordRecoverRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;

	@Transactional
	public void createRecoverToken(EmailDTO body) {
		User user = userRepository.findByEmail(body.getEmail());
		if (user == null) {
			throw new ResourceNotFoundException("Email not found");
		}

		String token = UUID.randomUUID().toString();

		PasswordRecover entity = new PasswordRecover();
		entity.setToken(token);
		entity.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60L));
		entity.setEmail(body.getEmail());
		passwordRecoverRepository.save(entity);

		String text = "Acesse o link para definir uma nova senha (válido por " + tokenMinutes + " minutos):\n\n"
				+ recoverUri + token;

		emailService.sendEmail(body.getEmail(), "Recuperação de senha", text);

		System.out.println("Token:  " + token);
	}

	@Transactional
	public void saveNewPassword(NewPasswordDTO body) {
		List<PasswordRecover> list = passwordRecoverRepository.searchValidTokens(body.getToken(), Instant.now());

		if (list.isEmpty()) {
			throw new ResourceNotFoundException("Token inválido!");
		}

		User user = userRepository.findByEmail(list.get(0).getEmail());
		user.setPassword(passwordEncoder.encode(body.getPassword()));
		userRepository.save(user);
	}
	protected User authenticated() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
			String username = jwtPrincipal.getClaim("username");
			return userRepository.findByEmail(username);
		} catch (Exception e) {
			throw new UsernameNotFoundException("Usuário inválido!");
		}
	}
}
