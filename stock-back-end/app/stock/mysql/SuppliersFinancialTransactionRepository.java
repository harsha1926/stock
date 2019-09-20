package stock.mysql;

import models.SuppliersFinancialTransaction;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface SuppliersFinancialTransactionRepository {
    public CompletableFuture<List<SuppliersFinancialTransaction>> getTransactions() throws Exception;
}
