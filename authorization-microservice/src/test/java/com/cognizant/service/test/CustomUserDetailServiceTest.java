package com.cognizant.service.test ;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.service.CustomUserDetailService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomUserDetailServiceTest {

	@Autowired(required = true)
	CustomUserDetailService customUserDetailService;

	@Test
	public void contextLoads() {

		assertNotNull(customUserDetailService);

	}
	@Test
	public void contextLoadsFail() {

		assertNull(customUserDetailService) ;

	}
	@Test
	public void loadUserByUsernameTestSuccess() {

		assertEquals("Iftak", customUserDetailService.loadUserByUsername("Iftak").getUsername());
	}

	@Test(expected = UsernameNotFoundException.class)
	public void loadUserByUsernameTestFail() {

		assertNotEquals("Random", customUserDetailService.loadUserByUsername("Iftak").getUsername());
	}
}
