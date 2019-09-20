package stock.mysql;

import models.SupplierTransaction;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface SupplierTransactionRepository {
    public CompletableFuture<List<SupplierTransaction>> getSupplierTransactions() throws Exception;
}
