package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "customer_id")
	private Integer customerId;

	@Column(name = "contact_id")
	private Integer contactId;

	@Column(name = "ordered_on")
	private LocalDate orderedOn;

	@Column(name = "total_price")
	private Integer totalPrice;

	public Order() {
	}

	public Order(Integer customerId, Integer contactId, Integer totalPrice) {
		this.customerId = customerId;
		this.contactId = contactId;
		this.orderedOn = LocalDate.now();
		this.totalPrice = totalPrice;
	}

	public Integer getId() {
		return id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public LocalDate getOrderedOn() {
		return orderedOn;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public Integer getContactId() {
		return contactId;
	}

}
