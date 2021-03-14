package com.technomori.hrworker.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technomori.hrworker.entities.Worker;
import com.technomori.hrworker.repositories.WorkerRepository;

@Service
@Transactional
public class WorkerServiceImpl implements WorkerService {
	
	@Autowired
	private WorkerRepository workerRepository;

	@Override
	public Worker findById(Long id) {
		return workerRepository.findById(id).orElse(null);
	}

	@Override
	public Worker findByName(String name) {
		return workerRepository.findByName(name);
	}

	@Override
	public List<Worker> findAll() {
		return workerRepository.findAll();
	}

	@Override
	public boolean exists(String name) {
		return findByName(name) != null;
	}

	@Override
	public Worker save(Worker worker) {
		return workerRepository.save(worker);
	}

}
