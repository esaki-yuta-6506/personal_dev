package com.example.demo.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Contact;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Item;
import com.example.demo.entity.Mode;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.Review;
import com.example.demo.entity.Shop;
import com.example.demo.model.Account;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.ModeRepository;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PlanRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.ShopRepository;

@Controller
public class AdminAccountController {

	@Autowired
	Account account;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	ShopRepository shopRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	ReviewRepository reviewRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderDetailRepository orderDetailRepository;

	@Autowired
	ModeRepository modeRepository;

	@Autowired
	PlanRepository planRepository;

	@Autowired
	ContactRepository contactRepository;

	@GetMapping("/admin")
	public String index() {
		return "admin/admin";
	}

	@GetMapping("/admin/account")
	public String view(
			@ModelAttribute(name = "msg") String msg,
			Model model) {
		model.addAttribute("msg", msg);

		List<Customer> customers = customerRepository.findByOrderById();
		model.addAttribute("customers", customers);
		List<Mode> modes = modeRepository.findByOrderById();
		model.addAttribute("modes", modes);

		return "admin/adminAccount";
	}

	@GetMapping("/admin/account/{id}")
	public String viewAccount(
			@PathVariable("id") Integer id,
			Model model) {
		Customer customer = customerRepository.findOneById(id);
		model.addAttribute("customer", customer);

		List<Contact> contacts = contactRepository.findByCustomerIdOrderById(id);
		model.addAttribute("contacts", contacts);

		List<Review> reviews = reviewRepository.findByCustomerIdOrderById(id);
		model.addAttribute("reviews", reviews);

		List<Order> orders = orderRepository.findByCustomerIdOrderById(id);
		model.addAttribute("orders", orders);

		List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
		for (Order order : orders) {
			orderDetails.addAll(orderDetailRepository.findByOrderId(order.getId()));
		}
		orderDetails.sort((od1, od2) -> od1.getId() - od2.getId());
		model.addAttribute("orderDetails", orderDetails);

		List<Mode> modes = modeRepository.findByOrderById();
		model.addAttribute("modes", modes);

		List<Item> items = itemRepository.findByOrderById();
		model.addAttribute("items", items);

		return "admin/adminAccountDetail";
	}

	@GetMapping("/admin/account/add")
	public String addAccount(Model model) {
		List<Mode> modes = modeRepository.findByOrderById();
		model.addAttribute("modes", modes);

		return "admin/addAccount";
	}

	@PostMapping("/admin/account/add")
	public String storeAccount(
			@RequestParam(name = "modeId", defaultValue = "") Integer modeId,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "tel", defaultValue = "") String tel,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "password", defaultValue = "") String password,
			@RequestParam(name = "rePassword", defaultValue = "") String rePassword,
			Model model) {
		List<Mode> modes = modeRepository.findByOrderById();
		model.addAttribute("modes", modes);

		Customer customer = customerRepository.findOneByEmail(email);

		if (name.length() == 0 || email.length() == 0 || password.length() == 0 || rePassword.length() == 0
				|| !password.equals(rePassword) || address.length() == 0 || tel.length() == 0 || customer != null) {
			String msg = "";

			if (name.length() == 0)
				msg += "<p>名前を入力してください</p>";
			if (email.length() == 0)
				msg += "<p>メールアドレスを入力してください</p>";
			if (customer != null)
				msg += "<p>そのメールアドレスは既に使用されています　別のメールアドレスを使用してください</p>";
			if (password.length() == 0)
				msg += "<p>パスワードを入力してください</p>";
			if (rePassword.length() == 0)
				msg += "<p>パスワードを再入力してください</p>";
			if (!password.equals(rePassword))
				msg += "<p>パスワードが一致していません</p>";
			if (address.length() == 0)
				msg += "<p>住所を入力してください</p>";
			if (tel.length() == 0)
				msg += "<p>電話番号を入力してください</p>";

			model.addAttribute("name", name);
			model.addAttribute("email", email);
			model.addAttribute("address", address);
			model.addAttribute("tel", tel);
			model.addAttribute("msg", msg);

			return "admin/addAccount";
		}

		customer = new Customer(modeId, name, address, tel, email, password);
		customerRepository.save(customer);

		return "redirect:/admin/account";
	}

	@GetMapping("/admin/account/set/{id}")
	public String setAccount(
			@PathVariable("id") Integer id,
			Model model) {
		List<Mode> modes = modeRepository.findByOrderById();
		model.addAttribute("modes", modes);

		Customer customer = customerRepository.findOneById(id);
		model.addAttribute("customer", customer);

		model.addAttribute("modeId", customer.getModeId());
		model.addAttribute("name", customer.getName());
		model.addAttribute("email", customer.getEmail());
		model.addAttribute("address", customer.getAddress());
		model.addAttribute("tel", customer.getTel());
		model.addAttribute("password", customer.getPassword());

		return "admin/setAccount";
	}

	@PostMapping("/admin/account/set/{id}")
	public String sendAccount(
			@PathVariable("id") Integer id,
			@RequestParam(name = "modeId", defaultValue = "") Integer modeId,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "tel", defaultValue = "") String tel,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "password", defaultValue = "") String password,
			@RequestParam(name = "rePassword", defaultValue = "") String rePassword,
			Model model) {
		List<Mode> modes = modeRepository.findByOrderById();
		model.addAttribute("modes", modes);

		Customer customer = customerRepository.findOneById(id);
		Customer eCustomer = null;
		if (!email.equals(customer.getEmail()))
			eCustomer = customerRepository.findOneByEmail(email);

		List<Customer> customers = customerRepository.findByModeIdOrderById(1);

		if (name.length() == 0 || email.length() == 0 || password.length() == 0 || rePassword.length() == 0
				|| !password.equals(rePassword) || address.length() == 0 || tel.length() == 0
				|| !email.equals(customer.getEmail()) && customer != null
				|| modeId != 1 && customer.getModeId() == 1 && customers.size() == 1) {
			String msg = "";

			if (modeId != 1 && customer.getModeId() == 1 && customers.size() == 1)
				msg += "<p>管理者アカウントがなくなります　先に管理者アカウントを新たに作成するかモード変更をお辞めください</p>";
			if (name.length() == 0)
				msg += "<p>名前を入力してください</p>";
			if (email.length() == 0)
				msg += "<p>メールアドレスを入力してください</p>";
			if (!email.equals(customer.getEmail()) && eCustomer == null)
				msg += "<p>そのメールアドレスは既に使用されています　別のメールアドレスを使用してください</p>";
			if (password.length() == 0)
				msg += "<p>パスワードを入力してください</p>";
			if (rePassword.length() == 0)
				msg += "<p>パスワードを再入力してください</p>";
			if (!password.equals(rePassword))
				msg += "<p>パスワードが一致していません</p>";
			if (address.length() == 0)
				msg += "<p>住所を入力してください</p>";
			if (tel.length() == 0)
				msg += "<p>電話番号を入力してください</p>";

			model.addAttribute("customer", customer);

			model.addAttribute("modeId", modeId);
			model.addAttribute("name", name);
			model.addAttribute("email", email);
			model.addAttribute("address", address);
			model.addAttribute("tel", tel);
			model.addAttribute("password", password);
			model.addAttribute("msg", msg);

			return "admin/setAccount";
		}

		if (account.getId() == id) {
			account.setId(id);
			account.setModeId(modeId);
			account.setName(name);
		}

		customer = new Customer(id, modeId, name, address, tel, email, password);
		customerRepository.save(customer);

		return "redirect:/admin/account";
	}

	@GetMapping("/admin/account/delete/{id}")
	public String deleteaccount(
			@PathVariable("id") Integer id,
			RedirectAttributes redirectAttributes) {

		Customer customer = customerRepository.findOneById(id);
		List<Customer> customers = customerRepository.findByModeIdOrderById(1);

		if (customer.getModeId() == 1 && customers.size() == 1) {
			String msg = "管理者アカウントがなくなります\r\n先に管理者アカウントを新たに作成するか\r\nこのアカウントの削除をお辞めください";

			redirectAttributes.addFlashAttribute("msg", msg);
		} else {
			if (customer.getModeId() < 3) {
				List<Shop> shops = shopRepository.findByCustomerIdOrderById(id);
				for (Shop shop : shops) {
					List<Item> items = itemRepository.findByShopIdOrderById(shop.getId());
					for (Item item : items) {
						item.setStatus(0);
						item.setStockCount(0);
						itemRepository.save(item);
					}
					shop.setStatus(0);
					shopRepository.save(shop);
				}
			}

			List<Contact> contacts = contactRepository.findByCustomerIdOrderById(id);
			for (Contact contact : contacts) {
				contactRepository.deleteById(contact.getId());
			}

			customerRepository.deleteById(id);
			if (account.getId() == id) {
				account.setId(null);
				account.setModeId(null);
				account.setName(null);
			}
		}
		return "redirect:/admin/account";
	}

}
