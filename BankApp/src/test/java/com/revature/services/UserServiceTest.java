package com.revature.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.IUserDAO;

public class UserServiceTest {
	
	@Mock
	private IUserDAO mockedDao;
	
	private UserService testInstance = new UserService(mockedDao);
	private User joey;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		testInstance = new UserService(mockedDao);
		
		joey = new User(1, "Joey", "Burss", "joeybankuser", "mypassword", "sample2email@sample.com", Role.Admin);
		
		when(mockedDao.findByUsername("joeybankuser")).thenReturn(joey);
		when(mockedDao.findByUsername(anyString())).thenReturn(null);
		when(mockedDao.findById(1)).thenReturn(joey);
		when(mockedDao.findById(anyInt())).thenReturn(null);
		// Regardless of what input value is provided, the fake DAO will return this specific
		// User object when the findByUsername method is invoked
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoginSuccessful() {
		assertEquals(testInstance.login("joeybankuser", "mypassword"), joey);
	}
	
	@Test
	public void testLoginFailure() {
		assertEquals(testInstance.login("joeybankuser", "wrongpw"), null);
	}

}