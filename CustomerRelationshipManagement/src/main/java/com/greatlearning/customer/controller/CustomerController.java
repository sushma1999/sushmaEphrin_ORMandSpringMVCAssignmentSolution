package com.greatlearning.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greatlearning.customer.model.Customer;
import com.greatlearning.customer.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	// add mapping for list of customers using "/list"
	@RequestMapping("/list")
	public String listCustomers(Model theModel) {

		// get list of students from database
		List<Customer> theCustomers = customerService.findAll();

		// add to the spring model
		theModel.addAttribute("Customers", theCustomers);
		return "list-customers";
	}

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		Customer thecustomer = new Customer();

		// create model attribute to bind from data
		theModel.addAttribute("Customer", thecustomer);
		return "customer-form";
	}

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int id, Model theModel) {

		// getting the customer from the service by id
		Customer thecustomer = customerService.findById(id);

		// set customers as an attribute to pre-populate the form
		theModel.addAttribute("Customer", thecustomer);

		// send over to our form
		return "customer-form";
	}

	@PostMapping("/save")
	public String saveCustomer(@RequestParam("id") int id, @RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("email") String email) {
		System.out.println(id);
		Customer thecustomer;
		if (id != 0) {
			thecustomer = customerService.findById(id);
			thecustomer.setFirstName(firstName);
			thecustomer.setLastName(lastName);
			thecustomer.setEmail(email);
		} else
			thecustomer = new Customer(firstName, lastName, email);

		// save the customer details
		customerService.save(thecustomer);

		// using a redirect to prevent duplicate submissions
		return "redirect:/customers/list";
	}

	@RequestMapping("/delete")
	public String delete(@RequestParam("customerId") int id) {

		// delete the customer along with it's details
		customerService.deleteById(id);

		// redirect to /customers/list --> main page
		return "redirect:/customers/list";
	}
}
