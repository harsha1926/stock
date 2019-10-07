package stock.mysql;

import models.StockParcel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface StockParcelRepository {
    public CompletableFuture<List<StockParcel>> getStockParcels() throws Exception;
    public CompletableFuture<StockParcel> getStockParcel(Long id) throws Exception;
    public CompletableFuture<StockParcel> addNewStockParcel(String supplierName, String billNumber, Long totalAmount) throws Exception;
    public CompletableFuture<Boolean> deleteStockParcel(Long id) throws Exception;
    public CompletableFuture<Boolean> updateStockParcel(Long id, String supplierName, String billNumber, Long totalAmount) throws Exception;

}
