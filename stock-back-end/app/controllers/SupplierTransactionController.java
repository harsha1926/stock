package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import models.SupplierTransaction;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import stock.mysql.SupplierTransactionRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;

@Singleton
public class SupplierTransactionController extends Controller {
    private SupplierTransactionRepository supplierTransactionRepository;
    private HttpExecutionContext httpExecutionContext;

    @Inject
    public SupplierTransactionController(SupplierTransactionRepository supplierTransactionRepository, HttpExecutionContext httpExecutionContext) {
        this.supplierTransactionRepository = supplierTransactionRepository;
        this.httpExecutionContext = httpExecutionContext;
    }

    public CompletableFuture<Result> index() throws Exception {
        return this.supplierTransactionRepository.getSupplierTransactions().thenApplyAsync(supplierTransactions -> {
            ArrayNode arr = Json.newArray();
            for (int i = 0; i < supplierTransactions.size(); i++) {
                SupplierTransaction supplierTransaction = supplierTransactions.get(i);
                arr.add(supplierTransaction.toJson());
            }
            return ok(arr);
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });
    }
}
