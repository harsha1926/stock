package stock.mysql;

import models.FinancialTransaction;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface FinancialTransactionRepository {
    public CompletableFuture<List<FinancialTransaction>> getTransactions() throws Exception;
}
