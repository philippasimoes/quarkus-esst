package com.fdsimoes.quarkus.data.web.rest;

import com.fdsimoes.quarkus.data.entity.Service;
import com.fdsimoes.quarkus.data.repository.ServiceRepository;
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

@Path("/rest/services")
@Produces("application/json")
@Consumes("application/json")
public class ServiceEndpoint {
    private final ServiceRepository serviceRepository;

    public ServiceEndpoint(ServiceRepository serviceRepository) {

        this.serviceRepository = serviceRepository;
    }

    @POST
    @ResponseStatus(201)
    @Transactional
    public Service addService(Service service) {
        this.serviceRepository.persist(service);
        return service;
    }

    @GET
    @Path("/{id}")
    public Service getService(@RestPath("id") long id) {
        Service service = this.serviceRepository.findById(id);

        if (service == null) {
            throw new NotFoundException();
        }

        return service;
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @ResponseStatus(204)
    public void updateService(@RestPath("id") long id, Service service) {
        if (id != service.getId()) {
            throw new BadRequestException();
        }
        Service serviceEntity = this.serviceRepository.findById(id);
        if (serviceEntity == null) {
            throw new NotFoundException();
        }
        serviceEntity.setName(service.getName());
        this.serviceRepository.persist(serviceEntity);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @ResponseStatus(205)
    public void deleteService(@RestPath("id") long id) {
        this.serviceRepository.deleteById(id);
    }
}
