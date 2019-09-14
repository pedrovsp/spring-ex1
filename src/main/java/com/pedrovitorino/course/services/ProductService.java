package com.pedrovitorino.course.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedrovitorino.course.dto.ProductDTO;
import com.pedrovitorino.course.dto.UserDTO;
import com.pedrovitorino.course.entities.Product;
import com.pedrovitorino.course.entities.User;
import com.pedrovitorino.course.repositories.ProductRepository;
import com.pedrovitorino.course.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
		
		@Autowired
		private ProductRepository productRepository;
		
		public List<ProductDTO> findAll() {
			List<Product> list = productRepository.findAll();
			
			return list.stream().map(e -> new ProductDTO(e)).collect(Collectors.toList());
		}
		
		public ProductDTO findById(Long id) {
			Optional<Product> obj = productRepository.findById(id);
			Product entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
			return new ProductDTO(entity);
		}
}
