package com.technomori.hroauth;

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

import com.technomori.hroauth.entities.User;
import com.technomori.hroauth.services.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = HrOauthApplication.class)
@AutoConfigureMockMvc
public class UserRestControllerUnitTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserService userService;

	@Test
	public void givenUser_whenGetByEmail_thenReturnUser() throws Exception {
		User user = new User("John", "john@email.com", "1234");

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
