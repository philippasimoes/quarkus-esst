package com.fdsimoes.quarkus.web.rest;

import com.fdsimoes.quarkus.data.entity.Customer;
import com.fdsimoes.quarkus.service.CustomerService;
import io.netty.util.internal.StringUtil;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;

import java.util.List;

@Path("/rest/customers")
@Produces("application/json")
@Consumes("application/json")
public class CustomerEndpoint {

  private final CustomerService customerService;

  public CustomerEndpoint(CustomerService customerService) {

    this.customerService = customerService;
  }

  @GET
  public List<Customer> getCustomers(@RestQuery("email") String email) {
    if (StringUtil.isNullOrEmpty(email)) {
      return this.customerService.getAllCustomers();
    } else {
      return this.customerService.getCustomersByEmail(email);
    }
  }

  @POST
  @ResponseStatus(201)
  public Customer addCustomer(Customer customer) {
    return this.customerService.addCustomer(customer);
  }

  @GET
  @Path("/{id}")
  public Customer getCustomer(@RestPath("id") long id) {
    return this.customerService.getCustomer(id);
  }

  @PUT
  @Path("/{id}")
  @ResponseStatus(204)
  public void updateCustomer(@RestPath("id") long id, Customer customer) {
    if (id != customer.getId()) throw new BadRequestException();
    this.customerService.updateCustomer(customer);
  }

  @DELETE
  @Path("/{id}")
  @Transactional
  @ResponseStatus(205)
  public void deleteCustomer(@RestPath("id") long id) {
    this.customerService.deleteCustomer(id);
  }
}
