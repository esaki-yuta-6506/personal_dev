package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Item;
import com.example.demo.model.Cart;
import com.example.demo.repository.ItemRepository;

@Controller
public class CartController {

	@Autowired
	Cart cart;

	@Autowired
	ItemRepository itemRepository;

	@GetMapping("/cart")
	public String index() {
		return "cart";
	}

	@PostMapping("/cart/add")
	public String addCart(
			@RequestParam("id") int id,
			@RequestParam(name = "quantity", defaultValue = "1") Integer quantity) {

		Item item = itemRepository.findOneById(id);
		item.setQuantity(quantity);
		cart.add(item);
		
		return "redirect:/cart";
	}

	@PostMapping("/cart/delete")
	public String deleteCart(@RequestParam("itemId") int itemId) {

		cart.delete(itemId);
		
		return "redirect:/cart";
	}
}
