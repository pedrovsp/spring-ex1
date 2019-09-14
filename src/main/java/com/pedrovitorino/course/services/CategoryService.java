package com.pedrovitorino.course.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.pedrovitorino.course.entities.Category;
import com.pedrovitorino.course.entities.Order;
import com.pedrovitorino.course.entities.User;
import com.pedrovitorino.course.repositories.CategoryRepository;
import com.pedrovitorino.course.repositories.OrderRepository;
import com.pedrovitorino.course.services.exceptions.DatabaseException;
import com.pedrovitorino.course.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {
		
		@Autowired
		private CategoryRepository categoryRepository;
		
		public List<Category> findAll() {
			return categoryRepository.findAll();
		}
		
		public Category findById(Long id) {
			Optional<Category> obj = categoryRepository.findById(id);
			return obj.orElseThrow(() -> new ResourceNotFoundException(id));
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
		
		public Category update(Long id, Category obj) {
			try {
				Category entity = categoryRepository.getOne(id);
				updateData(entity, obj);
				return categoryRepository.save(entity);				
			} catch(EntityNotFoundException e) {
				throw new ResourceNotFoundException(id);
			}
		}
		
		private void updateData(Category entity, Category obj) {
			entity.setName(obj.getName());
		}
		
}
