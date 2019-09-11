package com.gmo.service;

import java.util.List;

import com.gmo.model.Course;
import com.gmo.model.Profile;
import com.gmo.model.Role;
import com.gmo.model.Users;

public interface GenericService<T> {
	public void create(T t);

	public T update(T t);

	public T get(Object id);

	public T getDefaultRole(Object id);

	public void delete(Object id);

	public List<T> listTeacher();
	public List<T> listEnrolmentOfAStudent(int userid);

	public List<T> listAll();
	public boolean checkCourse(String code);
	public boolean checkLogin(String username, String password);
	public boolean checkEnrolment(Users userid, Course courseid);
	public boolean checkUser(String username);

	public T checkRole(String t);

	public boolean checkRoleName(String role_name);

	public Profile getProfile(Users user);

	public boolean updateUser(Role role, String username);

	public boolean updateProfile(String name, String address, String dob, String gender, String phone, String email,
			 String status, int id);
}
