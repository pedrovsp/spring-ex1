package com.pedrovitorino.course.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pedrovitorino.course.dto.OrderDTO;
import com.pedrovitorino.course.dto.OrderItemDTO;
import com.pedrovitorino.course.entities.Order;
import com.pedrovitorino.course.entities.OrderItem;
import com.pedrovitorino.course.entities.Product;
import com.pedrovitorino.course.entities.User;
import com.pedrovitorino.course.entities.enums.OrderStatus;
import com.pedrovitorino.course.repositories.OrderItemRepository;
import com.pedrovitorino.course.repositories.OrderRepository;
import com.pedrovitorino.course.repositories.ProductRepository;
import com.pedrovitorino.course.repositories.UserRepository;
import com.pedrovitorino.course.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
		
		@Autowired
		private AuthService authService;
		@Autowired
		private OrderRepository orderRepository;
		@Autowired
		private UserRepository userRepository;
		@Autowired
		private ProductRepository productRepository;
		@Autowired
		private OrderItemRepository orderItemRepository;
		
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

		@Transactional(readOnly = true)
		public List<OrderItemDTO> findItems(Long id) {
			Order entity = orderRepository.getOne(id);
			authService.validateOwnOrderOrAdmin(entity);
			Set<OrderItem> set = entity.getItems();
			return set.stream().map(e -> new OrderItemDTO(e)).collect(Collectors.toList());
		}

		@Transactional(readOnly = true)
		public List<OrderDTO> findByClientId(Long clientId) {
			User client = userRepository.getOne(clientId);
			List<Order> list = orderRepository.findByClient(client);
			return list.stream().map(e -> new OrderDTO(e)).collect(Collectors.toList());
		}

		@Transactional
		public OrderDTO placeOrder(List<OrderItemDTO> dto) {
			User client = authService.authenticated();
			
			Order order = new Order(null, Instant.now(), client, OrderStatus.WAITING_PAYMENT);
			
			for (OrderItemDTO itemDTO : dto) {
				Product product = productRepository.getOne(itemDTO.getProductId());
				OrderItem item = new OrderItem(order, product, itemDTO.getQuantity(), itemDTO.getPrice());
				order.getItems().add(item);
			}
			
			orderRepository.save(order);
			orderItemRepository.saveAll(order.getItems());
			
			return new OrderDTO(order);
		}
		

		@Transactional
		public OrderDTO update(Long id, OrderDTO obj) {
			try {
				Order entity = orderRepository.getOne(id);
				updateData(entity, obj);
				entity = orderRepository.save(entity);
				return new OrderDTO(entity);
			} catch (EntityNotFoundException e) {
				throw new ResourceNotFoundException(id);
			}
		}

		private void updateData(Order entity, OrderDTO obj) {
			entity.setOrderStatus(obj.getOrderStatus());
		}
}
