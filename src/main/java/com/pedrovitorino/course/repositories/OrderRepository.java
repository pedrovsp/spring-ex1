package com.pedrovitorino.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedrovitorino.course.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
