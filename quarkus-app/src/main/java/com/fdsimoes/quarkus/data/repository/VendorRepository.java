package com.fdsimoes.quarkus.data.repository;

import com.fdsimoes.quarkus.data.entity.Vendor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VendorRepository implements PanacheRepository<Vendor> {

  public Vendor findByEmail(String email) {
    return find("email", email).firstResult();
  }

  public Vendor findByName(String name) {
    return find("lower(name)", name.toLowerCase()).firstResult();
  }

  public Vendor findByNameAndEmail(String name, String email) {
    return find(
            "lower(name) = :name and email = :email",
            Parameters.with("name", name.toLowerCase()).and("email", email))
        .firstResult();
  }
}
