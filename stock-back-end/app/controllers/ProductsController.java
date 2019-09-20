package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import models.Product;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
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
}
