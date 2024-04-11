package com.fdsimoes.quarkus.data.repository;

import com.fdsimoes.quarkus.data.entity.Service;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ServiceRepository implements PanacheRepository<Service> {}
