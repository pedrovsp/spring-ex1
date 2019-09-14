package com.pedrovitorino.course.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pedrovitorino.course.dto.CategoryDTO;
import com.pedrovitorino.course.dto.UserDTO;
import com.pedrovitorino.course.entities.Category;
import com.pedrovitorino.course.entities.User;
import com.pedrovitorino.course.repositories.CategoryRepository;
import com.pedrovitorino.course.services.exceptions.DatabaseException;
import com.pedrovitorino.course.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {
		
		@Autowired
		private CategoryRepository categoryRepository;
		
		public List<CategoryDTO> findAll() {
			List<Category> list = categoryRepository.findAll();
			return list.stream().map(e -> new CategoryDTO(e)).collect(Collectors.toList());
		}
		
		public CategoryDTO findById(Long id) {
			Optional<Category> obj = categoryRepository.findById(id);
			Category entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
			return new CategoryDTO(entity);
		}

		public Category insert(Category obj) {
			return categoryRepository.save(obj);
		}
		
		public void delete(Long id) {
			try {
				categoryRepository.deleteById(id);				
			} catch(EmptyResultDataAccessException e) {
				throw new ResourceNotFoundException(id);
			} catch(DataIntegrityViolationException e) {
				throw new DatabaseException(e.getMessage());
			}
		}

		@Transactional
		public CategoryDTO update(Long id, CategoryDTO obj) {
			try {
				Category entity = categoryRepository.getOne(id);
				updateData(entity, obj);
				entity = categoryRepository.save(entity);
				return new CategoryDTO(entity);
			} catch(EntityNotFoundException e) {
				throw new ResourceNotFoundException(id);
			}
		}
		
		private void updateData(Category entity, CategoryDTO obj) {
			entity.setName(obj.getName());
		}
		
}
