package stock.mysql;

import models.Product;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public interface ProductsRepository {
    public CompletableFuture<List<Product>> getProducts() throws Exception;
    public CompletableFuture<Product> getProduct(Long id) throws Exception;
    public CompletableFuture<Product> addNewProduct(String name, String category) throws Exception;
    public CompletableFuture<Boolean> deleteProduct(Long id) throws Exception;
    public CompletableFuture<Boolean> updateProduct(Long id,String name, String category) throws Exception;
}
