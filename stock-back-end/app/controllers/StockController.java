package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import models.Stock;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import stock.mysql.StockRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;

@Singleton
public class StockController extends Controller {
    private StockRepository stockRepository;
    private HttpExecutionContext httpExecutionContext;

    @Inject
    public StockController(StockRepository stockRepository, HttpExecutionContext httpExecutionContext) {
        this.stockRepository = stockRepository;
        this.httpExecutionContext = httpExecutionContext;
    }
    public CompletableFuture<Result> index() throws Exception {
        return this.stockRepository.getStocks().thenApplyAsync(stocks -> {
            ArrayNode arr = Json.newArray();
            for (int i = 0; i < stocks.size(); i++) {
                Stock stock = stocks.get(i);
                arr.add(stock.toJson());
            }
            return ok(arr);
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });
    }
    public CompletableFuture<Result> read(Long id) throws Exception {
        return this.stockRepository.getStock(id).thenApplyAsync(stock -> {
            return ok(stock.toJson());
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });
    }
}
