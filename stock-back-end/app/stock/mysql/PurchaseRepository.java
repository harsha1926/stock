package stock.mysql;

import models.Purchase;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PurchaseRepository {
    public CompletableFuture<List<Purchase>> getPurchase() throws Exception;
}
