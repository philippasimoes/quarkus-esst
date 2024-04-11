package com.fdsimoes.quarkus.service;

import com.fdsimoes.quarkus.data.entity.Vendor;
import com.fdsimoes.quarkus.data.repository.VendorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class VendorService {
  private final VendorRepository vendorRepository;

  public VendorService(VendorRepository vendorRepository) {

    this.vendorRepository = vendorRepository;
  }

  public Vendor getVendorById(long id) {
    Vendor vendor = this.vendorRepository.findById(id);

    if (vendor == null) {
      throw new NotFoundException();
    }

    return vendor;
  }

  public List<Vendor> getAllVendors() {
    return this.vendorRepository.listAll();
  }

  public List<Vendor> getVendorByEmail(String email) {
    List<Vendor> vendors = new ArrayList<>();
    Vendor vendor = this.vendorRepository.findByEmail(email);
    vendors.add(vendor);
    return vendors;
  }

  public List<Vendor> getVendorsByName(String name) {
    Vendor vendor = this.vendorRepository.findByName(name);
    List<Vendor> vendors = new ArrayList<>();
    vendors.add(vendor);
    return vendors;
  }

  public List<Vendor> getVendorsByNameAndEmail(String name, String email) {

    List<Vendor> vendors = new ArrayList<>();

    Vendor vendor = this.vendorRepository.findByNameAndEmail(name, email);
    vendors.add(vendor);
    return vendors;
  }

  @Transactional
  public Vendor addVendor(Vendor vendor) {
    this.vendorRepository.persist(vendor);
    return vendor;
  }

  @Transactional
  public Vendor updateVendor(Vendor vendor) {

    Vendor vendorEntity = this.vendorRepository.findById(vendor.getId());
    if (vendorEntity == null) {
      throw new NotFoundException();
    }

    vendorEntity.setAddress(vendor.getAddress());
    vendorEntity.setEmail(vendor.getEmail());
    vendorEntity.setName(vendor.getName());
    vendorEntity.setPhone(vendor.getPhone());
    this.vendorRepository.persist(vendorEntity);
    return vendor;
  }

  @Transactional
  public void deleteVendor(long id) {
    this.vendorRepository.deleteById(id);
  }
}
