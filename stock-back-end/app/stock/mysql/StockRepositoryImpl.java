package stock.mysql;

import models.Stock;
import play.db.Database;
import play.db.NamedDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Singleton
public class StockRepositoryImpl implements StockRepository {
    private Database database;

    @Inject
    public StockRepositoryImpl(@NamedDatabase("stock") Database database) {
        this.database = database;
    }

    @Override
    public CompletableFuture<List<Stock>> getStocks() throws CompletionException {
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            List<Stock> stocks = new ArrayList<>();
            String sql = "select supplier_transaction_id, isSold from stock";
            try(CallableStatement stmt = connection.prepareCall(sql)) {
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    stocks.add(deserializeStockDetail(rs));
                }
                return stocks;
            } catch (CompletionException e) {
                throw e;
            }
        }));
    }

    @Override
    public CompletableFuture<Stock> getStock(Long id) throws CompletionException  {
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            String sql = "select supplier_transaction_id, isSold from stock where id = ?";
            try(CallableStatement stmt = connection.prepareCall(sql)) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) {
                    return deserializeStockDetail(rs);
                } else {
                    return null;
                }
            } catch (CompletionException e) {
                throw e;
            }
        }));
    }
    private Stock deserializeStockDetail(ResultSet rs) throws SQLException {
        Long supplierId = rs.getLong("suppliers_transactions_id");
        Boolean sold = rs.getBoolean("isSold");

        return new Stock(sold, supplierId);
    }

}
