package com.technomori.hroauth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.technomori.hroauth.entities.User;
import com.technomori.hroauth.network.UserServiceProxy;
import com.technomori.hroauth.services.UserService;
import com.technomori.hroauth.services.UserServiceImpl;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

	@TestConfiguration
	static class UserServiceImplTestContextConfiguration {
		@Bean
		public UserService userService() {
			return new UserServiceImpl();
		}
	}

	@Autowired
	private UserService userService;

	@MockBean
	private UserServiceProxy userServiceProxy;

	@BeforeEach
	public void setUp() {
		User alex = new User("Alex", "alex@email.com", "1234");

		Mockito.when(userServiceProxy.findByEmail(alex.getEmail()))
			.thenReturn(ResponseEntity.ok(alex));
		Mockito.when(userServiceProxy.findByEmail("wrong_email"))
			.thenReturn(ResponseEntity.ok(null));
	}

	@Test
	public void whenValidEmail_thenUserShouldBeFound() {
		User alex = new User("Alex", "alex@email.com", "1234");
		User found = userService.findByEmail(alex.getEmail());

		assertThat(found.getName()).isEqualTo(alex.getName());
	}

	@Test
	public void whenInvalidEmail_thenUserShouldNotBeFound() {
		User fromDb = userService.findByEmail("wrong_email");
		assertThat(fromDb).isNull();

		verifyFindByEmailIsCalledOnce("wrong_email");
	}

	private void verifyFindByEmailIsCalledOnce(String email) {
		Mockito.verify(userServiceProxy, VerificationModeFactory.times(1))
			.findByEmail(email);
		Mockito.reset(userServiceProxy);
	}

}
