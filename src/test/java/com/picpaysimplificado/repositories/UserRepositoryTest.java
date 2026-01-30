package com.picpaysimplificado.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dtos.UserDTO;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EntityManager entityManager;

	@Test
	@DisplayName("Should get User successfully from DB")
	void findUserByDocumentCase1() {
		String document = "99999999901";
		UserDTO data = new UserDTO("Fernanda", "Teste", document, new BigDecimal(10), "teste@gmail.com", "44444", UserType.COMMON);
		this.createUser(data);
		Optional<User> result = this.userRepository.findUserByDocument(document);
		assertThat(result.isPresent()).isTrue();
	}

	@Test
	@DisplayName("Should not get User from DB when user not exists")
	void findUserByDocumentCase2() {
		String document = "99999999901";
		Optional<User> result = this.userRepository.findUserByDocument(document);
		assertThat(result.isEmpty()).isTrue();
	}

	private User createUser(UserDTO data) {
		User newUser = new User(data);
		this.entityManager.persist(newUser);
		return newUser;
	}
}