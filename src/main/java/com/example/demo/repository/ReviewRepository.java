package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

	List<Review> findByItemIdOrderById(Integer itemId);

	List<Review> findByOrderById();
	
	List<Review> findByCustomerIdOrderById(Integer customerId);
	
	Review findOneById(Integer Id);
	
}
