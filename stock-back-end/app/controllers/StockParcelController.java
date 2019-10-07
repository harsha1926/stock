package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.StockParcel;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import stock.mysql.StockParcelRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;

@Singleton
public class StockParcelController extends Controller {
    private StockParcelRepository stockParcelRepository;
    private HttpExecutionContext httpExecutionContext;

    @Inject
    public StockParcelController(StockParcelRepository stockParcelRepository, HttpExecutionContext httpExecutionContext){
        this.httpExecutionContext = httpExecutionContext;
        this.stockParcelRepository = stockParcelRepository;
    }
    public CompletableFuture<Result> index() throws Exception{
        return this.stockParcelRepository.getStockParcels().thenApplyAsync(stockParcels -> {
            ArrayNode arr = Json.newArray();
            for (int i = 0; i < stockParcels.size(); i++) {
                StockParcel stockParcel = stockParcels.get(i);
                arr.add(stockParcel.toJson());
            }
            return ok(arr);
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });
    }
    public CompletableFuture<Result> read(Long id) throws Exception {
        return this.stockParcelRepository.getStockParcel(id).thenApplyAsync(stockParcel -> {
            return ok(stockParcel.toJson());
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });

    }
    public CompletableFuture<Result> delete(Long id) throws Exception {
        return this.stockParcelRepository.deleteStockParcel(id).thenApplyAsync(isDeleted -> {
            return ok(Json.toJson(isDeleted));
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });

    }
    public CompletableFuture<Result> create(Http.Request request) throws Exception {
        JsonNode requestJson = request.body().asJson();
        if(!(requestJson.hasNonNull("supplierName")
                && requestJson.get("supplierName").asText().length() > 3 &&
                requestJson.get("supplierName").asText().length() <= 50)) {
            return CompletableFuture.completedFuture(internalServerError("Invalid supplier name"));
        }
        if(!requestJson.hasNonNull("billNumber")) {
            return CompletableFuture.completedFuture(internalServerError("Invalid bill number"));
        }

        String supplierName = requestJson.hasNonNull("supplierName") ? requestJson.get("supplierName").asText(): "";
        String billNumber = requestJson.hasNonNull("billNumber") ? requestJson.get("billNumber").asText(): "";
        Long totalAmount = requestJson.hasNonNull("totalAmount") ? requestJson.get("totalAmount").asLong(): Long.valueOf("");


        return this.stockParcelRepository.addNewStockParcel(supplierName, billNumber, totalAmount ).thenApplyAsync(stockParcel -> {
            return ok(stockParcel.toJson());
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });
    }
    public CompletableFuture<Result> update(Http.Request request, Long id) throws Exception {
        JsonNode requestJson =  request.body().asJson();
        String supplierName = requestJson.get("supplierName").asText();
        String billNumber =requestJson.get("billNumber").asText();
        Long totalAmount = requestJson.get("totalAmount").asLong();
        return this.stockParcelRepository.updateStockParcel(id, supplierName,billNumber, totalAmount).thenApplyAsync(isUpdated -> {
            return ok(Json.toJson(isUpdated));
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });

    }
}


