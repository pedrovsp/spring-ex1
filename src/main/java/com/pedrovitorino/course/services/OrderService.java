package com.pedrovitorino.course.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedrovitorino.course.dto.OrderDTO;
import com.pedrovitorino.course.entities.Order;
import com.pedrovitorino.course.entities.User;
import com.pedrovitorino.course.repositories.OrderRepository;
import com.pedrovitorino.course.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
		
		@Autowired
		private AuthService authService;
		@Autowired
		private OrderRepository orderRepository;
		
		public List<OrderDTO> findAll() {
			List<Order> list = orderRepository.findAll();
			
			return list.stream().map(e -> new OrderDTO(e)).collect(Collectors.toList());
		}
		
		public OrderDTO findById(Long id) {
			Optional<Order> obj = orderRepository.findById(id);
			Order entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
			authService.validateOwnOrderOrAdmin(entity);
			return new OrderDTO(entity);
		}
		
		public List<OrderDTO> findByClient() {
			User client = authService.authenticated();
			List<Order> list = orderRepository.findByClient(client);
			return list.stream().map(e -> new OrderDTO(e)).collect(Collectors.toList());
		}
}
