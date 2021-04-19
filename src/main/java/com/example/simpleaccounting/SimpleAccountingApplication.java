package com.example.simpleaccounting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.simpleaccounting.domain.Transaction;
import com.example.simpleaccounting.domain.TransactionRepository;
import com.example.simpleaccounting.domain.Type;
import com.example.simpleaccounting.domain.TypeRepository;
import com.example.simpleaccounting.domain.User;
import com.example.simpleaccounting.domain.UserRepository;


@SpringBootApplication
public class SimpleAccountingApplication {

	private static final Logger log = LoggerFactory.getLogger(SimpleAccountingApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SimpleAccountingApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner transactionDemo(TransactionRepository traRepo, TypeRepository typeRepo,
			UserRepository userRepo) {
		return (args) -> {
			
			//for testing
			log.info("adding few transaction examples");
			//Creating users admin/admin & user/user with roles
			User user1 = new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
			User user2 = new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN");
			userRepo.save(user1);
			userRepo.save(user2);
			//adding few types 
			typeRepo.save(new Type("income"));
			typeRepo.save(new Type("expense"));
			// Attributes: String title, double amount, String date, String info, Type type
			traRepo.save(new Transaction("Puhelin", 9.90, "5.4.2021",
					"uusi puhelin", typeRepo.findByName("expense").get(0), user1));
			traRepo.save(new Transaction("Puhelin", 9.90, "5.4.2021",
					"uusi puhelin", typeRepo.findByName("expense").get(0), user2));
			traRepo.save(new Transaction("YEL", 250, "1.4.2021",
					"YEL-maksu", typeRepo.findByName("expense").get(0), user1));
			traRepo.save(new Transaction("YEL", 250, "1.4.2021",
					"YEL-maksu", typeRepo.findByName("expense").get(0), user2));
			traRepo.save(new Transaction("lasku", 2500, "29.3.2021",
					"Firma keikka", typeRepo.findByName("income").get(0), user2));
			traRepo.save(new Transaction("lasku", 3500, "2.4.2021",
					"Kuukausittainen laskutus", typeRepo.findByName("income").get(0), user2));
			traRepo.save(new Transaction("Puhelin", 9.90, "5.4.2021",
					"uusi puhelin", typeRepo.findByName("income").get(0), user1));
			
			
			// list all transactions to log
			log.info("fetch all transactions");
			for (Transaction transaction : traRepo.findAll()) {
				log.info(transaction.toString());
			}
			
			//empty repositories
			userRepo.deleteAll();
			traRepo.deleteAll();
			typeRepo.deleteAll();
					
		};
	}

}
