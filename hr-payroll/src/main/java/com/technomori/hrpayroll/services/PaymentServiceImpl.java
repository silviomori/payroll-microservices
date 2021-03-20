package com.technomori.hrpayroll.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.technomori.hrpayroll.entities.Payment;
import com.technomori.hrpayroll.entities.Worker;
import com.technomori.hrpayroll.network.WorkerServiceProxy;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private WorkerServiceProxy workerServiceProxy;

	public PaymentServiceImpl() {
	}

	public Payment getPayment(Long workerId, Integer days) {
		ResponseEntity<Worker> response = workerServiceProxy.findById(workerId);
		if(response == null) {
			return null;
		}
		Worker worker = response.getBody();
		return new Payment(worker.getName(), worker.getDailyIncome(), days);
	}
}
