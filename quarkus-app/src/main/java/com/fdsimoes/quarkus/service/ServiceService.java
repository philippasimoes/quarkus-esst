package com.fdsimoes.quarkus.service;

import com.fdsimoes.quarkus.data.entity.Service;
import com.fdsimoes.quarkus.data.repository.ServiceRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.jboss.resteasy.reactive.RestPath;

import java.util.List;

@ApplicationScoped
public class ServiceService {

  private final ServiceRepository serviceRepository;

  public ServiceService(ServiceRepository serviceRepository) {

    this.serviceRepository = serviceRepository;
  }

  public List<Service> getAllServices() {
    return this.serviceRepository.listAll();
  }

  @Transactional
  public Service addService(Service service) {
    this.serviceRepository.persist(service);
    return service;
  }

  public Service getService(long id) {
    Service service = this.serviceRepository.findById(id);
    if (service == null) {
      throw new NotFoundException();
    }
    return service;
  }

  @Transactional
  public Service updateService(Service service) {
    Service serviceEntity = this.serviceRepository.findById(service.getId());
    if (serviceEntity == null) {
      throw new NotFoundException();
    }
    serviceEntity.setName(service.getName());
    this.serviceRepository.persist(serviceEntity);
    return serviceEntity;
  }

  @Transactional
  public void deleteService(@RestPath("id") long id) {
    this.serviceRepository.deleteById(id);
  }
}
