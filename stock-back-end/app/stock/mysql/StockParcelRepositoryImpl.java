package stock.mysql;

import models.StockParcel;
import play.db.Database;
import play.db.NamedDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Singleton
public class StockParcelRepositoryImpl implements StockParcelRepository {
    private Database database;
    @Inject
    private StockParcelRepositoryImpl(@NamedDatabase("stock") Database database) {
        this.database = database;
    }
    @Override
    public CompletableFuture<List<StockParcel>> getStockParcels() throws CompletionException {
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            List<StockParcel> stockParcels = new ArrayList<>();
            String sql = "select id, supplier_name, bill_number, total_amount from stock_parcel";
            try(CallableStatement stmt = connection.prepareCall(sql)) {
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    stockParcels.add(deserializeStockParcel(rs));
                }
                return stockParcels;
            } catch (CompletionException e) {
                throw e;
            }
        }));
    }
    @Override
    public CompletableFuture<StockParcel> getStockParcel(Long id) throws CompletionException  {
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            String sql = "select id, supplier_name, bill_number, total_amount from stock_parcel where id = ?";
            try(CallableStatement stmt = connection.prepareCall(sql)) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) {
                    return deserializeStockParcel(rs);
                } else {
                    return null;
                }
            } catch (CompletionException e) {
                throw e;
            }
        }));
    }
    @Override
    public CompletableFuture<StockParcel> addNewStockParcel(String supplierName, String billNumber, Long totalAmount) throws CompletionException {
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            String sql = "insert into stock_parcel (supplier_name, bill_number, total_amount) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, supplierName);
                stmt.setString(2, billNumber);
                stmt.setLong(3, totalAmount);
                stmt.executeUpdate();
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        Long id = generatedKeys.getLong(1);
                        try{
                            return getStockParcel(id).toCompletableFuture().get();
                        } catch (Exception e) {
                            throw new SQLException("Failed to create new stock parcel!");
                        }
                    } else {
                        throw new SQLException("Failed to create stock parcel!");
                    }
                }

            } catch (CompletionException e) {
                throw e;
            }
        }));
    }
    @Override
    public CompletableFuture<Boolean> deleteStockParcel(Long id) throws CompletionException{
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            String sql ="delete from stock_parcel where id = ? ";
            try(CallableStatement stmt = connection.prepareCall(sql)){
                stmt.setLong(1, id);

                int rows = stmt.executeUpdate();
                if(rows > 0)
                    return true;
                else
                    return false;
            }
            catch (CompletionException e) {
                throw e;
            }
        }));

    }
    @Override
    public CompletableFuture<Boolean> updateStockParcel(Long id, String supplierName, String billNumber, Long totalAmount) throws CompletionException{
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            String sql ="update stock_parcel set supplier_name=?, bill_number=?, total_amount= ? where id = ?;";
            try(CallableStatement stmt = connection.prepareCall(sql)){
                stmt.setString(1, supplierName);
                stmt.setString(2, billNumber);
                stmt.setLong(3, totalAmount);
                stmt.setLong(4, id);
                int rows = stmt.executeUpdate();
                if(rows > 0)
                    return true;
                else
                    return false;
            }
            catch (CompletionException e) {
                throw e;
            }
        }));

    }

    private StockParcel deserializeStockParcel(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String supplierName = rs.getString("supplier_name");
        String billNumber = rs.getString("bill_number");
        Long totalAmount = rs.getLong("total_amount");
        return new StockParcel(id, supplierName, billNumber, totalAmount);
    }
}
