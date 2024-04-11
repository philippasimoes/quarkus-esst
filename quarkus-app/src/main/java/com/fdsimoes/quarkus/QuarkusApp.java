package com.fdsimoes.quarkus;

import com.fdsimoes.quarkus.data.entity.Customer;
import com.fdsimoes.quarkus.data.entity.Service;
import com.fdsimoes.quarkus.data.entity.Vendor;
import com.fdsimoes.quarkus.data.repository.CustomerRepository;
import com.fdsimoes.quarkus.data.repository.ServiceRepository;
import com.fdsimoes.quarkus.data.repository.VendorRepository;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.enterprise.context.control.ActivateRequestContext;
import java.util.List;

@QuarkusMain
public class QuarkusApp implements QuarkusApplication {

  private final ServiceRepository serviceRepository;
  private final VendorRepository vendorRepository;
  private final CustomerRepository customerRepository;

  public QuarkusApp(
      ServiceRepository serviceRepository,
      VendorRepository vendorRepository,
      CustomerRepository customerRepository) {

    this.serviceRepository = serviceRepository;
    this.vendorRepository = vendorRepository;
    this.customerRepository = customerRepository;
  }

  @Override
  @ActivateRequestContext
  public int run(String... args) throws Exception {

    List<Service> services = this.serviceRepository.listAll();
    services.forEach(System.out::println);

    Service service = this.serviceRepository.findById(2L);
    System.out.println(service);

    System.out.println("\n## Vendors ##");
    Vendor vendor = this.vendorRepository.findByName("MYCAT");
    System.out.println("Vendor by name: " + vendor);

    vendor = this.vendorRepository.findByEmail("kmiller3@alibaba.com");
    System.out.println("Vendor by email: " + vendor);

    System.out.println("\n## Customers ##");
    Customer customer = this.customerRepository.findByEmail("taciti@vehicula.edu");
    System.out.println("Customer by email: " + customer);

    return 0;
  }
}
