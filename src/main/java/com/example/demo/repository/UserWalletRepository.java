package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UserWallet;

public interface UserWalletRepository extends JpaRepository<UserWallet, Integer> {

}
