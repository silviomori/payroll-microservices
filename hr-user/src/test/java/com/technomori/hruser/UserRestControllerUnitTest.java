package com.technomori.hruser;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.technomori.hruser.entities.Role;
import com.technomori.hruser.entities.User;
import com.technomori.hruser.services.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = HrUserApplication.class)
@AutoConfigureMockMvc
public class UserRestControllerUnitTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserService userService;

	@Test
	public void givenUser_whenGetById_thenReturnUser() throws Exception {
		Role operator = new Role("Operator");
		User user = new User("John", "john@email.com", "1234");
		user.addRole(operator);

		Mockito.when(userService.findById(11L))
			.thenReturn(user);

	    mvc.perform(get("/users/"+11L)
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(content()
		      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("name", is(user.getName())))
	      .andExpect(jsonPath("email", is(user.getEmail())));
	}

	@Test
	public void givenUser_whenGetByInvalidId_thenReturnBlank() throws Exception {
		Role operator = new Role("Operator");
		User user = new User("John", "john@email.com", "1234");
		user.addRole(operator);

		Mockito.when(userService.findById(-99L))
			.thenReturn(null);

	    mvc.perform(get("/users/"+(-99L))
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(content().string(""));
	}

	@Test
	public void givenUser_whenGetByEmail_thenReturnUser() throws Exception {
		Role operator = new Role("Operator");
		User user = new User("John", "john@email.com", "1234");
		user.addRole(operator);

		Mockito.when(userService.findByEmail(user.getEmail()))
			.thenReturn(user);

	    mvc.perform(get("/users/search?email="+user.getEmail())
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(content()
		      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("name", is(user.getName())))
	      .andExpect(jsonPath("email", is(user.getEmail())));
	}

	@Test
	public void givenUser_whenGetByInvalidEmail_thenReturnBlank() throws Exception {
		Mockito.when(userService.findByEmail("invalid_email"))
			.thenReturn(null);

	    mvc.perform(get("/users/search?email=invalid_email")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(content().string(""));
	}

}
