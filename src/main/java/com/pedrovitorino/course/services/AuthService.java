package com.pedrovitorino.course.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pedrovitorino.course.dto.CredentialsDTO;
import com.pedrovitorino.course.dto.TokenDTO;
import com.pedrovitorino.course.security.JWTUtils;
import com.pedrovitorino.course.services.exceptions.JWTAuthencticationException;

@Service
public class AuthService {
	
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JWTUtils jwtUtil;
	
	@Transactional(readOnly = true)
	public TokenDTO authenticate(CredentialsDTO dto) {
		try {
			var authToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
			authenticationManager.authenticate(authToken);
			String token = jwtUtil.generateToken(dto.getEmail());
			return new TokenDTO(dto.getEmail(), token);			
		} catch(AuthenticationException e) {
			throw new JWTAuthencticationException("Bad Credentials");
		}
	}
}
