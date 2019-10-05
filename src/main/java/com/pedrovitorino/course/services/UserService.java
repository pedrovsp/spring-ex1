package com.pedrovitorino.course.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pedrovitorino.course.dto.UserDTO;
import com.pedrovitorino.course.dto.UserInsertDTO;
import com.pedrovitorino.course.entities.User;
import com.pedrovitorino.course.repositories.UserRepository;
import com.pedrovitorino.course.services.exceptions.DatabaseException;
import com.pedrovitorino.course.services.exceptions.ResourceNotFoundException;

@Service
public class UserService implements UserDetailsService {
		
		@Autowired
		private BCryptPasswordEncoder passwordEncoder;
		@Autowired
		private UserRepository userRepository;
		
		public List<UserDTO> findAll() {
			List<User> list = userRepository.findAll();
			
			return list.stream().map(e -> new UserDTO(e)).collect(Collectors.toList());
		}
		
		public UserDTO findById(Long id) {
			Optional<User> obj = userRepository.findById(id);
			User entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
			return new UserDTO(entity);
		}
		

		public UserDTO insert(UserInsertDTO obj) {
			User entity = obj.toEntity();
			entity.setPassword(passwordEncoder.encode(entity.getPassword()));
			entity = userRepository.save(entity);
			return new UserDTO(entity);
		}
		
		public void delete(Long id) {
			try {
				userRepository.deleteById(id);				
			} catch(EmptyResultDataAccessException e) {
				throw new ResourceNotFoundException(id);
			} catch(DataIntegrityViolationException e) {
				throw new DatabaseException(e.getMessage());
			}
		}
		
		@Transactional
		public UserDTO update(Long id, UserDTO obj) {
			try {
				User entity = userRepository.getOne(id);
				updateData(entity, obj);
				entity = userRepository.save(entity);
				return new UserDTO(entity);
			} catch(EntityNotFoundException e) {
				throw new ResourceNotFoundException(id);
			}
		}
		
		private void updateData(User entity, UserDTO obj) {
			entity.setName(obj.getName());
			entity.setEmail(obj.getEmail());
			entity.setPhone(obj.getPhone());
		}

		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			User user = userRepository.findByEmail(username);
			if (user == null) {
				throw new UsernameNotFoundException(username);
			}
			return user;
		}
}
