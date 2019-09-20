package stock.mysql;

import models.Stock;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface StockRepository {
    public CompletableFuture<List<Stock>> getStocks() throws Exception;
    public CompletableFuture<Stock> getStock(Long id) throws Exception;
}
