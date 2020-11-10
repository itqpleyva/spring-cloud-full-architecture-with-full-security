package com.itqpleyva.springjwtsecurity.Repositories;

import com.itqpleyva.springjwtsecurity.Models.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByusername(String username);

}