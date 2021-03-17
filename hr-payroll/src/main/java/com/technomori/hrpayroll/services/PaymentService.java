package com.technomori.hrpayroll.services;

import com.technomori.hrpayroll.entities.Payment;

public interface PaymentService {

	public Payment getPayment(Long workerId, Integer days);

}
