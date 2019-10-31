package stock.mysql;

import models.ParcelDetail;
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
public class ParcelDetailsRepositoryImpl implements ParcelDetailsRepository {
    private Database database;
    @Inject
    public ParcelDetailsRepositoryImpl(@NamedDatabase("stock") Database database) {
        this.database = database;
    }
    @Override
    public CompletableFuture<List<ParcelDetail>> getParcelDetails() throws CompletionException {
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            List<ParcelDetail> suppliers = new ArrayList<>();
            String sql = "select id, bill_number, product_name, product_category, purchase_cost, modified_by, modified_on from parcel_details";
            try(CallableStatement stmt = connection.prepareCall(sql)) {
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    suppliers.add(deserializeParcelDetail(rs));
                }
                return suppliers;
            } catch (CompletionException e) {
                throw e;
            }
        }));
    }
    @Override
    public CompletableFuture<ParcelDetail> getParcelDetail(Long id) throws CompletionException  {
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            String sql = "select id, bill_number, product_name, product_category, purchase_cost, modified_by, modified_on from parcel_details where id = ?";
            try(CallableStatement stmt = connection.prepareCall(sql)) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) {
                    return deserializeParcelDetail(rs);
                } else {
                    return null;
                }
            } catch (CompletionException e) {
                throw e;
            }
        }));
    }
    @Override
    public CompletableFuture<ParcelDetail> addNewParcelDetail(String billNumber, String productName, String productCategory, Long purchaseCost) throws CompletionException {
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            String sql = "insert into parcel_details (bill_number, product_name, product_category, purchase_cost, modified_by, " +
                    "modified_on) VALUES (?, ?, ?, ?, ?, now())";
            try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, billNumber);
                stmt.setString(2, productName);
                stmt.setString(3, productCategory);
                stmt.setLong(4,purchaseCost);
                stmt.setString(5, "admin");
                stmt.executeUpdate();
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        Long id = generatedKeys.getLong(1);
                        try{
                            return getParcelDetail(id).toCompletableFuture().get();
                        } catch (Exception e) {
                            throw new SQLException("Failed to create new details!");
                        }
                    } else {
                        throw new SQLException("Failed to create new details!");
                    }
                }

            } catch (CompletionException e) {
                throw e;
            }
        }));
    }
    @Override
    public CompletableFuture<Boolean> deleteParcelDetail(Long id) throws CompletionException{
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            String sql ="delete from parcel_details where id = ? ";
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
    public CompletableFuture<Boolean> updateParcelDetail(Long id, String billNumber, String productName, String productCategory, Long purchaseCost) throws CompletionException{
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            String sql ="update parcel_details set bill_number=?, product_name=?, product_category= ?, purchase_cost=?, modified_by=?, modified_on=now()  where id = ?;";
            try(CallableStatement stmt = connection.prepareCall(sql)){
                stmt.setString(1, billNumber);
                stmt.setString(2, productName);
                stmt.setString(3, productCategory);
                stmt.setLong(4, purchaseCost);
                stmt.setString(5,"admin");
                stmt.setLong(6, id);
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
    private ParcelDetail deserializeParcelDetail(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String billNumber = rs.getString("bill_number");
        String productName = rs.getString("product_name");
        String productCategory = rs.getString("product_category");
        Long purchaseCost = rs.getLong("purchase_cost");
        String modifiedBy = rs.getString("modified_by");
        Date modifiedOn = rs.getDate("modified_on");
        return new ParcelDetail(id, billNumber, productName, productCategory, purchaseCost, modifiedBy, modifiedOn);
    }
}
