package com.pedrovitorino.course.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedrovitorino.course.entities.Category;
import com.pedrovitorino.course.entities.Order;
import com.pedrovitorino.course.repositories.CategoryRepository;
import com.pedrovitorino.course.repositories.OrderRepository;

@Service
public class CategoryService {
		
		@Autowired
		private CategoryRepository categoryRepository;
		
		public List<Category> findAll() {
			return categoryRepository.findAll();
		}
		
		public Category findById(Long id) {
			return categoryRepository.findById(id).get();
		}
		
}
