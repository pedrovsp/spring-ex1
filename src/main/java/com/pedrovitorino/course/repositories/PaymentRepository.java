package com.pedrovitorino.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedrovitorino.course.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
