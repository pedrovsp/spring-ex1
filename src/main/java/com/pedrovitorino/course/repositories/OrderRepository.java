package com.pedrovitorino.course.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedrovitorino.course.entities.Order;
import com.pedrovitorino.course.entities.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByClient(User client);
}
