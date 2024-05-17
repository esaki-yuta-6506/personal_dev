package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	Customer findOneByEmailAndPassword(String email, String password);

	Customer findOneByEmail(String email);

	Customer findOneById(Integer id);

	List<Customer> findByModeId(Integer modeId);
}
