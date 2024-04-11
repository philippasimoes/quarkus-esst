package com.fdsimoes.quarkus.web.graphql;

import com.fdsimoes.quarkus.data.entity.Vendor;
import com.fdsimoes.quarkus.service.VendorService;
import com.fdsimoes.quarkus.web.graphql.input.VendorInput;
import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
public class VendorResource {

  private final VendorService vendorService;

  public VendorResource(VendorService vendorService) {

    this.vendorService = vendorService;
  }

  @Query("allVendors")
  @Description("Gets all vendors")
  public List<Vendor> getAllVendors() {
    return this.vendorService.getAllVendors();
  }

  @Query("vendorById")
  @Description("Gets a vendor by ID")
  public Vendor getVendorById(@Name("id") long id) {
    return this.vendorService.getVendorById(id);
  }

  @Query("vendorsByName")
  @Description("Gets all vendors with the same name")
  public List<Vendor> getVendorsByName(@Name("name") String name) {
    return this.vendorService.getVendorsByName(name);
  }

  @Query("vendorsByNameAndEmail")
  @Description("Gets all vendors with the same name and email")
  public List<Vendor> getVendorsByNameAndEmail(
      @Name("name") String name, @Name("email") String email) {
    return this.vendorService.getVendorsByNameAndEmail(name, email);
  }

  @Mutation("addVendor")
  @Description("Adds a vendor to the system")
  public Vendor addVendor(VendorInput vendorInput) {
    return this.vendorService.addVendor(vendorInput.getEntity());
  }

  @Mutation("updateVendor")
  @Description("Updates a vendor")
  public Vendor updateVendor(Vendor vendor) {

    return this.vendorService.updateVendor(vendor);
  }

  @Mutation("deleteVendor")
  @Description("Deletes a service")
  public Vendor deleteVendor(@Name("id") long id) {
    Vendor vendor = this.vendorService.getVendorById(id);

    this.vendorService.deleteVendor(id);
    return vendor;
  }
}
