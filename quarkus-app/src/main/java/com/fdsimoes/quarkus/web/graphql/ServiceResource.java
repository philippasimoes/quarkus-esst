package com.fdsimoes.quarkus.web.graphql;

import com.fdsimoes.quarkus.data.entity.Service;
import com.fdsimoes.quarkus.service.ServiceService;
import com.fdsimoes.quarkus.web.graphql.input.ServiceInput;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;

@GraphQLApi
public class ServiceResource {
  private final ServiceService serviceService;

  public ServiceResource(ServiceService serviceService) {

    this.serviceService = serviceService;
  }

  @Query("allServices")
  @Description("Gets all services available in the system")
  public List<Service> getAllServices() {
    return this.serviceService.getAllServices();
  }

  @Mutation("addService")
  @Description("Adds a service to the system")
  public Service addService(ServiceInput serviceInput) {
    return this.serviceService.addService(serviceInput.getEntity());
  }

  @Query("serviceById")
  @Description("Gets a service by ID")
  public Service getService(@Name("id") long id) {
    return this.serviceService.getService(id);
  }

  @Mutation("updateService")
  @Description("Updates a service")
  public Service updateService(Service service) {

    return this.serviceService.updateService(service);
  }

  @Mutation("deleteService")
  @Description("Deletes a service")
  public Service deleteService(@Name("id") long id) {
    Service service = this.serviceService.getService(id);

    this.serviceService.deleteService(id);
    return service;
  }
}
