package com.fdsimoes.quarkus.service;

import com.fdsimoes.quarkus.data.entity.Customer;
import com.fdsimoes.quarkus.data.repository.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CustomerService {
  private final CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {

    this.customerRepository = customerRepository;
  }

  public List<Customer> getAllCustomers() {
    return this.customerRepository.listAll();
  }

  public List<Customer> getCustomersByEmail(String email) {
    List<Customer> customers = new ArrayList<>();
    Customer customer = this.customerRepository.findByEmail(email);
    customers.add(customer);
    return customers;
  }

  public Customer getCustomer(long id) {
    Customer customer = this.customerRepository.findById(id);

    if (customer == null) {
      throw new NotFoundException();
    }

    return customer;
  }

  @Transactional
  public Customer addCustomer(Customer customer) {
    this.customerRepository.persist(customer);
    return customer;
  }

  @Transactional
  public Customer updateCustomer(Customer customer) {
    Customer customerEntity = this.customerRepository.findById(customer.getId());
    if (customerEntity == null) {
      throw new NotFoundException();
    }

    customerEntity.setAddress(customer.getAddress());
    customerEntity.setEmail(customer.getEmail());
    customerEntity.setFirstName(customer.getFirstName());
    customerEntity.setLastName(customer.getLastName());
    customerEntity.setPhone(customer.getPhone());
    this.customerRepository.persist(customerEntity);

    return customerEntity;
  }

  @Transactional
  public void deleteCustomer(long id) {
    this.customerRepository.deleteById(id);
  }
}
