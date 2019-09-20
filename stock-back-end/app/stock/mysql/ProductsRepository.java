package stock.mysql;

import models.Product;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public interface ProductsRepository {
    public CompletableFuture<List<Product>> getProducts() throws Exception;
    public CompletableFuture<Product> getProduct(Long id) throws Exception;
}
