package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Item;
import com.example.demo.entity.Review;
import com.example.demo.model.Account;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.ShopRepository;

@Controller
public class ItemController {

	@Autowired
	Account account;
	
	@Autowired
	ItemRepository itemRepository;

	@Autowired
	ShopRepository shopRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ReviewRepository reviewRepository;

	@GetMapping("/items")
	public String index(
			@RequestParam(name = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(name = "keyword", defaultValue = "") String keyword,
			@RequestParam(name = "minprice", defaultValue = "") Integer minprice,
			@RequestParam(name = "maxprice", defaultValue = "") Integer maxprice,
			Model model) {

		List<Item> items = new ArrayList<Item>();
		String msg = "";

		if (minprice != null && maxprice != null && maxprice < minprice) {
			msg = "最高金額には最低金額よりも大きい値を入力してください";
			items = itemRepository.findByOrderById();
		} else {
			items = findItems(categoryId, keyword, minprice, maxprice);
		}

		String url = "";

		if (keyword != null) {
			url += "&keyword=" + keyword;
		}
		if (minprice != null) {
			url += "&minprice=" + minprice;
		}
		if (maxprice != null) {
			url += "&maxprice=" + maxprice;
		}

		if (items.size() == 0)
			msg = "条件に当てはまる商品はありませんでした";

		model.addAttribute("msg", msg);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("keyword", keyword);
		model.addAttribute("minprice", minprice);
		model.addAttribute("maxprice", maxprice);
		model.addAttribute("items", items);
		model.addAttribute("url", url);
		model.addAttribute("categories", categoryRepository.findByOrderById());
		model.addAttribute("shops", shopRepository.findByOrderById());

		return "items";
	}

	@GetMapping("/items/{id}")
	public String view(
			@PathVariable("id") Integer id,
			Model model) {

		Item item = itemRepository.findOneById(id);
		List<Review> reviews = reviewRepository.findByItemIdOrderById(id);

		model.addAttribute("item", item);
		model.addAttribute("categories", categoryRepository.findByOrderById());
		model.addAttribute("reviews", reviews);
		model.addAttribute("shops", shopRepository.findByOrderById());

		return "itemDetail";
		
	}

	@GetMapping("/items/{id}/review")
	public String review(
			@PathVariable("id") Integer id,
			Model model) {

		Item item = itemRepository.findOneById(id);
		model.addAttribute("item", item);
		return "review";
	}

	@PostMapping("/items/{id}/review")
	public String add(
			@PathVariable("id") Integer id,
			@RequestParam(name = "title", defaultValue = "") String title,
			@RequestParam(name = "reviewText", defaultValue = "") String reviewText,
			@RequestParam(name = "score", defaultValue = "") Integer score,
			Model model) {

		String msg = "";

		Item item = itemRepository.findOneById(id);
		model.addAttribute("item", item);

		model.addAttribute("title", title);
		model.addAttribute("reviewText", reviewText);
		model.addAttribute("score", score);

		if (title.length() == 0 || reviewText.length() == 0 || score == null) {
			if (title.length() == 0)
				msg += "<p>タイトルが未入力です</p>";
			if (reviewText.length() == 0)
				msg += "<p>本文が未入力です</p>";
			if (score == null)
				msg += "<p>評価の入力内容が不正です</p>";

			model.addAttribute("msg", msg);
			return "review";
		}

		Review review = new Review(id, account.getId(), title, reviewText, score);
		reviewRepository.save(review);
		return "redirect:/items/" + id;
	}

	@GetMapping("/items/{id}/review/{reviewId}")
	public String set(
			@PathVariable("id") Integer id,
			@PathVariable("reviewId") Integer reviewId,
			Model model) {
		Item item = itemRepository.findOneById(id);
		model.addAttribute("item", item);

		Review review = reviewRepository.findOneById(reviewId);
		model.addAttribute("review", review);
		
		model.addAttribute("title", review.getTitle());
		model.addAttribute("reviewText", review.getReviewText());
		model.addAttribute("score", review.getScore());

		return "setReview";
	}

	@PostMapping("/items/{id}/review/{reviewId}")
	public String send(
			@PathVariable("id") Integer id,
			@PathVariable("reviewId") Integer reviewId,
			@RequestParam(name = "title", defaultValue = "") String title,
			@RequestParam(name = "reviewText", defaultValue = "") String reviewText,
			@RequestParam(name = "score", defaultValue = "") Integer score,
			Model model) {

		String msg = "";

		Item item = itemRepository.findOneById(id);
		model.addAttribute("item", item);

		Review review = reviewRepository.findOneById(reviewId);
		model.addAttribute("review", review);

		model.addAttribute("title", title);
		model.addAttribute("reviewText", reviewText);
		model.addAttribute("score", score);

		if (title.length() == 0 || reviewText.length() == 0 || score == null) {
			if (title.length() == 0)
				msg += "<p>タイトルが未入力です</p>";
			if (reviewText.length() == 0)
				msg += "<p>本文が未入力です</p>";
			if (score == null)
				msg += "<p>評価の入力内容が不正です</p>";

			model.addAttribute("msg", msg);
			return "review";
		}

		review.setTitle(title);
		review.setReviewText(reviewText);
		review.setScore(score);

		reviewRepository.save(review);
		return "redirect:/items/" + id;
	}

	@GetMapping("/items/{id}/review/{reviewId}/delete")
	public String delete(
			@PathVariable("id") Integer id,
			@PathVariable("reviewId") Integer reviewId) {
		
		Review review = reviewRepository.findOneById(reviewId);

		if (review.getItemId() == id)
			reviewRepository.deleteById(reviewId);

		return "redirect:/items/" + id;
	}

	private List<Item> findItems(Integer categoryId, String keyword, Integer minprice, Integer maxprice) {
		List<Item> items;
		if (categoryId != null) {
			if (keyword != null) {
				if (minprice != null) {
					if (maxprice != null) {
						items = itemRepository
								.findByCategoryIdAndNameContainingAndPriceGreaterThanEqualAndPriceLessThanEqualOrderById(
										categoryId, keyword, minprice, maxprice);
					} else {
						items = itemRepository.findByCategoryIdAndNameContainingAndPriceGreaterThanEqualOrderById(categoryId,
								keyword, minprice);
					}

				} else {
					if (maxprice != null) {
						items = itemRepository.findByCategoryIdAndNameContainingAndPriceLessThanEqualOrderById(categoryId,
								keyword, maxprice);
					} else {
						items = itemRepository.findByCategoryIdAndNameContainingOrderById(categoryId, keyword);
					}
				}
			} else {

				if (minprice != null) {
					if (maxprice != null) {
						items = itemRepository.findByCategoryIdAndPriceGreaterThanEqualAndPriceLessThanEqualOrderById(categoryId,
								minprice, maxprice);
					} else {
						items = itemRepository.findByCategoryIdAndPriceGreaterThanEqualOrderById(categoryId, minprice);
					}

				} else {
					if (maxprice != null) {
						items = itemRepository.findByCategoryIdAndPriceLessThanEqualOrderById(categoryId, maxprice);
					} else {
						items = itemRepository.findByCategoryIdOrderById(categoryId);
					}
				}
			}
		} else {
			if (keyword != null) {
				if (minprice != null) {
					if (maxprice != null) {
						items = itemRepository.findByNameContainingAndPriceGreaterThanEqualAndPriceLessThanEqualOrderById(
								keyword, minprice, maxprice);
					} else {
						items = itemRepository.findByNameContainingAndPriceGreaterThanEqualOrderById(keyword, minprice);
					}

				} else {
					if (maxprice != null) {
						items = itemRepository.findByNameContainingAndPriceLessThanEqualOrderById(keyword, maxprice);
					} else {
						items = itemRepository.findByNameContainingOrderById(keyword);
					}
				}
			} else {

				if (minprice != null) {
					if (maxprice != null) {
						items = itemRepository.findByPriceGreaterThanEqualAndPriceLessThanEqualOrderById(minprice, maxprice);
					} else {
						items = itemRepository.findByPriceGreaterThanEqualOrderById(minprice);
					}

				} else {
					if (maxprice != null) {
						items = itemRepository.findByPriceLessThanEqualOrderById(maxprice);
					} else {
						items = itemRepository.findByOrderById();
					}
				}
			}
		}
		return items;
	}
}
