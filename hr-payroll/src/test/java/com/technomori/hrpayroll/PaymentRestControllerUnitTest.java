package com.technomori.hrpayroll;

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

import com.technomori.hrpayroll.entities.Payment;
import com.technomori.hrpayroll.entities.Worker;
import com.technomori.hrpayroll.services.PaymentService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = HrPayrollApplication.class)
@AutoConfigureMockMvc
public class PaymentRestControllerUnitTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private PaymentService paymentService;

	@Test
	public void givenWorker_whenGetPayment_thenStatus200() throws Exception {
		Worker worker = new Worker("John");
		worker.setId(11L);
		worker.setDailyIncome(150d);
		Integer days = 3;
		Mockito.when(paymentService.getPayment(worker.getId(), days))
			.thenReturn(new Payment(worker.getName(), worker.getDailyIncome(), days));

	    mvc.perform(get("/payments/"+worker.getId()+"/days/"+days)
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(content()
		      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("name", is(worker.getName())))
	      .andExpect(jsonPath("dailyIncome", is(worker.getDailyIncome())));
	}

	@Test
	public void givenInvalidWorker_whenGetPayment_thenReturnBlank() throws Exception {
		Worker worker = new Worker("Bob");
		worker.setId(22L);
		worker.setDailyIncome(250d);
		Integer days = 2;
		Mockito.when(paymentService.getPayment(33L, days))
			.thenReturn(null);

	    mvc.perform(get("/payments/"+worker.getId()+"/days/"+days)
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(content().string(""));
	}

}
