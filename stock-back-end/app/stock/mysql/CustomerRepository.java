package stock.mysql;

import models.Customer;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CustomerRepository {
    public CompletableFuture<List<Customer>> getCustomers() throws Exception;
    public CompletableFuture<Customer> getCustomer(Long id) throws Exception;
    public CompletableFuture<Boolean> addNewCustomer(String name, String reference, String address, String phone,
                                                     String email) throws Exception;
    public CompletableFuture<Boolean> deleteCustomer(Long id) throws Exception;
}
