package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "shops")
public class Shop {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "plan_id")
	private Integer planId;

	@Column(name = "customer_id")
	private Integer customerId;

	private String name;

	public Shop() {
	}

	public Shop(Integer planId, Integer customerId, String name) {
		this.planId = planId;
		this.customerId = customerId;
		this.name = name;
	}

	public Shop(Integer id, Integer planId, Integer customerId, String name) {
		this.id = id;
		this.planId = planId;
		this.customerId = customerId;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
