package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_wallets")
public class UserWallet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "customer_id")
	private Integer customerId;

	@Column(name = "wallet_id")
	private Integer walletId;

	@Column(name = "wallet_number")
	private Integer walletNumber;

	@Column(name = "wallet_key")
	private Integer walletKey;

	public UserWallet() {
	}

	public UserWallet(Integer customerId, Integer walletId, Integer walletNumber, Integer walletKey) {
		this.customerId = customerId;
		this.walletId = walletId;
		this.walletNumber = walletNumber;
		this.walletKey = walletKey;
	}

	public UserWallet(Integer id, Integer customerId, Integer walletId, Integer walletNumber, Integer walletKey) {
		this.id = id;
		this.customerId = customerId;
		this.walletId = walletId;
		this.walletNumber = walletNumber;
		this.walletKey = walletKey;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getWalletId() {
		return walletId;
	}

	public void setWalletId(Integer walletId) {
		this.walletId = walletId;
	}

	public Integer getWalletNumber() {
		return walletNumber;
	}

	public void setWalletNumber(Integer walletNumber) {
		this.walletNumber = walletNumber;
	}

	public Integer getWalletKey() {
		return walletKey;
	}

	public void setWalletKey(Integer walletKey) {
		this.walletKey = walletKey;
	}

}
