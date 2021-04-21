package com.example.simpleaccounting.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Transaction {

	private @Id @GeneratedValue(strategy=GenerationType.AUTO) Long id;
	private String title;
	private double amount;
	private String date; // Change to date type later
	private String info;
	
	@ManyToOne
	@JsonIgnore
    @JoinColumn(name = "userid")
	private User user;
	
	@ManyToOne()
	@JoinColumn(name = "typeId", referencedColumnName="typeId")
	@JsonManagedReference
	private Type type;
	
	public Transaction() {}
	
	public Transaction(String title, double amount, String date, String info, Type type, User user) {
		super();
		this.title = title;
		this.amount = amount;
		this.date = date;
		this.info = info;
		this.type = type;
		this.user = user;
	}
	
	public Transaction(String title, double amount, String date, String info, Type type) {
		super();
		this.title = title;
		this.amount = amount;
		this.date = date;
		this.info = info;
		this.type = type;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", title=" + title + ", amount=" + amount + ", date=" + date + ", info=" + info
				+ ", user=" + user + ", type=" + type + "]";
	}
	
}
