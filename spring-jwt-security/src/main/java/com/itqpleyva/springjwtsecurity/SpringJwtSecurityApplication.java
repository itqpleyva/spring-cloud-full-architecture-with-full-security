package com.itqpleyva.springjwtsecurity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.itqpleyva.springjwtsecurity.Models.ACLModel;
import com.itqpleyva.springjwtsecurity.Models.Usuario;
import com.itqpleyva.springjwtsecurity.Repositories.ACLRepository;
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

	@Autowired
	ACLRepository aclRepository;

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

		//adding acl objects
		String roles_list = "ROLE_ADMIN,ROLE_USER,ROLE_MANAGER";

		ACLModel acl1 = new ACLModel("/auth/authenticate", "GET", roles_list);
		ACLModel acl2 = new ACLModel("/auth/valid_token", "GET", roles_list);
		ACLModel acl3 = new ACLModel("/micro2/message", "GET", roles_list);
		ACLModel acl4 = new ACLModel("/micro1/message", "GET", roles_list);

		aclRepository.save(acl1);
		aclRepository.save(acl2);
		aclRepository.save(acl3);
		aclRepository.save(acl4);
		System.out.println(aclRepository.findAll());
	
	}

}
