package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "item_id")
	private Integer itemId;

	@Column(name = "customer_id")
	private Integer customerId;

	@Column(name = "reviewed_on")
	private LocalDate reviewedOn;

	private String title;

	@Column(name = "review_text")
	private String reviewText;

	private Integer score;

	public Review() {
	}

	public Review(Integer itemId, Integer customerId, String title, String reviewText, Integer score) {
		this.itemId = itemId;
		this.customerId = customerId;
		this.reviewedOn = LocalDate.now();
		this.title = title;
		this.reviewText = reviewText;
		this.score = score;
	}

	public Review(Integer id, Integer itemId, Integer customerId, LocalDate reviewedOn, String title, String reviewText,
			Integer score) {
		this.id = id;
		this.itemId = itemId;
		this.customerId = customerId;
		this.reviewedOn = reviewedOn;
		this.title = title;
		this.reviewText = reviewText;
		this.score = score;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public LocalDate getReviewedOn() {
		return reviewedOn;
	}

	public void setReviewedOn(LocalDate reviewedOn) {
		this.reviewedOn = reviewedOn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}
