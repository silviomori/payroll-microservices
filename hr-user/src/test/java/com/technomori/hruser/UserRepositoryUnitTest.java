package com.technomori.hruser;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.technomori.hruser.entities.User;
import com.technomori.hruser.repositories.UserRepository;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryUnitTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
    public void whenFindByEmail_thenReturnUser() {
        User alex = new User();
        alex.setName("alex");
        alex.setEmail("alex@email.com");
        alex.setPassword("1234");
        entityManager.persistAndFlush(alex);

        User found = userRepository.findByEmail(alex.getEmail());
        assertThat(found.getName()).isEqualTo(alex.getName());
        assertThat(found.getEmail()).isEqualTo(alex.getEmail());
        assertThat(found.getPassword()).isEqualTo(alex.getPassword());
    }
	
	@Test
    public void whenInvalidEmail_thenReturnNull() {
        User fromDb = userRepository.findByEmail("doesNotExist");
        assertThat(fromDb).isNull();
    }

    @Test
    public void whenFindById_thenReturnUser() {
        User user = new User();
        user.setName("test");
        user = entityManager.persistAndFlush(user);

        User fromDb = userRepository.findById(user.getId()).orElse(null);
        assertThat(fromDb.getName()).isEqualTo(user.getName());
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        User fromDb = userRepository.findById(-11l).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfUsers_whenFindAll_thenReturnAllUsers() {
        User alex = new User();
        alex.setName("alex");
        User ron = new User();
        ron.setName("ron");
        User bob = new User();
        bob.setName("bob");

        entityManager.persist(alex);
        entityManager.persist(ron);
        entityManager.persist(bob);
        entityManager.flush();

        List<User> allWorkers = userRepository.findAll();

        assertThat(allWorkers).hasSize(3).extracting(User::getName)
        	.containsOnly(alex.getName(), ron.getName(), bob.getName());
    }
}
