package com.technomori.hruser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.hibernate.TransientObjectException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.technomori.hruser.entities.Role;
import com.technomori.hruser.entities.User;
import com.technomori.hruser.repositories.UserRepository;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRoleUnitTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository userRepository;

	@Test
    public void whenFindByEmail_thenReturnUserWithRole() {
		Role guest = new Role("guest");
        User alex = new User("Alex", "alex@email.com", "1234");
        alex.addRole(guest);

        entityManager.persistAndFlush(guest);
        entityManager.persistAndFlush(alex);

        User found = userRepository.findByEmail(alex.getEmail());
        assertThat(found.getName()).isEqualTo(alex.getName());
        assertThat(found.getRoles()).containsExactly(guest);
    }

	@Test
    public void whenInvalidRole_thenThrowsException() {
		Role guest = new Role("guest");
        User alex = new User("Alex", "alex@email.com", "1234");
        alex.addRole(guest);

        assertThatExceptionOfType(IllegalStateException.class)
        	.isThrownBy(() -> entityManager.persistAndFlush(alex))
        	.withCauseExactlyInstanceOf(TransientObjectException.class);
    }

    @Test
    public void givenSetOfRoles_thenReturnAllRoles() {
        Role guest = new Role("guest");
        Role operator = new Role("operator");
        Role admin = new Role("admin");
        entityManager.persist(guest);
        entityManager.persist(operator);
        entityManager.persist(admin);
        entityManager.flush();

        User alex = new User("Alex", "alex@email.com", "1234");
        alex.addRole(guest);
        alex.addRole(operator);
        alex.addRole(admin);

        entityManager.persistAndFlush(alex);

        User found = userRepository.findByEmail(alex.getEmail());

        assertThat(found.getRoles()).hasSize(3).extracting(Role::getRoleName)
        	.containsOnly(guest.getRoleName(), operator.getRoleName(),
        			admin.getRoleName());
    }

    @Test
    public void givenDuplicatedRoles_thenReturnUniqueRoles() {
        Role guest = new Role("guest");
        Role operator = new Role("operator");
        Role admin = new Role("admin");
        entityManager.persist(guest);
        entityManager.persist(operator);
        entityManager.persist(admin);
        entityManager.flush();

        User alex = new User("Alex", "alex@email.com", "1234");
        alex.addRole(guest);
        alex.addRole(operator);
        alex.addRole(admin);
        alex.addRole(guest);
        
        entityManager.persistAndFlush(alex);

        User found = userRepository.findByEmail(alex.getEmail());

        assertThat(found.getRoles()).hasSize(3).extracting(Role::getRoleName)
        	.containsOnly(guest.getRoleName(), operator.getRoleName(),
        			admin.getRoleName());
    }

}
