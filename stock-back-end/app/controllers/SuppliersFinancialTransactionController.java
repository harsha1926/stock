package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import models.SuppliersFinancialTransaction;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Result;
import stock.mysql.SuppliersFinancialTransactionRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;

import static play.mvc.Results.internalServerError;
import static play.mvc.Results.ok;

@Singleton
public class SuppliersFinancialTransactionController {
    private SuppliersFinancialTransactionRepository suppliersFinancialTransactionRepository;
    private HttpExecutionContext httpExecutionContext;

    @Inject
    public SuppliersFinancialTransactionController(SuppliersFinancialTransactionRepository suppliersFinancialTransactionRepository, HttpExecutionContext httpExecutionContext) {
        this.suppliersFinancialTransactionRepository = suppliersFinancialTransactionRepository;
        this.httpExecutionContext = httpExecutionContext;
    }

    public CompletableFuture<Result> index() throws Exception {
        return this.suppliersFinancialTransactionRepository.getTransactions().thenApplyAsync(transactions -> {
            ArrayNode arr = Json.newArray();
            for (int i = 0; i < transactions.size(); i++) {
                SuppliersFinancialTransaction transaction = transactions.get(i);
                arr.add(transaction.toJson());
            }
            return ok(arr);
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });
    }
}
