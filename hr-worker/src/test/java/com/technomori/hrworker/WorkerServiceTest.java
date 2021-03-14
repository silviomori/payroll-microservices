package com.technomori.hrworker;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.technomori.hrworker.entities.Worker;
import com.technomori.hrworker.repositories.WorkerRepository;
import com.technomori.hrworker.services.WorkerService;
import com.technomori.hrworker.services.WorkerServiceImpl;

@ExtendWith(SpringExtension.class)
public class WorkerServiceTest {

	@TestConfiguration
	static class WorkerServiceImplTestContextConfiguration {
		@Bean
		public WorkerService workerService() {
			return new WorkerServiceImpl();
		}
	}

	@Autowired
	private WorkerService workerService;

	@MockBean
	private WorkerRepository workerRepository;

	@BeforeEach
	public void setUp() {
		Worker john = new Worker("john");
		john.setId(11L);
		Worker bob = new Worker("bob");
		Worker alex = new Worker("alex");

		List<Worker> allWorkers = Arrays.asList(john, bob, alex);

		Mockito.when(workerRepository.findByName(john.getName())).thenReturn(john);
		Mockito.when(workerRepository.findByName(alex.getName())).thenReturn(alex);
		Mockito.when(workerRepository.findByName("wrong_name")).thenReturn(null);
		Mockito.when(workerRepository.findById(john.getId())).thenReturn(Optional.of(john));
		Mockito.when(workerRepository.findAll()).thenReturn(allWorkers);
		Mockito.when(workerRepository.findById(-99L)).thenReturn(Optional.empty());
	}

	@Test
	public void whenValidName_thenWorkerShouldBeFound() {
		String name = "alex";
		Worker found = workerService.findByName(name);

		assertThat(found.getName()).isEqualTo(name);
	}

	@Test
	public void whenInvalidName_thenWorkerShouldNotBeFound() {
		Worker fromDb = workerService.findByName("wrong_name");
		assertThat(fromDb).isNull();

		verifyFindByNameIsCalledOnce("wrong_name");
	}

	@Test
	public void whenValidName_thenWorkerShouldExist() {
		boolean doesWorkerExist = workerService.exists("john");
		assertThat(doesWorkerExist).isEqualTo(true);

		verifyFindByNameIsCalledOnce("john");
	}

	@Test
	public void whenNonExistingName_thenWorkerShouldNotExist() {
		boolean doesWorkerExist = workerService.exists("some_name");
		assertThat(doesWorkerExist).isEqualTo(false);

		verifyFindByNameIsCalledOnce("some_name");
	}

	@Test
	public void whenValidId_thenWorkerShouldBeFound() {
		Worker fromDb = workerService.findById(11L);
		assertThat(fromDb.getName()).isEqualTo("john");

		verifyFindByIdIsCalledOnce();
	}

	@Test
	public void whenInvalidId_thenWorkerShouldNotBeFound() {
		Worker fromDb = workerService.findById(-99L);
		verifyFindByIdIsCalledOnce();
		assertThat(fromDb).isNull();
	}

	@Test
	public void given3Workers_whengetAll_thenReturn3Records() {
		Worker alex = new Worker("alex");
		Worker john = new Worker("john");
		Worker bob = new Worker("bob");

		List<Worker> allWorkers = workerService.findAll();
		verifyFindAllWorkersIsCalledOnce();
		assertThat(allWorkers).hasSize(3).extracting(Worker::getName).contains(alex.getName(), john.getName(),
				bob.getName());
	}

	private void verifyFindByNameIsCalledOnce(String name) {
		Mockito.verify(workerRepository, VerificationModeFactory.times(1)).findByName(name);
		Mockito.reset(workerRepository);
	}

	private void verifyFindByIdIsCalledOnce() {
		Mockito.verify(workerRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.reset(workerRepository);
	}

	private void verifyFindAllWorkersIsCalledOnce() {
		Mockito.verify(workerRepository, VerificationModeFactory.times(1)).findAll();
		Mockito.reset(workerRepository);
	}
}
