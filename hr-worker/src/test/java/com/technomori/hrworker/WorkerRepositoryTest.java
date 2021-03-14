package com.technomori.hrworker;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.technomori.hrworker.entities.Worker;
import com.technomori.hrworker.repositories.WorkerRepository;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class WorkerRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private WorkerRepository workerRepository;
	
	@Test
    public void whenFindByName_thenReturnWorker() {
        Worker alex = new Worker("alex");
        entityManager.persistAndFlush(alex);

        Worker found = workerRepository.findByName(alex.getName());
        assertThat(found.getName()).isEqualTo(alex.getName());
    }
	
	@Test
    public void whenInvalidName_thenReturnNull() {
        Worker fromDb = workerRepository.findByName("doesNotExist");
        assertThat(fromDb).isNull();
    }

    @Test
    public void whenFindById_thenReturnWorker() {
        Worker worker = new Worker("test");
        worker = entityManager.persistAndFlush(worker);

        Worker fromDb = workerRepository.findById(worker.getId()).orElse(null);
        assertThat(fromDb.getName()).isEqualTo(worker.getName());
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Worker fromDb = workerRepository.findById(-11l).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfWorkers_whenFindAll_thenReturnAllWorkers() {
        Worker alex = new Worker("alex");
        Worker ron = new Worker("ron");
        Worker bob = new Worker("bob");

        entityManager.persist(alex);
        entityManager.persist(ron);
        entityManager.persist(bob);
        entityManager.flush();

        List<Worker> allWorkers = workerRepository.findAll();

        assertThat(allWorkers).hasSize(3).extracting(Worker::getName)
        	.containsOnly(alex.getName(), ron.getName(), bob.getName());
    }
}
