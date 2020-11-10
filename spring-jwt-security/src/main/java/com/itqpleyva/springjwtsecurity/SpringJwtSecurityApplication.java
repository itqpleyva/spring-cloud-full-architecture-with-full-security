package com.itqpleyva.springjwtsecurity;

import com.itqpleyva.springjwtsecurity.Models.Usuario;
import com.itqpleyva.springjwtsecurity.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringJwtSecurityApplication implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(SpringJwtSecurityApplication.class, args);
		System.out.println("-----------------------I am inside of the monster----------------------");

		
	}

	@Override
	public void run(String... args) throws Exception {
		
		Usuario u = new Usuario();
		u.setPassword("password");
		u.setRoles("ROLE_ADMIN");
		u.setUsername("user");
		userRepository.save(u);

		Usuario uw = new Usuario();
		uw.setPassword("password");
		uw.setRoles("ROLE_USER");
		uw.setUsername("user1");
		userRepository.save(uw);

		Usuario uwq = new Usuario();
		uwq.setPassword("password");
		uwq.setRoles("ROLE_MANAGER");
		uwq.setUsername("user2");
		userRepository.save(uwq);

	}

}
