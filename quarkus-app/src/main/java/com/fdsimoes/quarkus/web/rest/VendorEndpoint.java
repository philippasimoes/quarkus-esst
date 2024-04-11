package com.fdsimoes.quarkus.web.rest;

import com.fdsimoes.quarkus.data.entity.Vendor;
import com.fdsimoes.quarkus.service.VendorService;
import io.netty.util.internal.StringUtil;
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

  private final VendorService vendorService;

  public VendorEndpoint(VendorService vendorService) {

    this.vendorService = vendorService;
  }

  @GET
  public List<Vendor> getVendors(@RestQuery("email") String email) {
    if (StringUtil.isNullOrEmpty(email)) {
      return this.vendorService.getAllVendors();
    }

    return this.vendorService.getVendorByEmail(email);
  }

  @POST
  @ResponseStatus(201)
  public Vendor addVendor(Vendor vendor) {
    return this.vendorService.addVendor(vendor);
  }

  @GET
  @Path("/{id}")
  public Vendor getVendor(@RestPath("id") long id) {
    Vendor vendor = this.vendorService.getVendorById(id);

    if (vendor == null) {
      throw new NotFoundException();
    }

    return vendor;
  }

  @GET
  @Path("/name/{name}")
  public List<Vendor> getVendorByName(@RestPath("name") String name) {

    if (StringUtil.isNullOrEmpty(name)) {
      return this.vendorService.getAllVendors();
    }

    return this.vendorService.getVendorsByName(name);
  }

  @GET
  @Path("/name-email")
  public List<Vendor> getVendorByNameAndEmail(
      @QueryParam("name") String name, @QueryParam("email") String email) {

    if (StringUtil.isNullOrEmpty(name) && StringUtil.isNullOrEmpty(email)) {
      return this.vendorService.getAllVendors();
    } else {
      List<Vendor> vendors = new ArrayList<>();
      if (!StringUtil.isNullOrEmpty(name) && !StringUtil.isNullOrEmpty(email)) {
        return this.vendorService.getVendorsByNameAndEmail(name, email);
      } else if (!StringUtil.isNullOrEmpty(email)) {
        return this.vendorService.getVendorByEmail(email);
      } else {
        return this.vendorService.getVendorsByName(name);
      }
    }
  }

  @PUT
  @Path("/{id}")
  @ResponseStatus(204)
  public void updateVendor(@RestPath("id") long id, Vendor vendor) {
    if (id != vendor.getId()) {
      throw new BadRequestException();
    }
    this.vendorService.updateVendor(vendor);
  }

  @DELETE
  @Path("/{id}")
  @ResponseStatus(205)
  public void deleteVendor(@RestPath("id") long id) {

    this.vendorService.deleteVendor(id);
  }
}
