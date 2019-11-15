package com.pedrovitorino.course.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedrovitorino.course.dto.PaymentDTO;
import com.pedrovitorino.course.entities.Payment;
import com.pedrovitorino.course.repositories.PaymentRepository;
import com.pedrovitorino.course.services.exceptions.ResourceNotFoundException;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	public List<PaymentDTO> findAll() {
		List<Payment> list = paymentRepository.findAll();
		return list.stream().map(e -> new PaymentDTO(e)).collect(Collectors.toList());
	}

	public PaymentDTO findById(Long id) {
		Optional<Payment> obj = paymentRepository.findById(id);
		Payment entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
		return new PaymentDTO(entity);
	}
}
