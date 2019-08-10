package com.pedrovitorino.course.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.pedrovitorino.course.entities.User;
import com.pedrovitorino.course.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		User u1 = new User(null, "Nelio", "nelio@email.com", "34988884444", "123543");
		User u2 = new User(null, "Camila", "camila@email.com", "34983384444", "123123");
		
		userRepository.saveAll(Arrays.asList(u1, u2));
	}
}
