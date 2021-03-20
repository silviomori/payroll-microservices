package com.technomori.hrpayroll.network;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.technomori.hrpayroll.entities.Worker;

@Component
@FeignClient(name = "hr-worker", url = "localhost:8001", path = "/workers")
public interface WorkerServiceProxy {

	@GetMapping(value = "/{id}")
	ResponseEntity<Worker> findById(@PathVariable("id") Long id);

}
