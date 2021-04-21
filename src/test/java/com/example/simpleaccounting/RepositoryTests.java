package com.example.simpleaccounting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import com.example.simpleaccounting.domain.Transaction;
import com.example.simpleaccounting.domain.TransactionRepository;
import com.example.simpleaccounting.domain.Type;
import com.example.simpleaccounting.domain.TypeRepository;
import com.example.simpleaccounting.domain.User;
import com.example.simpleaccounting.domain.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RepositoryTests {

	@Autowired
	private TransactionRepository traRepo;
	@Autowired
	private TypeRepository typeRepo;
	@Autowired
	private UserRepository userRepo;

	// Transaction repository tests
	@Test
	public void createNewTransaction() {
		// Constructor attributes:
		// String title, double amount, String date, String info, Type type
		Transaction transaction = new Transaction("Testi", 10.10, "15.04.2021", "Laskutus", new Type("income"));
		traRepo.save(transaction);
		assertThat(transaction.getTitle()).isNotNull();
	}
	@Test
	public void findByTransactionsShouldReturnTransaction() {
		List<Transaction> transaction = traRepo.findByTitle("YEL");
		assertThat(transaction).hasSize(2);
		assertThat(transaction.get(0).getAmount()).isEqualTo(250);
	}

	// Type repository tests
	@Test
	public void createNewType() {
		// Constructor attributes: String name
		Type type = new Type("income");
		typeRepo.save(type);
		assertThat(type.getName()).isNotNull();
	}
	@Test
	public void findByTypeCheckIfHasTransactions() {
		List<Type> type = typeRepo.findByName("income");
		assertThat(type).hasSize(1);
		assertThat(type.get(0).getTransactions()).isNotNull();
	}

	// User repository tests
	@Test
	public void createNewUser() {
		// Constructors: String username, String passwordHash, String role
		User user = new User("test", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "user");
		userRepo.save(user);
		assertThat(user.getUsername()).isNotNull();
	}
	/* Commented becouse userRepo is emptied on commandLineRunner
	 @Test
	 public void findByUsername() {
	 User user = userRepo.findByUsername("user");
	 assertThat(user).isNotNull();
	 assertThat(user.getRole()).isEqualTo("USER");
	 }
	*/
	
	// DELETE TESTS FOR EVERY REPOSITORY
		 @Test
		 public void deleteTransactionTest() {
			 List<Transaction> transactions = traRepo.findByTitle("YEL");
			 assertThat(transactions).hasSize(2);
			 Long id = transactions.get(0).getId();
			 traRepo.deleteById(id);
		 }
		 @Test
		 public void deleteTypeTest() {
			 List<Type> types = typeRepo.findByName("income");
			 assertThat(types).hasSize(1);
			 Long id = types.get(0).getTypeId();
			 typeRepo.deleteById(id);
		 }
		 
		 /* Commented becouse userRepo is emptied on commandLineRunner
		 @Test 
		 public void deleteUserTest() {
			 User user = userRepo.findByUsername("user");
			 Long id = user.getId();
			 userRepo.deleteById(id);
		 }*/
}
