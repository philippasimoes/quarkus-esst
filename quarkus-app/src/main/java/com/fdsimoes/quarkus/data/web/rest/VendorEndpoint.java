package com.fdsimoes.quarkus.data.web.rest;

import com.fdsimoes.quarkus.data.entity.Vendor;
import com.fdsimoes.quarkus.data.repository.VendorRepository;
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
import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.QueryParam;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/rest/vendors")
@Produces("application/json")
@Consumes("application/json")
public class VendorEndpoint {

  private final VendorRepository vendorRepository;

  public VendorEndpoint(VendorRepository vendorRepository) {

    this.vendorRepository = vendorRepository;
  }

  @GET
  public List<Vendor> getVendors(@RestQuery("email") String email) {
    if (StringUtil.isNullOrEmpty(email)) {
      return this.vendorRepository.listAll();
    }

    List<Vendor> vendors = new ArrayList<>();
    Vendor vendor = this.vendorRepository.findByEmail(email);
    vendors.add(vendor);
    return vendors;
  }

  @POST
  @ResponseStatus(201)
  @Transactional
  public Vendor addVendor(Vendor vendor) {
    this.vendorRepository.persist(vendor);
    return vendor;
  }

  @GET
  @Path("/{id}")
  public Vendor getVendor(@RestPath("id") long id) {
    Vendor vendor = this.vendorRepository.findById(id);

    if (vendor == null) {
      throw new NotFoundException();
    }

    return vendor;
  }

  @GET
  @Path("/name/{name}")
  public List<Vendor> getVendorByName(@RestPath("name") String name) {

    if (StringUtil.isNullOrEmpty(name)) {
      return this.vendorRepository.listAll();
    }

    List<Vendor> vendors = new ArrayList<>();
    Vendor vendor = this.vendorRepository.findByName(name);
    vendors.add(vendor);
    return vendors;
  }

  @GET
  @Path("/name-email")
  public List<Vendor> getVendorByNameAndEmail(
      @QueryParam("name") String name, @QueryParam("email") String email) {

    if (StringUtil.isNullOrEmpty(name) && StringUtil.isNullOrEmpty(email)) {
      return this.vendorRepository.listAll();
    } else {
      List<Vendor> vendors = new ArrayList<>();
      if (!StringUtil.isNullOrEmpty(name) && !StringUtil.isNullOrEmpty(email)) {
        Vendor vendor = this.vendorRepository.findByNameAndEmail(name, email);
        vendors.add(vendor);
      } else if (!StringUtil.isNullOrEmpty(email)) {
        Vendor vendor = this.vendorRepository.findByEmail(email);
        vendors.add(vendor);
      } else {
        Vendor vendor = this.vendorRepository.findByName(name);
        vendors.add(vendor);
      }
      return vendors;
    }
  }

  @PUT
  @Path("/{id}")
  @Transactional
  @ResponseStatus(204)
  public void updateVendor(@RestPath("id") long id, Vendor vendor) {
    if (id != vendor.getId()) {
      throw new BadRequestException();
    }
    Vendor vendorEntity = this.vendorRepository.findById(id);
    if (vendorEntity == null) {
      throw new NotFoundException();
    }

    vendorEntity.setAddress(vendor.getAddress());
    vendorEntity.setEmail(vendor.getEmail());
    vendorEntity.setName(vendor.getName());
    vendorEntity.setPhone(vendor.getPhone());
    this.vendorRepository.persist(vendorEntity);
  }

  @DELETE
  @Path("/{id}")
  @Transactional
  @ResponseStatus(205)
  public void deleteVendor(@RestPath("id") long id) {
    this.vendorRepository.deleteById(id);
  }
}
