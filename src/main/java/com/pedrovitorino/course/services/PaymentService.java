package com.pedrovitorino.course.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pedrovitorino.course.dto.PaymentDTO;
import com.pedrovitorino.course.entities.Order;
import com.pedrovitorino.course.entities.Payment;
import com.pedrovitorino.course.repositories.OrderRepository;
import com.pedrovitorino.course.repositories.PaymentRepository;
import com.pedrovitorino.course.services.exceptions.ResourceNotFoundException;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private OrderRepository orderRepository;
	
	public List<PaymentDTO> findAll() {
		List<Payment> list = paymentRepository.findAll();
		return list.stream().map(e -> new PaymentDTO(e)).collect(Collectors.toList());
	}

	public PaymentDTO findById(Long id) {
		Optional<Payment> obj = paymentRepository.findById(id);
		Payment entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
		return new PaymentDTO(entity);
	}
	
	@Transactional
	public PaymentDTO insert(PaymentDTO obj) {
		Order order = orderRepository.getOne(obj.getOrderId());
		Payment entity = new Payment(null, obj.getMoment(), order);
		order.setPayment(entity);
		orderRepository.save(order);
		return new PaymentDTO(order.getPayment());
	}

	@Transactional
	public PaymentDTO update(Long id, PaymentDTO obj) {
		try {
			Payment entity = paymentRepository.getOne(id);
			updateData(entity, obj);
			entity = paymentRepository.save(entity);
			return new PaymentDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Payment entity, PaymentDTO obj) {
		entity.setMoment(obj.getMoment());
	}
}
