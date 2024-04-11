package com.fdsimoes.quarkus.data.web.rest;

import com.fdsimoes.quarkus.data.entity.Customer;
import com.fdsimoes.quarkus.data.repository.CustomerRepository;
import io.netty.util.internal.StringUtil;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Path("/rest/customers")
@Produces("application/json")
@Consumes("application/json")
public class CustomerEndpoint {
  private final CustomerRepository customerRepository;

  public CustomerEndpoint(CustomerRepository customerRepository) {

    this.customerRepository = customerRepository;
  }

  @GET
  public List<Customer> getCustomers(@RestQuery("email") String email) {
    if (StringUtil.isNullOrEmpty(email)) {
      return this.customerRepository.listAll();
    }

    List<Customer> customers = new ArrayList<>();
    Customer customer = this.customerRepository.findByEmail(email);
    customers.add(customer);
    return customers;
  }

  @POST
  @ResponseStatus(201)
  @Transactional
  public Customer addCustomer(Customer customer) {
    this.customerRepository.persist(customer);
    return customer;
  }

  @GET
  @Path("/{id}")
  public Customer getCustomer(@RestPath("id") long id) {
    Customer customer = this.customerRepository.findById(id);

    if (customer == null) {
      throw new NotFoundException();
    }

    return customer;
  }

  @PUT
  @Path("/{id}")
  @Transactional
  @ResponseStatus(204)
  public void updateCustomer(@RestPath("id") long id, Customer customer) {
    if (id != customer.getId()) {
      throw new BadRequestException();
    }
    Customer customerEntity = this.customerRepository.findById(id);
    if (customerEntity == null) {
      throw new NotFoundException();
    }

    customerEntity.setAddress(customer.getAddress());
    customerEntity.setEmail(customer.getEmail());
    customerEntity.setFirstName(customer.getFirstName());
    customerEntity.setLastName(customer.getLastName());
    customerEntity.setPhone(customer.getPhone());
    this.customerRepository.persist(customerEntity);
  }

  @DELETE
  @Path("/{id}")
  @Transactional
  @ResponseStatus(205)
  public void deleteCustomer(@RestPath("id") long id) {
    this.customerRepository.deleteById(id);
  }
}
