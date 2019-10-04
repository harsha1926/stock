package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.Product;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import stock.mysql.ProductsRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;

@Singleton
public class ProductsController extends Controller {
    private ProductsRepository productsRepository;
    private HttpExecutionContext httpExecutionContext;

    @Inject
    public ProductsController(ProductsRepository productsRepository, HttpExecutionContext httpExecutionContext) {
        this.productsRepository = productsRepository;
        this.httpExecutionContext = httpExecutionContext;
    }

    public CompletableFuture<Result> index() throws Exception {
            return this.productsRepository.getProducts().thenApplyAsync(products -> {
                ArrayNode arr = Json.newArray();
                for (int i = 0; i < products.size(); i++) {
                    Product product = products.get(i);
                    arr.add(product.toJson());
                }
                return ok(arr);
            }, httpExecutionContext.current()).exceptionally(e -> {
                e.printStackTrace();
                return internalServerError(e.toString());
            });
    }
    public CompletableFuture<Result> read(Long id) throws Exception {
        return this.productsRepository.getProduct(id).thenApplyAsync(product -> {
            return ok(product.toJson());
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });

    }
    public CompletableFuture<Result> create(Http.Request request) throws Exception {
        JsonNode requestJson = request.body().asJson();
        if(!requestJson.hasNonNull("name")) {
            return CompletableFuture.completedFuture(internalServerError("Invalid name"));
        }
        if(!requestJson.hasNonNull("category")) {
            return CompletableFuture.completedFuture(internalServerError("Invalid category"));
        }

        String name = requestJson.hasNonNull("name") ? requestJson.get("name").asText():"";
        String category = requestJson.hasNonNull("category") ? requestJson.get("category").asText():"";

        return this.productsRepository.addNewProduct(name, category).thenApplyAsync(product -> {
            return ok(product.toJson());
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });
    }
    public CompletableFuture<Result> delete(Long id) throws Exception {
        return this.productsRepository.deleteProduct(id).thenApplyAsync(isDeleted -> {
            return ok(Json.toJson(isDeleted));
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });

    }
    public CompletableFuture<Result> update(Http.Request request, Long id) throws Exception {
        JsonNode requestJson =  request.body().asJson();
        String name = requestJson.get("name").asText();
        String category =requestJson.get("category").asText();

        return this.productsRepository.updateProduct(id, name, category ).thenApplyAsync(isUpdated -> {
            return ok(Json.toJson(isUpdated));
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });

    }

}
