package in.cp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.apiclub.captcha.Captcha;
import in.cp.entity.User_Data;
import in.cp.repo.UserRepository;
import in.cp.service.UserService;
import in.cp.utill.CaptchaUtill;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final int DEFAULT_PAGE_SIZE = 5;

	@Autowired
	private UserService service;

	@Autowired
	private UserRepository repo;

	@GetMapping("/register")
	public String registerUser(Model model, User_Data user) {
		getCaptcha(user);
		model.addAttribute("user", user);
		return "registerUser";
	}

	@PostMapping("/save")
	public String saveUser(@ModelAttribute User_Data user, Model model) {
		if (user.getCaptcha().equals(user.getHiddenCaptcha())) {
			service.createUser(user);
			model.addAttribute("message", "User Registered successfully!");
			return "redirect:allUsers";
		} else {
			model.addAttribute("message", "Invalid Captcha");
			getCaptcha(user);
			model.addAttribute("user", user);
		}
		return "registerUser";
	}

	@GetMapping("/edit")
	public String edit(@RequestParam("id") Integer id, Model model) {
		Optional<User_Data> findById = repo.findById(id);
		if (findById.isPresent()) {
			User_Data user = findById.get();
			model.addAttribute("user", user);
		}
		return "registerUser";
	}

	@GetMapping("/allUsers")
	public String getAllUsers(@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "" + DEFAULT_PAGE_SIZE) int size,
			@RequestParam(name = "keyword", defaultValue = "") String keyword, Model model) {

		Page<User_Data> usersPage = service.getUsers(page - 1, size, keyword);
		model.addAttribute("data", usersPage.getContent()); // List of users
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", usersPage.getTotalPages());
		model.addAttribute("keyword", keyword);
		model.addAttribute("pageSize", size);
		return "listUser";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("id") Integer id, Model model) {
		repo.deleteById(id);
		model.addAttribute("msg", "Record Deleted");
		model.addAttribute("emps", repo.findAll());
		return "redirect:/user/allUsers";
	}

	private void getCaptcha(User_Data user) {
		Captcha captcha = CaptchaUtill.createCaptcha(240, 70);
		user.setHiddenCaptcha(captcha.getAnswer());
		user.setCaptcha(""); // value entered by the User
		user.setRealCaptcha(CaptchaUtill.encodeCaptcha(captcha));
	}
}
