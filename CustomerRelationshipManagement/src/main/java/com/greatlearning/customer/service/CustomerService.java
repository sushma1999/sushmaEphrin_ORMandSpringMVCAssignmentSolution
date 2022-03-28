package com.greatlearning.customer.service;

import java.util.List;

import com.greatlearning.customer.model.Customer;

public interface CustomerService {

	public List<Customer> findAll();

	public Customer findById(int id);

	public void save(Customer thecustomer);

	public void deleteById(int id);

}