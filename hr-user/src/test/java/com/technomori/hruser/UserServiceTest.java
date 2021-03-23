package com.technomori.hruser;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.technomori.hruser.entities.User;
import com.technomori.hruser.repositories.UserRepository;
import com.technomori.hruser.services.UserService;
import com.technomori.hruser.services.UserServiceImpl;

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
	private UserRepository userRepository;

	@BeforeEach
	public void setUp() {
		User john = new User("John", "john@email.com", "1234");
		User bob = new User("Bob", "bob@email.com", "1234");
		User alex = new User("Alex", "alex@email.com", "1234");

		List<User> allUsers = Arrays.asList(john, bob, alex);

		Mockito.when(userRepository.findByEmail(john.getEmail())).thenReturn(john);
		Mockito.when(userRepository.findByEmail(alex.getEmail())).thenReturn(alex);
		Mockito.when(userRepository.findByEmail("wrong_email")).thenReturn(null);
		Mockito.when(userRepository.findById(11L)).thenReturn(Optional.of(john));
		Mockito.when(userRepository.findById(-99L)).thenReturn(Optional.empty());
		Mockito.when(userRepository.findAll()).thenReturn(allUsers);
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

	@Test
	public void whenValidEmail_thenUserShouldExist() {
		boolean doesUserExist = userService.exists("john@email.com");
		assertThat(doesUserExist).isEqualTo(true);

		verifyFindByEmailIsCalledOnce("john@email.com");
	}

	@Test
	public void whenInvalidEmail_thenUserShouldNotExist() {
		boolean doesUserExist = userService.exists("wrong_email");
		assertThat(doesUserExist).isEqualTo(false);

		verifyFindByEmailIsCalledOnce("wrong_email");
	}

	@Test
	public void whenValidId_thenUserShouldBeFound() {
		User fromDb = userService.findById(11L);
		assertThat(fromDb.getEmail()).isEqualTo("john@email.com");

		verifyFindByIdIsCalledOnce();
	}

	@Test
	public void whenInvalidId_thenUserShouldNotBeFound() {
		User fromDb = userService.findById(-99L);
		verifyFindByIdIsCalledOnce();
		assertThat(fromDb).isNull();
	}

	@Test
	public void given3Users_whengetAll_thenReturn3Records() {
		User john = new User("John", "john@email.com", "1234");
		User bob = new User("Bob", "bob@email.com", "1234");
		User alex = new User("Alex", "alex@email.com", "1234");

		List<User> allUsers = userService.findAll();
		verifyFindAllUsersIsCalledOnce();
		assertThat(allUsers)
			.hasSize(3)
			.extracting(User::getName)
				.contains(alex.getName(), john.getName(), bob.getName());
	}

	private void verifyFindByEmailIsCalledOnce(String email) {
		Mockito.verify(userRepository, VerificationModeFactory.times(1))
			.findByEmail(email);
		Mockito.reset(userRepository);
	}

	private void verifyFindByIdIsCalledOnce() {
		Mockito.verify(userRepository, VerificationModeFactory.times(1))
			.findById(Mockito.anyLong());
		Mockito.reset(userRepository);
	}

	private void verifyFindAllUsersIsCalledOnce() {
		Mockito.verify(userRepository, VerificationModeFactory.times(1))
			.findAll();
		Mockito.reset(userRepository);
	}
}
