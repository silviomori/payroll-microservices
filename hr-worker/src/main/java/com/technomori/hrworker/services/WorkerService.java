package com.technomori.hrworker.services;

import java.util.List;

import com.technomori.hrworker.entities.Worker;

public interface WorkerService {

	public Worker save(Worker worker);

	public List<Worker> findAll();

	public Worker findById(Long id);

	public Worker findByName(String name);

	public boolean exists(String name);

}
