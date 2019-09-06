package com.gmo.dao.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.gmo.model.Role;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
public class RoleDAOTest {

	@Test
	public void testCreate() {
		Role role = new Role();
		role.setRole_name("Sub-Admin");
		RoleDAO roleDAO = new RoleDAO();
		roleDAO.create(role);
		assertTrue(role.getId()>0);
	}

}
