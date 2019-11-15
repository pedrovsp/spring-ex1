package com.pedrovitorino.course.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.pedrovitorino.course.entities.Category;
import com.pedrovitorino.course.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Transactional(readOnly = true)
	@Query("SELECT obj FROM Product obj INNER JOIN obj.categories cats WHERE :cat IN cats")
	Page<Product> findByCategory(Category cat, Pageable pageable);

}
