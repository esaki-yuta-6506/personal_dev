package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
	List<Item> findByCategoryIdOrderById(Integer categoryId);
    
    List<Item> findByCategoryIdAndNameContainingOrderById(Integer categoryId, String keyword);
    
    List<Item> findByCategoryIdAndPriceGreaterThanEqualOrderById(Integer categoryId, Integer minPrice);
    
    List<Item> findByCategoryIdAndPriceLessThanEqualOrderById(Integer categoryId, Integer maxPrice);
    
    List<Item> findByCategoryIdAndNameContainingAndPriceGreaterThanEqualOrderById(Integer categoryId, String keyword, Integer minPrice);
    
    List<Item> findByCategoryIdAndNameContainingAndPriceLessThanEqualOrderById(Integer categoryId, String keyword, Integer maxPrice);
    
    List<Item> findByCategoryIdAndPriceGreaterThanEqualAndPriceLessThanEqualOrderById(Integer categoryId, Integer minPrice, Integer maxPrice);
    
    List<Item> findByCategoryIdAndNameContainingAndPriceGreaterThanEqualAndPriceLessThanEqualOrderById(Integer categoryId, String keyword, Integer minPrice, Integer maxPrice);
	
	List<Item> findByNameContainingOrderById(String keyword);
    
    List<Item> findByNameContainingAndPriceGreaterThanEqualOrderById(String keyword, Integer minPrice);
    
    List<Item> findByNameContainingAndPriceLessThanEqualOrderById(String keyword, Integer maxPrice);
    
    List<Item> findByNameContainingAndPriceGreaterThanEqualAndPriceLessThanEqualOrderById(String keyword, Integer minPrice, Integer maxPrice);
    
    List<Item> findByPriceGreaterThanEqualOrderById(Integer minPrice);
    
    List<Item> findByPriceGreaterThanEqualAndPriceLessThanEqualOrderById(Integer minPrice, Integer maxPrice);

    List<Item> findByPriceLessThanEqualOrderById(Integer maxPrice);

	Item findOneById(Integer id);

	List<Item> findByShopIdOrderById(Integer id);

	List<Item> findByOrderById();
}
