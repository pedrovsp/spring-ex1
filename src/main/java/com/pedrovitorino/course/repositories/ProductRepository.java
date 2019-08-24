package com.pedrovitorino.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedrovitorino.course.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
