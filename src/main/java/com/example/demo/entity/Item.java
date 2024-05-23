package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "items")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "category_id")
	private Integer categoryId;

	@Column(name = "shop_id")
	private Integer shopId;

	private String name;

	private Integer price;

	@Column(name = "stock_count")
	private Integer stockCount;

	@Column(name = "sell_count")
	private Integer sellCount;
	
	private Integer status;

	@Transient
	private Integer quantity;

	public Item() {
	}

	public Item(Integer categoryId, Integer shopId, String name, Integer price, Integer stockCount, Integer status) {
		this.categoryId = categoryId;
		this.shopId = shopId;
		this.name = name;
		this.price = price;
		this.stockCount = stockCount;
		this.sellCount = 0;
		this.status = status;
	}

	public Item(Integer categoryId, Integer shopId, String name, Integer price, Integer stockCount, Integer sellCount, Integer status) {
		this.categoryId = categoryId;
		this.shopId = shopId;
		this.name = name;
		this.price = price;
		this.stockCount = stockCount;
		this.sellCount = sellCount;
		this.status = status;
	}

	public Item(Integer id, Integer categoryId, Integer shopId, String name, Integer price, Integer stockCount,
			Integer sellCount, Integer status) {
		this.id = id;
		this.categoryId = categoryId;
		this.shopId = shopId;
		this.name = name;
		this.price = price;
		this.stockCount = stockCount;
		this.sellCount = sellCount;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getStockCount() {
		return stockCount;
	}

	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}

	public Integer getSellCount() {
		return sellCount;
	}

	public void setSellCount(Integer sellCount) {
		this.sellCount = sellCount;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getUrl() {
		return "<a href = '/items/" + id + "'>" + name + "</a>";
	}

	public String getShopUrl() {
		return "<a href = '/shop/" + shopId + "/" + id + "'>" + name + "</a>";
	}

	public String getAdminUrl() {
		return "<a href = '/admin/shop/" + shopId + "/" + id + "'>" + name + "</a>";
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
