package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import models.FinancialTransaction;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Result;
import stock.mysql.FinancialTransactionRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;

import static play.mvc.Results.internalServerError;
import static play.mvc.Results.ok;

@Singleton
public class FinancialTransactionController {
    private FinancialTransactionRepository financialTransactionRepository;
    private HttpExecutionContext httpExecutionContext;

    @Inject
    public FinancialTransactionController(FinancialTransactionRepository financialTransactionRepository, HttpExecutionContext httpExecutionContext) {
        this.financialTransactionRepository = financialTransactionRepository;
        this.httpExecutionContext = httpExecutionContext;
    }

    public CompletableFuture<Result> index() throws Exception {
        return this.financialTransactionRepository.getTransactions().thenApplyAsync(transactions -> {
            ArrayNode arr = Json.newArray();
            for (int i = 0; i < transactions.size(); i++) {
                FinancialTransaction transaction = transactions.get(i);
                arr.add(transaction.toJson());
            }
            return ok(arr);
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });
    }
}
