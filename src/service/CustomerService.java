package service;

import model.Customer;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

public class CustomerService {

	private static final CustomerService customerService = new CustomerService();

	private final Map<String, Customer> customers = new HashMap<>();

	private CustomerService() {
	}

	public static CustomerService getInstance() {
		return customerService;
	}

	/**
	 * To add a customer
	 * 
	 * @param email
	 * @param firstName
	 * @param lastName
	 */
	public void addCustomer(String email, String firstName, String lastName) {
		Customer customer = new Customer(email, firstName, lastName);
		customers.put(email, customer);
	}

	/**
	 * Retrieve a customer by email
	 * 
	 * @param customerEmail
	 * @return
	 */
	public Customer getCustomer(String customerEmail) {
		return customers.get(customerEmail);
	}

	/**
	 * Retrieves all customers
	 * 
	 * @return
	 */
	public Collection<Customer> getAllCustomers() {
		return customers.values();
	}
}
