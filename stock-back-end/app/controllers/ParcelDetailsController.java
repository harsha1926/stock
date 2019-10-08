package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.ParcelDetail;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import stock.mysql.ParcelDetailsRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;

@Singleton
public class ParcelDetailsController extends Controller {
    private HttpExecutionContext httpExecutionContext;
    private ParcelDetailsRepository parcelDetailsRepository;
    @Inject
    public ParcelDetailsController(HttpExecutionContext httpExecutionContext, ParcelDetailsRepository parcelDetailsRepository){
        this.httpExecutionContext = httpExecutionContext;
        this.parcelDetailsRepository = parcelDetailsRepository;

    }
    public CompletableFuture<Result> index() throws Exception {
        return this.parcelDetailsRepository.getParcelDetails().thenApplyAsync(parcelDetails -> {
            ArrayNode arr = Json.newArray();
            for (int i = 0; i < parcelDetails.size(); i++) {
                ParcelDetail parcelDetail = parcelDetails.get(i);
                arr.add(parcelDetail.toJson());
            }
            return ok(arr);
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });
    }
    public CompletableFuture<Result> read(Long id) throws Exception {
        return this.parcelDetailsRepository.getParcelDetail(id).thenApplyAsync(parcelDetail -> {
            return ok(parcelDetail.toJson());
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });
    }
    public CompletableFuture<Result> delete(Long id) throws Exception {
        return this.parcelDetailsRepository.deleteParcelDetail(id).thenApplyAsync(isDeleted -> {
            return ok(Json.toJson(isDeleted));
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });
    }
    public CompletableFuture<Result> create(Http.Request request) throws Exception {
        JsonNode requestJson = request.body().asJson();
        String billNumber = requestJson.hasNonNull("billNumber") ?  requestJson.get("billNumber").asText():"";
        String productName = requestJson.hasNonNull("productName") ? requestJson.get("productName").asText(): "";
        String productCategory = requestJson.hasNonNull("productCategory") ? requestJson.get("productCategory").asText(): "";
        Long purchaseCost = requestJson.hasNonNull("purchaseCost") ? requestJson.get("purchaseCost").asLong(): Long.valueOf("");

        return this.parcelDetailsRepository.addNewParcelDetail(billNumber, productName, productCategory, purchaseCost).thenApplyAsync(parcelDetail -> {
            return ok(parcelDetail.toJson());
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });
    }
    public CompletableFuture<Result> update(Http.Request request, Long id) throws Exception {
        JsonNode requestJson =  request.body().asJson();
        String billNumber = requestJson.get("billNumber").asText();
        String productName =requestJson.get("productName").asText();
        String productCategory = requestJson.get("productCategory").asText();
        Long purchaseCost = requestJson.get("purchaseCost").asLong();
        return this.parcelDetailsRepository.updateParcelDetail(id, billNumber, productName, productCategory, purchaseCost).thenApplyAsync(isUpdated -> {
            return ok(Json.toJson(isUpdated));
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });

    }
}
