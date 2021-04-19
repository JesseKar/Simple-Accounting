package com.example.simpleaccounting.web;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.simpleaccounting.domain.Transaction;
import com.example.simpleaccounting.domain.TransactionRepository;
import com.example.simpleaccounting.domain.Type;
import com.example.simpleaccounting.domain.TypeRepository;
import com.example.simpleaccounting.domain.User;
import com.example.simpleaccounting.domain.UserRepository;

@Controller
public class MainController {

	@Autowired
	private TransactionRepository traRepo;	
	
	@Autowired
	private TypeRepository typeRepo;
	
	@Autowired
	private UserRepository userRepo;
	
		
	//login page
	@RequestMapping(value="/login")
    public String login() {	
        return "login";
    }	
	
	// show all transaction, both expenses and incomes
	@RequestMapping(value = {"/", "/transactions"})
	public String listAllTransactions(Model model) {
		// fetch one user and transactions for that user
		
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String username = user.getUsername();
		User currentUser = userRepo.findByUsername(username);
		model.addAttribute("transactions", traRepo.findByUser(currentUser));
		
		List<Transaction> traList = traRepo.findByUser(currentUser);
		double sum = 0;
		for(int i = 0; i < traList.size(); i++) {
			if(traList.get(i).getType().getName() == "income") {
				sum += traList.get(i).getAmount();
			}
			if(traList.get(i).getType().getName() == "expense") {
				sum -= traList.get(i).getAmount();
			}
		}
					
		model.addAttribute("sum", sum);
		
		return "transactions";
	}
	
	// show all incomes
		@RequestMapping(value = "/incomes")
		public String listAllIncomes(Model model) {
			// fetch one user
			UserDetails user = (UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			String username = user.getUsername();
			User currentUser = userRepo.findByUsername(username);
			List<Transaction> traList = traRepo.findByUser(currentUser);
			//Separate incomes from all transactions
			List<Transaction> incomes = new ArrayList<>();
			for(int i = 0; i < traList.size(); i++) {
				if(traList.get(i).getType().getName() == "income") {
					incomes.add(traList.get(i));
				}
			}
			
			model.addAttribute("incomes", incomes);
			
			// Sum all incomes
			double sum = 0;
			for(int i = 0; i < traList.size(); i++) {
				if(traList.get(i).getType().getName() == "income") {
					sum += traList.get(i).getAmount();
				}
			}
						
			model.addAttribute("sum", sum);
			return "incomes";
		}
	
	// show all expenses
		@RequestMapping(value = "/expenses")
		public String listAllExpenses(Model model) {
			// fetch one user
			UserDetails user = (UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			String username = user.getUsername();
			User currentUser = userRepo.findByUsername(username);
			List<Transaction> traList = traRepo.findByUser(currentUser);
			//Separate expenses from all transactions
			List<Transaction> expenses = new ArrayList<>();
			for(int i = 0; i < traList.size(); i++) {
				if(traList.get(i).getType().getName() == "expense") {
					expenses.add(traList.get(i));
				}
			}
			
			model.addAttribute("expenses", expenses);
			
			// sum all expenses
			double sum = 0;
			for(int i = 0; i < traList.size(); i++) {
				if(traList.get(i).getType().getName() == "expense") {
					sum += traList.get(i).getAmount();
				}
			}
						
			model.addAttribute("sum", sum);
			return "expenses";
		}
	
	// add transaction SAAKO TÄHÄN JOTENKIN ET VALMIINA INCOME TAI EXPENSE SIVULTA TULLESSA??!??
		@RequestMapping(value = "/add")
		public String addTransaction(Model model) {
			model.addAttribute("transaction", new Transaction());
			model.addAttribute("types", typeRepo.findAll());
			
			return "addtransaction";
		}
		
	//edit transaction 
		@RequestMapping(value = "/edit/{id}")
		public String editTransaction(@PathVariable("id") Long transactionId, Model model) {
			model.addAttribute("transaction", traRepo.findById(transactionId));
			model.addAttribute("types", typeRepo.findAll());
			return "edittransaction";
		}
		
	// save transaction
		@PostMapping("/save")
		public String save(Transaction transaction) {
			
			UserDetails user = (UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			String username = user.getUsername();
			User currentUser = userRepo.findByUsername(username);
			transaction.setUser(currentUser);
			// ohjaus tyypin mukaan income tai expenses sivulle???
			traRepo.save(transaction);
			if(transaction.getType().getName() == "income") {
				return "redirect:incomes";
			} 
			else if (transaction.getType().getName() == "expense") {
				return "redirect:expenses";
			}
			else {
				return "redirect:transactions";
			}
		}
		
	//delete transaction
		@GetMapping("/delete/{id}")
		public String deleteTransaction(@PathVariable("id") Long transactionId, Model model) {
			traRepo.deleteById(transactionId);
			// ohjaus tyypin mukaan income tai expenses sivulle???
			return "redirect:../transactions";
		}
		
		//RESTful show all transactions
		@GetMapping("/rest")
		public @ResponseBody List<Transaction> transactionsListRest() {
			return (List<Transaction>) traRepo.findAll();
		}
		//RESTful to get a transaction by id
		@GetMapping("/rest/{id}")
		public @ResponseBody Optional<Transaction> findTranscationRest(@PathVariable("id") Long transactionId) {
			return traRepo.findById(transactionId);
		}
		
		
		
		
		
		
}









