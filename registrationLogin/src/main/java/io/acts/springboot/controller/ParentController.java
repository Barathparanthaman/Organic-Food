package io.acts.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import io.acts.springboot.entity.Suggestion;
import io.acts.springboot.entity.User;
import io.acts.springboot.repository.SuggestionRepository;
import io.acts.springboot.repository.UserRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class ParentController {
	String fullName;
	String userName;
	User dbUser;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SuggestionRepository sugRepo;
	@Autowired
	private SuggService suggService;
	
	
//	@RequestMapping("/")
	@GetMapping("/")
//	@ResponseBody
	public String show() {
//		return "Welcome toSpringboot registration and login";
		return "hlo";
	}
	
	
	@GetMapping("/login")
	public String loginPage(User user) {
		return "login";
	}
	//handler for registraion page
	@GetMapping("/registration")
	public String registrationPage( User user) {
//		User user=new User();
		return "register";
	}
	
//	
	@GetMapping("/mainPage")
	public String mainPage(HttpSession session,Model model,User user) {
		if(session.getAttribute("authenticated")!=null) {
			model.addAttribute("fullName",fullName);
//			model.addAttribute("user",user);
			return "main";
		}else {
			return "redirect:login";
		}
		
	}
	@GetMapping("/sug")
	public String SugPage() {
		return "sugg";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute("user") User user) {
		System.out.println(user);
		//user.setFullname("SETMETHOD");
		User UN=userRepository.findByUsername(user.getUsername());
//		
		if (UN == null) {
		String encodedPasswword=BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(12) );
		user.setPassword(encodedPasswword);
		userRepository.save(user);
//		session.setAttribute("authenticated",true);
		return "redirect:/login?success";}
		
		
//		model.addAttribute("userexist", user);
		
		return "redirect:/registeration";
		
		
	}
	
	@PostMapping ("/loginhand")
//	@ResponseBody
	public String loginhandled(@RequestParam("username") String username,@RequestParam("password") String password,HttpSession session,Model model) {
		 dbUser=userRepository.findByUsername(username);
		userName=username;
//		User dbName=userRepository.findByUserName(username);
		fullName=dbUser.getFullname();
;		Boolean isPasswordMatch=BCrypt.checkpw(password,dbUser.getPassword());
		if(isPasswordMatch) {
			
			session.setAttribute("authenticated",true);
			session.setAttribute("dbUser",dbUser.getUsername());
			
//			return "Welcome to dashBoard of user"+dbUser.getFullname();
			return "redirect:/mainPage";
		}else {
		return "redirect:/?failed";
	}
	}
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
//		session.invalidate();
		session.removeAttribute("authenticated");
		session.removeAttribute("dbUser");
		return "redirect:/";
	}
	
	
	
	
	
	@GetMapping("/contact")
	public String ContactForm(Suggestion sugg,Model model,HttpSession session) {
		List<String> type=new ArrayList<>();
		type.add("Fruit");
		type.add("Vegetable");
		type.add("Grains");
		if(session.getAttribute("authenticated")!=null) {
		model.addAttribute("sugg",sugg);
		model.addAttribute("type",type);
		return "contact";
		}else {	
		
		return "redirect:/login";
	}
		}
	
	@PostMapping("/contacthand")
	public String ContactHanded(@ModelAttribute("sugg") Suggestion sugg,User dbUser ,HttpSession session) {
		
		String  UN=(String)session.getAttribute("dbUser");
		sugg.setUsename(UN);
		sugRepo.save(sugg);
		return "redirect:contact?success";
		}
	
	
	// API for types of items
	
	@GetMapping("/types/fruit")
	public String VegetableList() {
		return "Fruit";
	}
	
	@GetMapping("/types/vegetables")
	public String GrainstList() {
		return "Vegetable";
	}
	
	@GetMapping("/types/grains")
	public String fruitList() {
		return "Grains";
	}
		
	
	@GetMapping("/suggestions")
	public String showSugg(Model model) {
		model.addAttribute("allSuggestions",suggService.getAllSuggestions());
		return "sugg";
	}
	
	@GetMapping("/suggestion/{type}")
	public String ShowByTypes(@PathVariable(value="type") String type,Model model) {
		model.addAttribute("allSuggestions",suggService.getByTypeSuggestion(type));
		return "sugg";
	}

//	
//	@GetMapping("/")
////	@RequestMapping("/")
//	public String displayEmployees(Model model) {
//		model.addAttribute("allEmployees",employeeService.getAllEmployee());
//		return "index";
//	}
	
	
	

}
