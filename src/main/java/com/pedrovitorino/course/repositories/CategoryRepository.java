package com.pedrovitorino.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedrovitorino.course.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
