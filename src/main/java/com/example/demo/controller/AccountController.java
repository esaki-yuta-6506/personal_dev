package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Customer;
import com.example.demo.model.Account;
import com.example.demo.repository.CustomerRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {

	@Autowired
	HttpSession httpSession;

	@Autowired
	Account account;

	@Autowired
	CustomerRepository customerRepository;

	@GetMapping({ "/", "/login", "/logout" })
	public String index(
			@RequestParam(name = "error", defaultValue = "") String error,
			Model model) {
		httpSession.invalidate();

		if (error.equals("notLoggedIn")) {
			//model.addAttribute("msg", "ログインしてください");
		}
		return "login";
	}

	@PostMapping("/login")
	public String login(
			@RequestParam(name = "email") String email,
			@RequestParam(name = "password", defaultValue = "") String password,
			Model model) {

		Customer customer = customerRepository.findOneByEmailAndPassword(email, password);

		if (customer == null) {
			model.addAttribute("email", email);
			model.addAttribute("msg", "メールアドレスまたはパスワードが間違っています");
			return "login";
		}

		account.setId(customer.getId());
		account.setName(customer.getName());
		account.setModeId(customer.getModeId());

		return "redirect:/items";
	}

	@GetMapping("/login/add")
	public String add() {
		return "addAccount";
	}

	@PostMapping("/login/add")
	public String store(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "password", defaultValue = "") String password,
			@RequestParam(name = "rePassword", defaultValue = "") String rePassword,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "tel", defaultValue = "") String tel,
			Model model) {

		Customer customer = customerRepository.findOneByEmail(email);

		if (name.length() == 0 || email.length() == 0 || password.length() == 0 || rePassword.length() == 0
				|| !password.equals(rePassword) || address.length() == 0 || tel.length() == 0 || customer != null) {
			String msg = "";

			if (name.length() == 0)
				msg += "<p class = 'msg'>名前を入力してください</p>";
			if (email.length() == 0)
				msg += "<p class = 'msg'>メールアドレスを入力してください</p>";
			if (customer != null)
				msg += "<p class = 'msg'>そのメールアドレスは既に使用されています　別のメールアドレスを使用してください</p>";
			if (password.length() == 0)
				msg += "<p class = 'msg'>パスワードを入力してください</p>";
			if (rePassword.length() == 0)
				msg += "<p class = 'msg'>パスワードを再入力してください</p>";
			if (!password.equals(rePassword))
				msg += "<p class = 'msg'>パスワードが一致していません</p>";
			if (address.length() == 0)
				msg += "<p class = 'msg'>住所を入力してください</p>";
			if (tel.length() == 0)
				msg += "<p class = 'msg'>電話番号を入力してください</p>";

			model.addAttribute("name", name);
			model.addAttribute("email", email);
			model.addAttribute("address", address);
			model.addAttribute("tel", tel);
			model.addAttribute("msg", msg);

			return "addAccount";
		}

		customerRepository.save(new Customer(3, name, address, tel, email, password));

		return "login";
	}
}
