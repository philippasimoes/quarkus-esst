package com.fdsimoes.quarkus.web.graphql;

import com.fdsimoes.quarkus.data.entity.Customer;
import com.fdsimoes.quarkus.service.CustomerService;
import com.fdsimoes.quarkus.web.graphql.input.CustomerInput;
import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
public class CustomerResource {
  private final CustomerService customerService;

  public CustomerResource(CustomerService customerService) {

    this.customerService = customerService;
  }

  @Query("allCustomers")
  @Description("Gets all customers available in the system")
  public List<Customer> getAllCustomers() {
    return this.customerService.getAllCustomers();
  }

  @Query("getCustomersByEmail")
  @Description("Gets all customers that have a determined email in the system")
  public List<Customer> getAllCustomersByEmail(@Name("email") String email) {
    return this.customerService.getCustomersByEmail(email);
  }

  @Mutation("addCustomer")
  @Description("Adds a customer to the system")
  public Customer addCustomer(CustomerInput customerInput) {
    return this.customerService.addCustomer(customerInput.getEntity());
  }

  @Query("getCustomer")
  @Description("Gets a customer by ID")
  public Customer getCustomer(@Name("id") long id) {
    return this.customerService.getCustomer(id);
  }

  @Mutation("updateCustomer")
  @Description("Updates a customer")
  public Customer updateCustomer(Customer customer) {

    return this.customerService.updateCustomer(customer);
  }

  @Mutation("deleteCustomer")
  @Description("Deletes a customer")
  public Customer deleteCustomer(@Name("id") long id) {
    Customer customer = this.customerService.getCustomer(id);

    this.customerService.deleteCustomer(id);
    return customer;
  }
}
