package com.example.customer_service.services;


import com.example.customer_service.model.Customer;
import com.example.customer_service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  // Récupérer tous les clients
  public List<Customer> getAllCustomers() {
    return customerRepository.findAll();
  }

  // Récupérer un client par ID
  public Optional<Customer> getCustomerById(Long id) {
    return customerRepository.findById(id);
  }

  // Créer un nouveau client
  public Customer createCustomer(Customer customer) {
    return customerRepository.save(customer);
  }

  // Supprimer un client
  public void deleteCustomer(Long id) {
    customerRepository.deleteById(id);
  }
}