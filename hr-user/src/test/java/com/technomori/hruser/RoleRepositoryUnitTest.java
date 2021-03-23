package com.technomori.hruser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.technomori.hruser.entities.Role;
import com.technomori.hruser.repositories.RoleRepository;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RoleRepositoryUnitTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Test
    public void whenFindById_thenReturnRole() {
        Role operator = new Role("operator");
        entityManager.persistAndFlush(operator);

        Role found = roleRepository.findById(operator.getId()).orElse(null);
        assertThat(found.getRoleName()).isEqualTo(operator.getRoleName());
    }
	
	@Test
    public void whenInvalidId_thenReturnNull() {
        Role fromDb = roleRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfRoles_whenFindAll_thenReturnAllRoles() {
        Role guest = new Role("guest");
        Role operator = new Role("operator");
        Role admin = new Role("admin");

        entityManager.persist(guest);
        entityManager.persist(operator);
        entityManager.persist(admin);
        entityManager.flush();

        List<Role> allWorkers = roleRepository.findAll();

        assertThat(allWorkers).hasSize(3).extracting(Role::getRoleName)
        	.containsOnly(guest.getRoleName(), operator.getRoleName(),
        			admin.getRoleName());
    }

    @Test
    public void givenUnnamedRole_thenThrowsException() {
        Role unnamed = new Role();

        assertThatExceptionOfType(PersistenceException.class)
	    	.isThrownBy(() -> entityManager.persistAndFlush(unnamed))
	    	.withCauseExactlyInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void givenDuplicatedRoles_thenThrowsException() {
        Role guestOne = new Role("guest");
        Role guestTwo = new Role("guest");

        entityManager.persistAndFlush(guestOne);

        assertThatExceptionOfType(PersistenceException.class)
	    	.isThrownBy(() -> entityManager.persistAndFlush(guestTwo))
	    	.withCauseExactlyInstanceOf(ConstraintViolationException.class);
    }
}
