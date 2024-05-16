package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
	List<Item> findByCategoryId(Integer categoryId);
    
    List<Item> findByCategoryIdAndNameContaining(Integer categoryId, String keyword);
    
    List<Item> findByCategoryIdAndPriceGreaterThanEqual(Integer categoryId, Integer minPrice);
    
    List<Item> findByCategoryIdAndPriceLessThanEqual(Integer categoryId, Integer maxPrice);
    
    List<Item> findByCategoryIdAndNameContainingAndPriceGreaterThanEqual(Integer categoryId, String keyword, Integer minPrice);
    
    List<Item> findByCategoryIdAndNameContainingAndPriceLessThanEqual(Integer categoryId, String keyword, Integer maxPrice);
    
    List<Item> findByCategoryIdAndPriceGreaterThanEqualAndPriceLessThanEqual(Integer categoryId, Integer minPrice, Integer maxPrice);
    
    List<Item> findByCategoryIdAndNameContainingAndPriceGreaterThanEqualAndPriceLessThanEqual(Integer categoryId, String keyword, Integer minPrice, Integer maxPrice);
	
	List<Item> findByNameContaining(String keyword);
    
    List<Item> findByNameContainingAndPriceGreaterThanEqual(String keyword, Integer minPrice);
    
    List<Item> findByNameContainingAndPriceLessThanEqual(String keyword, Integer maxPrice);
    
    List<Item> findByNameContainingAndPriceGreaterThanEqualAndPriceLessThanEqual(String keyword, Integer minPrice, Integer maxPrice);
    
    List<Item> findByPriceGreaterThanEqual(Integer minPrice);
    
    List<Item> findByPriceGreaterThanEqualAndPriceLessThanEqual(Integer minPrice, Integer maxPrice);

    List<Item> findByPriceLessThanEqual(Integer maxPrice);

	Item findOneById(Integer id);

	List<Item> findByShopId(Integer id);
}
