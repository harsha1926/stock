package stock.mysql;

import models.Customer;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CustomerRepository {
    public CompletableFuture<List<Customer>> getCustomers() throws Exception;
    public CompletableFuture<Customer> getCustomer(Long id) throws Exception;
}
