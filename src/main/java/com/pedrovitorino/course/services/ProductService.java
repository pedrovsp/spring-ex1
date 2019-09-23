package com.pedrovitorino.course.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pedrovitorino.course.dto.CategoryDTO;
import com.pedrovitorino.course.dto.ProductCategoriesDTO;
import com.pedrovitorino.course.dto.ProductDTO;
import com.pedrovitorino.course.entities.Category;
import com.pedrovitorino.course.entities.Product;
import com.pedrovitorino.course.repositories.CategoryRepository;
import com.pedrovitorino.course.repositories.ProductRepository;
import com.pedrovitorino.course.services.exceptions.DatabaseException;
import com.pedrovitorino.course.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
		
		@Autowired
		private ProductRepository productRepository;
		
		@Autowired
		private CategoryRepository categoryRepository;
		
		public Page<ProductDTO> findAllPaged(Pageable pageable) {
			Page<Product> list = productRepository.findAll(pageable);
			
			return list.map(e -> new ProductDTO(e));
		}
		
		public ProductDTO findById(Long id) {
			Optional<Product> obj = productRepository.findById(id);
			Product entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
			return new ProductDTO(entity);
		}
		
		public ProductDTO insert(ProductCategoriesDTO obj) {
			Product entity = obj.toEntity();
			setProductCategories(entity, obj.getCategories());
			entity = productRepository.save(entity);
			return new ProductDTO(entity);
		}

		@Transactional
		public ProductDTO update(Long id, ProductCategoriesDTO obj) {
			try {
				Product entity = productRepository.getOne(id);
				updateData(entity, obj);
				entity = productRepository.save(entity);
				return new ProductDTO(entity);
			} catch(EntityNotFoundException e) {
				throw new ResourceNotFoundException(id);
			}
		}
		
		
		public void delete(Long id) {
			try {
				productRepository.deleteById(id);				
			} catch(EmptyResultDataAccessException e) {
				throw new ResourceNotFoundException(id);
			} catch(DataIntegrityViolationException e) {
				throw new DatabaseException(e.getMessage());
			}
		}
		
		private void setProductCategories(Product entity, List<CategoryDTO> categories) {
			entity.getCategories().clear();
			for(CategoryDTO category: categories) {
				Category cat = categoryRepository.getOne(category.getId());
				entity.getCategories().add(cat);
			}
			
		}
		
		private void updateData(Product entity, ProductCategoriesDTO obj) {
			entity.setName(obj.getName());
			entity.setDescription(obj.getDescription());
			entity.setPrice(obj.getPrice());
			entity.setImgUrl(obj.getImgUrl());
			if (obj.getCategories() != null && obj.getCategories().size() > 0) {
				setProductCategories(entity, obj.getCategories());
			}
		}
}
