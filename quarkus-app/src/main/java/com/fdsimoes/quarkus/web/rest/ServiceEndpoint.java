package com.fdsimoes.quarkus.web.rest;

import com.fdsimoes.quarkus.data.entity.Service;
import com.fdsimoes.quarkus.service.ServiceService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestPath;

@Path("/rest/services")
@Produces("application/json")
@Consumes("application/json")
public class ServiceEndpoint {
  private final ServiceService serviceService;

  public ServiceEndpoint(ServiceService serviceService) {

    this.serviceService = serviceService;
  }

  @POST
  @ResponseStatus(201)
  public Service addService(Service service) {
    return this.serviceService.addService(service);
  }

  @GET
  @Path("/{id}")
  public Service getService(@RestPath("id") long id) {
    Service service = this.serviceService.getService(id);

    if (service == null) {
      throw new NotFoundException();
    }

    return service;
  }

  @PUT
  @Path("/{id}")
  @ResponseStatus(204)
  public void updateService(@RestPath("id") long id, Service service) {
    if (id != service.getId()) throw new WebApplicationException(400);
    this.serviceService.updateService(service);
  }

  @DELETE
  @Path("/{id}")
  @ResponseStatus(205)
  public void deleteService(@RestPath("id") long id) {
    this.serviceService.deleteService(id);
  }
}
