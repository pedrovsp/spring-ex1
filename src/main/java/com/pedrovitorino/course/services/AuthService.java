package com.pedrovitorino.course.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pedrovitorino.course.dto.CredentialsDTO;
import com.pedrovitorino.course.dto.TokenDTO;
import com.pedrovitorino.course.entities.User;
import com.pedrovitorino.course.repositories.UserRepository;
import com.pedrovitorino.course.security.JWTUtils;
import com.pedrovitorino.course.services.exceptions.JWTAuthencticationException;
import com.pedrovitorino.course.services.exceptions.JWTAuthorizationException;

@Service
public class AuthService {
	
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JWTUtils jwtUtil;
	@Autowired
	private UserRepository userRepository;
	
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
	
	public User authenticated() {
		try {
			UserDetails userDetails = (UserDetails)SecurityContextHolder
					.getContext()
					.getAuthentication()
					.getPrincipal();
			
			return userRepository.findByEmail(userDetails.getUsername());
		} catch(Exception e) {
			throw new JWTAuthorizationException("Access Denied");
		}
	}
	
	public void validateSelfOrAdmin(Long userId) {
		User user = authenticated();
		if (user == null || (!user.getId().equals(userId)) && !user.hasRole("ROLE_ADMIN")) {
			throw new JWTAuthorizationException("Access denied");
		}
	}
}
