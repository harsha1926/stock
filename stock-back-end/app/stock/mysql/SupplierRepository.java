package stock.mysql;

import models.Supplier;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface SupplierRepository {
    public CompletableFuture<List<Supplier>> getSuppliers() throws Exception;
    public CompletableFuture<Supplier> getSupplier(Long id) throws Exception;
    public CompletableFuture<Boolean> addNewSupplier(String name, String reference, String address, String phone,
                               String email, String country, String state, String city, String postal_code) throws Exception;
    public CompletableFuture<Boolean> deleteSupplier(Long id) throws Exception;
    public CompletableFuture<Boolean> updateSupplier(Long id,String name, String reference, String address, String phone,
                                                     String email, String country, String state, String city, String postal_code) throws Exception;
}
