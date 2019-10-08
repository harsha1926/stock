package stock.mysql;

import models.ParcelDetail;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ParcelDetailsRepository {
    public CompletableFuture<List<ParcelDetail>> getParcelDetails() throws Exception;
    public CompletableFuture<ParcelDetail> getParcelDetail(Long id) throws Exception;
    public CompletableFuture<ParcelDetail> addNewParcelDetail(String billNumber, String productName, String productCategory, Long purchaseCost) throws Exception;
    public CompletableFuture<Boolean> deleteParcelDetail(Long id) throws Exception;
    public CompletableFuture<Boolean> updateParcelDetail(Long id, String billNumber, String productName, String productCategory, Long purchaseCost) throws Exception;
}
