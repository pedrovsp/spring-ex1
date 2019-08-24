package com.pedrovitorino.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedrovitorino.course.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
