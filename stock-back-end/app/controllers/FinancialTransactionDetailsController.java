package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import models.FinancialTransactionDetails;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Result;
import stock.mysql.FinancialTransactionDetailsRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;

import static play.mvc.Results.internalServerError;
import static play.mvc.Results.ok;

@Singleton
public class FinancialTransactionDetailsController {
    private FinancialTransactionDetailsRepository financialTransactionDetailsRepository;
    private HttpExecutionContext httpExecutionContext;

    @Inject
    public FinancialTransactionDetailsController(FinancialTransactionDetailsRepository financialTransactionDetailsRepository, HttpExecutionContext httpExecutionContext) {
        this.financialTransactionDetailsRepository = financialTransactionDetailsRepository;
        this.httpExecutionContext = httpExecutionContext;
    }

    public CompletableFuture<Result> index() throws Exception {
        return this.financialTransactionDetailsRepository.getTransactionDetails().thenApplyAsync(transactionDetails -> {
            ArrayNode arr = Json.newArray();
            for (int i = 0; i < transactionDetails.size(); i++) {
                FinancialTransactionDetails transactionDetail = transactionDetails.get(i);
                arr.add(transactionDetail.toJson());
            }
            return ok(arr);
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });
    }
}
