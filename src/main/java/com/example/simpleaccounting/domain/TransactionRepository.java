package com.example.simpleaccounting.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface TransactionRepository extends CrudRepository<Transaction, Long> {
	//Find transactions by their type (expense / income)
	List<Transaction> findByType(Type type);
	
	//Find all transactions to list
	List<Transaction> findAll();
	
	List<Transaction> findByTitle(@Param("title") String title);
	
	//find transactions by user for user unique lists
	List<Transaction> findByUser(User user);
	
}
