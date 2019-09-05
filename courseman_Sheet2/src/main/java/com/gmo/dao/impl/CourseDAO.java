package com.gmo.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gmo.dao.GenericDAO;
import com.gmo.model.Course;
import com.gmo.model.Profile;
import com.gmo.model.Role;
import com.gmo.model.Users;

@Repository
public class CourseDAO implements GenericDAO<Course> {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void create(Course t) {
		sessionFactory.getCurrentSession().save(t); 

	}

	@Override
	public Course update(Course course) {
		sessionFactory.getCurrentSession().update(course);
		return course;
	}

	@Override
	public Course get(Object id) {
		// TODO Auto-generated method stub
		return (Course) sessionFactory.getCurrentSession().get(Course.class, (Serializable) id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Course> listAll() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from Course").list();
	}

	@Override
	public void delete(Object id) {
		Course course = (Course) sessionFactory.getCurrentSession().load(Course.class, (Serializable) id);
		if (null != course) {
			this.sessionFactory.getCurrentSession().delete(course);
		}

	}

	@Override
	public boolean updateCourse(String code, String name, String time, String fee, String type, Users userid,
			String startDate, String endDate, int id) {
		String hql = "update Course c set c.code =:code , c.name=:name,c.time=:time,c.fee=:fee,c.type=:type,c.userid=:userid,c.startDate=:startDate,c.endDate=:endDate  "
				+ "where c.id =:id";
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try {
			Query query = session.createQuery(hql);
			query.setParameter("code", code).setParameter("name", name).setParameter("time", time)
					.setParameter("fee", fee).setParameter("type", type).setParameter("userid", userid)
					.setParameter("startDate", startDate).setParameter("endDate", endDate).setParameter("id", id);
			query.executeUpdate();
			t.commit();
			session.close();
			return true;

		} catch (Exception e) {
			t.rollback();
			session.close();
			return false;

		}
	}

	@Override
	public boolean updateProfile(String name, String address, String dob, String gender, String phone, String email,
			String classCode, String status, int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Course> listTeacher() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Course> listEnrolmentOfAStudent(int userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkEnrolment(Users userid, Course courseid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Course getDefaultRole(Object id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Profile getProfile(Users user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkLogin(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkUser(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkRoleName(String role_name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Course checkRole(String t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateUser(Role role, String username) {
		// TODO Auto-generated method stub
		return false;
	}
}
