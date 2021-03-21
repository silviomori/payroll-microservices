package com.technomori.hrworker;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.technomori.hrworker.entities.Worker;
import com.technomori.hrworker.repositories.WorkerRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = HrWorkerApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class WorkerRestControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private WorkerRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

	@Test
	public void givenEmployees_whenGetEmployees_thenStatus200()
	  throws Exception {

	    createTestWorker("bob");

	    mvc.perform(get("/workers")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(content()
	      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("$[0].name", is("bob")));
	}

    @Test
    public void givenWorkers_whenGetWorkers_thenStatus200() throws Exception {
        createTestWorker("bob");
        createTestWorker("alex");

        // @formatter:off
        mvc.perform(get("/workers").contentType(MediaType.APPLICATION_JSON))
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
          .andExpect(jsonPath("$[0].name", is("bob")))
          .andExpect(jsonPath("$[1].name", is("alex")));
        // @formatter:on
    }

    private void createTestWorker(String name) {
        Worker emp = new Worker(name);
        repository.saveAndFlush(emp);
    }
}
