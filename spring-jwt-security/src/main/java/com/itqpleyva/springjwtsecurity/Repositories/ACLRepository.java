package com.itqpleyva.springjwtsecurity.Repositories;

import com.itqpleyva.springjwtsecurity.Models.ACLModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ACLRepository extends JpaRepository<ACLModel, Long> {

}