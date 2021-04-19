package com.example.simpleaccounting.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Type {

	private @Id @GeneratedValue(strategy=GenerationType.AUTO) Long typeId;
	private String name;
	private double sumIncome;
	private double sumExpense;
	
	@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "type")
	private List<Transaction> transactions;
	
	public Type() {}
	
	public Type(String name) {
		super();
		this.name = name;
	}
		

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public double getSumIncome() {
		return sumIncome;
	}

	public void setSumIncome() {
		double sum = 0;
		if(this.name == "income") {
		for(int i = 0; i < transactions.size(); i++) {
			sum += transactions.get(i).getAmount();
		}
		}
		sumIncome = sum;
	}

	public double getSumExpense() {
		return sumExpense;
	}

	public void setSumExpense() {
		
	}

	
	
	
	
}
