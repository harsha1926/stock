package stock.mysql;

import models.FinancialTransactionDetails;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface FinancialTransactionDetailsRepository {
    public CompletableFuture<List<FinancialTransactionDetails>> getTransactionDetails() throws Exception;
}
