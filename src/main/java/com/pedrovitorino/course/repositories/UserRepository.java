package com.pedrovitorino.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedrovitorino.course.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
