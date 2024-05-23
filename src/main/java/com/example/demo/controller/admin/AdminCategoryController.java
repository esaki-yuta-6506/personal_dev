package com.example.demo.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;

@Controller
public class AdminCategoryController {
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	CategoryRepository categoryRepository;

	@GetMapping("/admin/category")
	public String index(Model model) {
		List<Category> categoryList = categoryRepository.findByOrderById();
		model.addAttribute("categories", categoryList);
		return "admin/categories";
	}

	@GetMapping("/admin/category/add")
	public String add() {
		return "admin/addCategory";
	}

	@PostMapping("/admin/category/add")
	public String store(
			@RequestParam(value = "name", defaultValue = "") String name,
			Model model) {
		
		Category category = categoryRepository.findOneByName(name);
		
		if(name.length() == 0 || category != null) {
			model.addAttribute("name", name);
			String msg = "";
			
			if(name.length() == 0)
				msg += "カテゴリー名が入力されていません";
			if(category != null)
				msg += "すでに同名のカテゴリーが存在します";
			
			model.addAttribute("msg", msg);
			return "admin/addCategory";
		}
			
		category = new Category(name);
		categoryRepository.save(category);
		return "redirect:/admin/category";
	}

	@GetMapping("/admin/category/set/{id}")
	public String set(@PathVariable("id") Integer id, Model model) {

		Category category = categoryRepository.findOneById(id);
		model.addAttribute("category", category);
		model.addAttribute("name", category.getName());
		return "admin/setCategory";
	}

	@PostMapping("/admin/category/set/{id}")
	public String send(
			@PathVariable("id") Integer id,
			@RequestParam(value = "name", defaultValue = "") String name,
			Model model) {

		Category category = categoryRepository.findOneByName(name);
		
		if(name.length() == 0 || category != null && category.getId() != id) {
			model.addAttribute("name", name);
			String msg = "";
			
			if(name.length() == 0)
				msg += "カテゴリー名が入力されていません";
			if(category != null)
				msg += "すでに同名のカテゴリーが存在します";
			
			category = categoryRepository.findOneById(id);
			model.addAttribute("category", category);
			model.addAttribute("msg", msg);
			return "admin/setCategory";
		}
		
		category = new Category(id, name);
		categoryRepository.save(category);
		return "redirect:/admin/category";
	}
}
