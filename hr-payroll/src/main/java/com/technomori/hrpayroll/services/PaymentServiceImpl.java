package com.technomori.hrpayroll.services;


import org.springframework.beans.factory.annotation.Autowired;
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
		Worker worker = workerServiceProxy.getWorker(workerId);
		if(worker == null) {
			return null;
		}
		return new Payment(worker.getName(), worker.getDailyIncome(), days);
	}
}
