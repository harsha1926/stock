package stock.mysql;

import models.Customer;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CustomerRepository {
    public CompletableFuture<List<Customer>> getCustomers() throws Exception;
    public CompletableFuture<Customer> getCustomer(Long id) throws Exception;
    public CompletableFuture<Customer> addNewCustomer(String name, String reference, String address1, String address2, String phone,
                                                     String email, String country, String state, String city, String postal_code) throws Exception;
    public CompletableFuture<Boolean> deleteCustomer(Long id) throws Exception;
    public CompletableFuture<Boolean> updateCustomer(Long id,String name, String reference, String address1, String address2, String phone,
                                                     String email, String country, String state, String city, String postalCode) throws Exception;
}
