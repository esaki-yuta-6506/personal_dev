package com.example.demo.controller.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Item;
import com.example.demo.entity.Plan;
import com.example.demo.entity.Review;
import com.example.demo.entity.Shop;
import com.example.demo.model.Account;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.PlanRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.ShopRepository;

@Controller
public class ShopController {

	@Autowired
	ShopRepository shopRepository;

	@Autowired
	Account account;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	PlanRepository planRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ReviewRepository reviewRepository;

	@GetMapping("/shop")
	public String index(
			Model model) {

		List<Plan> plans = planRepository.findByOrderById();
		model.addAttribute("plans", plans);

		List<Shop> shops;

		shops = shopRepository.findByCustomerIdOrderById(account.getId());

		for (Shop shop : shops)
			shop.setUrl();

		model.addAttribute("shops", shops);

		return "shop/shop";
	}

	@GetMapping("/shop/{id}")
	public String view(
			@PathVariable("id") Integer id,
			Model model) {
		if (account.getId() != shopRepository.findOneById(id).getCustomerId()) {
			return "redirect:/shop";
		}

		List<Category> categories = categoryRepository.findByOrderById();
		model.addAttribute("categories", categories);

		List<Plan> plans = planRepository.findByOrderById();
		model.addAttribute("plans", plans);

		Shop shop = shopRepository.findOneById(id);
		model.addAttribute("shop", shop);

		List<Item> items = itemRepository.findByShopIdOrderById(id);
		model.addAttribute("items", items);

		return "shop/shopDetail";
	}

	@GetMapping("/shop/{id}/{itemId}")
	public String viewItem(
			@PathVariable("id") Integer id,
			@PathVariable("itemId") Integer itemId,
			Model model) {
		if (account.getId() != shopRepository.findOneById(id).getCustomerId()) {
			return "redirect:/shop";
		}

		List<Category> categories = categoryRepository.findByOrderById();
		model.addAttribute("categories", categories);

		model.addAttribute("id", id);

		Item item = itemRepository.findOneById(itemId);
		List<Review> reviews = reviewRepository.findByItemIdOrderById(itemId);

		model.addAttribute("item", item);
		model.addAttribute("categories", categoryRepository.findByOrderById());
		model.addAttribute("reviews", reviews);
		model.addAttribute("shop", shopRepository.findOneById(id));

		return "shop/itemDetail";
	}

	@GetMapping("/shop/{id}/add")
	public String addItem(
			@PathVariable("id") Integer id,
			Model model) {
		if (account.getId() != shopRepository.findOneById(id).getCustomerId()) {
			return "redirect:/shop";
		}

		List<Category> categories = categoryRepository.findByOrderById();
		model.addAttribute("categories", categories);

		model.addAttribute("id", id);
		return "shop/addItem";
	}

	@PostMapping("/shop/{id}/add")
	public String storeItem(
			@PathVariable("id") Integer id,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(name = "price", defaultValue = "") Integer price,
			@RequestParam(name = "stockCount", defaultValue = "") Integer stockCount,
			Model model) {
		if (account.getId() != shopRepository.findOneById(id).getCustomerId()) {
			return "redirect:/shop";
		}

		List<Category> categories = categoryRepository.findByOrderById();
		model.addAttribute("categories", categories);

		if (name.length() == 0 || categoryId == null || price == null || stockCount == null) {
			model.addAttribute("id", id);
			model.addAttribute("name", name);
			model.addAttribute("categoryId", categoryId);
			model.addAttribute("price", price);
			model.addAttribute("stockCount", stockCount);

			String msg = "";

			if (name.length() == 0)
				msg += "<p>商品名が未入力です</p>";
			if (categoryId == null)
				msg += "<p>カテゴリーが未選択です</p>";
			if (price == null)
				msg += "<p>価格が未入力です</p>";
			if (stockCount == null)
				msg += "<p>在庫数が未入力です</p>";

			model.addAttribute("msg", msg);

			return "shop/addItem";
		}

		Item item = new Item(categoryId, id, name, price, stockCount);
		itemRepository.save(item);

		return "redirect:/shop/" + id;
	}

	@GetMapping("/shop/{id}/set/{itemId}")
	public String setItem(
			@PathVariable("id") Integer id,
			@PathVariable("itemId") Integer itemId,
			Model model) {
		if (account.getId() != shopRepository.findOneById(id).getCustomerId()) {
			return "redirect:/shop";
		}

		List<Category> categories = categoryRepository.findByOrderById();
		model.addAttribute("categories", categories);

		model.addAttribute("id", id);
		Item item = itemRepository.findOneById(itemId);
		model.addAttribute("item", item);
		model.addAttribute("itemId", itemId);
		model.addAttribute("name", item.getName());
		model.addAttribute("categoryId", item.getCategoryId());
		model.addAttribute("price", item.getPrice());
		model.addAttribute("stockCount", item.getStockCount());
		return "shop/setItem";
	}

	@PostMapping("/shop/{id}/set/{itemId}")
	public String sendItem(
			@PathVariable("id") Integer id,
			@PathVariable("itemId") Integer itemId,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(name = "price", defaultValue = "") Integer price,
			@RequestParam(name = "stockCount", defaultValue = "") Integer stockCount,
			Model model) {
		if (account.getId() != shopRepository.findOneById(id).getCustomerId()) {
			return "redirect:/shop";
		}

		List<Category> categories = categoryRepository.findByOrderById();
		model.addAttribute("categories", categories);

		if (name.length() == 0 || categoryId == null || price == null || stockCount == null) {
			model.addAttribute("id", id);
			model.addAttribute("itemId", itemId);
			model.addAttribute("name", name);
			model.addAttribute("categoryId", categoryId);
			model.addAttribute("price", price);
			model.addAttribute("stockCount", stockCount);

			String msg = "";

			if (name.length() == 0)
				msg += "<p>商品名が未入力です</p>";
			if (categoryId == null)
				msg += "<p>カテゴリーが未選択です</p>";
			if (price == null)
				msg += "<p>価格が未入力です</p>";
			if (stockCount == null)
				msg += "<p>在庫数が未入力です</p>";

			model.addAttribute("msg", msg);

			return "shop/setItem";
		}

		Item item = itemRepository.findOneById(itemId);
		item = new Item(itemId, categoryId, id, name, price, stockCount, item.getSellCount());
		itemRepository.save(item);

		return "redirect:/shop/" + id;
	}

	@GetMapping("/shop/{id}/delete/{itemId}")
	public String deleteItem(
			@PathVariable("id") Integer id,
			@PathVariable("itemId") Integer itemId) {
		if (account.getId() != shopRepository.findOneById(id).getCustomerId()) {
			return "redirect:/shop";
		}

		Item item = itemRepository.findOneById(itemId);
		item.setStatus(0);
		item.setStockCount(0);

		itemRepository.save(item);

		return "redirect:/shop/" + id;
	}

	@GetMapping("/shop/add")
	public String add(Model model) {
		List<Plan> plans = planRepository.findByOrderById();
		model.addAttribute("plans", plans);

		return "shop/addShop";
	}

	@PostMapping("/shop/add")
	public String store(
			@RequestParam(name = "name", defaultValue = "") String name,
			Model model) {

		List<Plan> plans = planRepository.findByOrderById();
		model.addAttribute("plans", plans);
		if (name.length() == 0) {
			model.addAttribute("name", name);

			String msg = "";

			if (name.length() == 0)
				msg += "<p>ショップ名が未入力です</p>";

			model.addAttribute("mas", msg);

			return "shop/addShop";
		}

		Shop shop = new Shop(account.getId(), name);
		shopRepository.save(shop);

		return "redirect:/shop";
	}

	@GetMapping("/shop/set/{id}")
	public String set(
			@PathVariable("id") Integer id,
			Model model) {
		if (account.getId() != shopRepository.findOneById(id).getCustomerId()) {
			return "redirect:/shop";
		}

		List<Plan> plans = planRepository.findByOrderById();
		model.addAttribute("plans", plans);
		model.addAttribute("id", id);
		Shop shop = shopRepository.findOneById(id);
		model.addAttribute("shop", shop);

		model.addAttribute("customerId", shop.getCustomerId());
		model.addAttribute("palnId", shop.getPlanId());
		model.addAttribute("name", shop.getName());
		return "shop/setShop";
	}

	@PostMapping("/shop/set/{id}")
	public String send(
			@PathVariable("id") Integer id,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "planId", defaultValue = "") Integer planId,
			Model model) {
		if (account.getId() != shopRepository.findOneById(id).getCustomerId()) {
			return "redirect:/shop";
		}

		List<Plan> plans = planRepository.findByOrderById();
		model.addAttribute("plans", plans);

		if (name.length() == 0) {
			model.addAttribute("id", id);
			model.addAttribute("name", name);

			String msg = "";

			if (name.length() == 0)
				msg += "<p>ショップ名が未入力です</p>";

			model.addAttribute("mas", msg);

			return "shop/setShop";
		}
		Shop shop = shopRepository.findOneById(id);
		shop = new Shop(id, planId, account.getId(), name);
		shopRepository.save(shop);

		return "redirect:/shop/" + id;
	}

	@GetMapping("/shop/delete/{id}")
	public String delete(
			@PathVariable("id") Integer id) {
		if (account.getId() != shopRepository.findOneById(id).getCustomerId()) {
			return "redirect:/shop";
		}

		List<Item> items = itemRepository.findByShopIdOrderById(id);

		for (Item item : items) {
			item.setStatus(0);
			item.setStockCount(0);
			itemRepository.save(item);
		}

		shopRepository.deleteById(id);

		return "redirect:/shop";
	}
}