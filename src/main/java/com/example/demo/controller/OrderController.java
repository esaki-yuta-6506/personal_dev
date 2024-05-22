package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Contact;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Item;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.model.Account;
import com.example.demo.model.Cart;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;

@Controller
public class OrderController {

	@Autowired
	Account account;

	@Autowired
	Cart cart;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderDetailRepository orderDetailRepository;

	@Autowired
	ContactRepository contactRepository;

	@GetMapping("/order")
	public String index(Model model) {
		
		if(cart.getItems().size() < 1) {
			return "redirect:/cart";
		}

		Customer customer = customerRepository.findOneById(account.getId());
		model.addAttribute("customer", customer);

		List<Contact> contacts = contactRepository.findByCustomerIdOrderById(customer.getId());
		model.addAttribute("contacts", contacts);

		return "contactForm";
	}

	@PostMapping("/order/confirm")
	public String doOonfirm(
			@RequestParam(name = "id", defaultValue = "0") Integer id,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "tel", defaultValue = "") String tel,
			@RequestParam(name = "email", defaultValue = "") String email,
			Model model) {

		Contact contact = null;
		Customer customer = customerRepository.findOneById(account.getId());
		model.addAttribute("customer", customer);

		model.addAttribute("id", id);
		model.addAttribute("name", name);
		model.addAttribute("address", address);
		model.addAttribute("tel", tel);
		model.addAttribute("email", email);

		switch (id) {
		case -1:
			if (name.length() == 0 || address.length() == 0 || tel.length() == 0 || email.length() == 0) {
				String msg = "";

				List<Contact> contacts = contactRepository.findByCustomerIdOrderById(customer.getId());
				model.addAttribute("contacts", contacts);

				if (name.length() == 0)
					msg += "<p>名前が未入力です</p>";
				if (address.length() == 0)
					msg += "<p>住所が未入力です</p>";
				if (tel.length() == 0)
					msg += "<p>電話番号が未入力です</p>";
				if (email.length() == 0)
					msg += "<p>メールアドレスが未入力です</p>";

				model.addAttribute("msg", msg);
				return "contactForm";
			}
			contact = new Contact(account.getId(), name, address, tel, email);
			break;
		case 0:
			contact = new Contact(customer.getId(), customer.getName(), customer.getAddress(), customer.getTel(),
					customer.getEmail());
			break;
		default:
			contact = contactRepository.findOneById(id);
			break;
		}

		model.addAttribute("contact", contact);

		return "orderConfirm";
	}

	@PostMapping("/order")
	public String doOrder(
			@RequestParam(name = "id") Integer id,
			@RequestParam(name = "name") String name,
			@RequestParam(name = "address") String address,
			@RequestParam(name = "tel") String tel,
			@RequestParam(name = "email") String email,
			RedirectAttributes redirectAttributes) {

		Customer customer = customerRepository.findOneById(account.getId());
		Contact contact = new Contact(account.getId(), name, address, tel, email);

		if (id == -1) {
			contactRepository.save(contact);
			id = contact.getId();
		}

		Order order = new Order(customer.getId(), id, cart.getTotalPrice());

		orderRepository.save(order);

		List<Item> itemList = cart.getItems();
		List<OrderDetail> orderDetails = new ArrayList<>();
		for (Item item : itemList) {
			orderDetails.add(new OrderDetail(order.getId(), item.getId(), item.getQuantity()));
			item.setSellCount(item.getSellCount() + item.getQuantity());
			item.setStockCount(item.getStockCount() - item.getQuantity());
		}
		orderDetailRepository.saveAll(orderDetails);

		cart.clear();

		cart.setLastOrder(order.getId());

		return "redirect:/ordered";
	}

	@GetMapping("/ordered")
	public String doneOrder(
			Model model) {
		model.addAttribute("orderNumber", cart.getLastOrder());

		return "ordered";
	}

}
