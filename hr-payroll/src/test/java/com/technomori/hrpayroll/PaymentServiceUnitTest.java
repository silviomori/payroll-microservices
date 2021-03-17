package com.technomori.hrpayroll;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.technomori.hrpayroll.entities.Worker;
import com.technomori.hrpayroll.network.WorkerServiceProxy;
import com.technomori.hrpayroll.services.PaymentService;
import com.technomori.hrpayroll.services.PaymentServiceImpl;

@ExtendWith(SpringExtension.class)
public class PaymentServiceUnitTest {

	@Mock
	private WorkerServiceProxy workerServiceProxy;

	@InjectMocks
	private PaymentService paymentService = new PaymentServiceImpl();


	@BeforeEach
	public void setUp() {
		Worker john = new Worker("john");
		john.setId(11L);
		john.setDailyIncome(50d);

		Mockito.when(workerServiceProxy.getWorker(john.getId())).thenReturn(john);
	}

	@Test
	public void whenValidId_thenPaymentShouldBeCalculated() {
		Worker john = new Worker("john");
		john.setId(11L);

		assertThat(paymentService.getPayment(john.getId(), 3)).isNotNull();
	}

	@Test
	public void whenInvalidId_thenPaymentShouldNotBeCalculated() {
		assertThat(paymentService.getPayment(-99L, 3)).isNull();
	}

	@Test
	public void whenZeroDaysWorked_thenPaymentShouldBeZero() {
		Worker john = new Worker("john");
		john.setId(11L);

		assertThat(paymentService.getPayment(john.getId(), 0).getTotal()).isEqualTo(0);
	}

	@Test
	public void whenSomeDaysWorked_thenPaymentShouldBeCalculated() {
		Worker john = new Worker("john");
		john.setId(11L);

		assertThat(paymentService.getPayment(john.getId(), 5).getTotal()).isNotEqualTo(0);
	}

}
