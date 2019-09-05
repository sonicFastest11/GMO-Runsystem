package com.gmo.controller.admin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gmo.model.Course;
import com.gmo.model.Enrolment;
import com.gmo.model.Profile;
import com.gmo.model.Role;
import com.gmo.model.Users;
import com.gmo.service.GenericService;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Controller
public class UserController {
	@Autowired
	private GenericService<Enrolment> enrolmentService;
	@Autowired
	private GenericService<Course> courseService;
	@Autowired
	private GenericService<Users> userService;
	@Autowired
	private GenericService<Role> roleService;
	@Autowired
	private GenericService<Profile> profileService;

	public UserController() {
		System.out.println("UserController()");
	}

	// take register form
	@RequestMapping(value = "/addUser")
	public ModelAndView addUser(ModelAndView model) {
		Users user = new Users();
		model.addObject("message", user.getUsername());
		model.addObject("user", user);
		model.setViewName("admin/user/addUser");
		return model;
	}

	// Register to save user
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveUser(@ModelAttribute("user") Users user, ModelAndView model) {
		if (!userService.checkUser(user.getUsername())) {
			if (user.getPassword().equals(user.getConfirmPassword())) {
				user.setRoleid(roleService.get(user.getIdRole()));
				userService.create(user);
				Profile profile = new Profile(user.getUsername(), "10/10/1998", "Nam", user);
				profileService.create(profile);
				return new ModelAndView("redirect:/userList");
			} else {
				model.setViewName("admin/user/register");
				model.addObject("message", "Password not match !!!");
				return model;
			}
		} else {
			model.setViewName("admin/user/addUser");
			model.addObject("message", "Username has been existed !!!");
			return model;
		}

	}

	// take list of Users
	@RequestMapping(value = "/userList")
	public String loadAllUser(ModelMap model, HttpSession session) {
		List<Users> list = userService.listAll();
		model.addAttribute("list", list);
		return "admin/user/listUsers";
	}

	@ModelAttribute("roles")
	public List<Role> getRoles() {
		List<Role> list = roleService.listAll();
		return list;
	}

	// delete User
	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public ModelAndView deleteUser(HttpServletRequest request) {
		int userId = Integer.parseInt(request.getParameter("id"));
		userService.delete(userId);
		return new ModelAndView("redirect:/userList");
	}

	// update Role of User
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public ModelAndView updateUser(@ModelAttribute Users user) {
		Role role = roleService.get(user.getIdRole());
		userService.updateUser(role, user.getUsername());
		return new ModelAndView("redirect:/userList");

	}

	// take the idRole in URL
	@RequestMapping(value = "/editUser", method = RequestMethod.GET)
	public ModelAndView editUser(HttpServletRequest request) {
		int userId = Integer.parseInt(request.getParameter("id"));
		Users user = userService.get(userId);
		ModelAndView model = new ModelAndView("admin/user/userEditForm");
		model.addObject("user", user);

		return model;
	}

	// take the list Teacher
	@RequestMapping(value = "/teacherList")
	public String listTeacher(ModelMap model, HttpSession session) {
		List<Users> listTeacher = userService.listTeacher();
		model.addAttribute("listTeacher", listTeacher);
		return "admin/course/listTeacher";
	}

	// come to import form
	@RequestMapping(value = "/importPage")
	public String importPage() {
		return "admin/user/uploadCSVFile";
	}

	// Import
	@RequestMapping(value = "/importUser")
	public ModelAndView importUser(@RequestParam("file") MultipartFile file, ModelAndView model) {
		if (file.isEmpty()) {
			model.setViewName("admin/user/uploadCSVFile");
			model.addObject("importMessage", "Please, choose a file !!!");
			return model;
		} else {
			String path = "C:\\Users\\Admin\\Downloads\\Documents\\" + file.getOriginalFilename();
			BufferedReader br = null;
			try {
				String line;
				Users userEx = null;

				br = new BufferedReader(new FileReader(path));
				List<Users> users = new ArrayList<Users>();
				while ((line = br.readLine()) != null) {
					List<String> list = parseCsvLine(line);
					userEx = convertToUser(list.get(0), list.get(1), list.get(2), list.get(3));
					userService.create(userEx);
					Profile profile = new Profile(userEx.getUsername(), "10/10/1998", "Nam", userEx);
					profileService.create(profile);
				}
				System.err.println(users.size());
			} catch (Exception e) {

			}
			model.setViewName("redirect:/userList");
			model.addObject("importMessage", "Import successfully !!!");
			return model;
		}
	}

	private List<String> parseCsvLine(String csvLine) {
		List<String> result = new ArrayList<String>();
		if (csvLine != null) {
			String[] splitData = csvLine.split(",");
			for (int i = 0; i < splitData.length; i++) {
				result.add(splitData[i]);
			}
		}
		return result;
	}

	private Users convertToUser(String username, String password, String confirmPassword, String roleid) {
		Users user = new Users();
		Integer roleId = Integer.valueOf(roleid);
		user.setUsername(username);
		user.setPassword(password);
		user.setConfirmPassword(confirmPassword);
		user.setRoleid(roleService.get(roleId));
		return user;
	}
}
