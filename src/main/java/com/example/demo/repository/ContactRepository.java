package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

	Contact findOneById(Integer id);

	List<Contact> findByOrderById();

	List<Contact> findByCustomerIdOrderById(Integer id);
}
