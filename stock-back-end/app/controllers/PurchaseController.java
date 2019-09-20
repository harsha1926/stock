package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import stock.mysql.PurchaseRepository;
import models.Purchase;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;

@Singleton
public class PurchaseController extends Controller {
    private PurchaseRepository purchaseRepository;
    private HttpExecutionContext httpExecutionContext;

    @Inject
    public PurchaseController(PurchaseRepository purchaseRepository, HttpExecutionContext httpExecutionContext) {
        this.purchaseRepository = purchaseRepository;
        this.httpExecutionContext = httpExecutionContext;
    }
    public CompletableFuture<Result> index() throws Exception {
        return this.purchaseRepository.getPurchase().thenApplyAsync(purchases -> {
            ArrayNode arr = Json.newArray();
            for (int i = 0; i < purchases.size(); i++){
                Purchase purchase = purchases.get(i);
                arr.add(purchase.toJson());
            }
            return ok(arr);
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });

    }
}
