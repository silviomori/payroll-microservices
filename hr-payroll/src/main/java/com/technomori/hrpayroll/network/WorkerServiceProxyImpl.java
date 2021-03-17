package com.technomori.hrpayroll.network;

import org.springframework.stereotype.Service;

import com.technomori.hrpayroll.entities.Worker;

@Service
public class WorkerServiceProxyImpl implements WorkerServiceProxy {

	@Override
	public Worker getWorker(Long id) {
		return null;
	}

}
