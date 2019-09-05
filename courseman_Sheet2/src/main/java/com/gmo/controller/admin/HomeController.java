package com.gmo.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gmo.model.Users;
import com.gmo.service.GenericService;

@Controller
public class HomeController {
	@Autowired
	private GenericService<Users> userService;

	public HomeController() {
		System.out.println("HomeController()");
	}

	// come to index
	@RequestMapping(value = { "/", "/trang-chu" })
	public String returnHome() {
		return "admin/home/index";
	}

	// come to index
	@RequestMapping(value = "/home")
	public String homePage() {
		return "admin/home/home";
	}

	// take login form
	@RequestMapping(value = "/login")
	public ModelAndView returnLoginPage(ModelAndView model) {
		Users user = new Users();
		model.addObject("user", user);
		model.setViewName("admin/home/login");
		return model;
	}

	// login
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public ModelAndView checkLogin(@ModelAttribute("user") Users user, ModelAndView model, HttpServletRequest rq,
			HttpSession session) {
		Map<String, String> hashMap = new HashMap<>();
		if (userService.checkLogin(user.getUsername(), user.getPassword())) {
			String role = userService.checkRole(user.getUsername()).getRoleid().getRole_name();
			int userid = userService.checkRole(user.getUsername()).getId();
			if ("ADMIN".equals(userService.checkRole(user.getUsername()).getRoleid().getRole_name())) {
				hashMap.put("loadallrole", "List of Roles");
				hashMap.put("userList", "List of Users");
				hashMap.put("courseList", "List of Courses");
				hashMap.put("teacherList", "List of Teachers");
				hashMap.put("enrolmentList", "List of Enrolments");
				model.addObject("menu", hashMap);
			} else {
				hashMap.put("teachers", "List of Teachers");
				hashMap.put("enrolments", "List of Enrolments");
				hashMap.put("courses", "List of Courses");
				model.addObject("menu", hashMap);
			}
			session.setAttribute("user", user);
			session.setAttribute("userid", userid);
			session.setAttribute("role", role);

//			System.err.println(userid);
			session.setAttribute("name", user.getUsername());
//			System.err.println(session.getAttribute("name"));
			session.setAttribute("menu", hashMap);
			if (session.getAttribute("user") != null) {
				System.out.println("contain user");
			}
			model.setViewName("admin/home/home");
			model.addObject("message", user.getUsername());
			return model;
		} else {
			model.setViewName("admin/home/login");
			model.addObject("message", "Incorrect Usename or Password !!!");
			return model;
		}
	}

	// logout
	@RequestMapping(value = "/logout")
	public ModelAndView logout(@ModelAttribute("user") Users user, ModelAndView model, HttpServletRequest rq) {
		HttpSession session = rq.getSession();
		session.removeAttribute("user");
		session.removeAttribute("name");
		session.removeAttribute("menu");
		if (session.getAttribute("user") == null) {
			System.out.println("delete user session!");
		}

		return new ModelAndView("redirect:/trang-chu");
	}

	// return 403 page
	@RequestMapping("403")
	public String errorPage() {
		return "admin/home/403";
	}

}
