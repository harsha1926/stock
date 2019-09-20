package stock.mysql;

import com.google.inject.internal.cglib.core.$ClassNameReader;
import models.Purchase;
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
public class PurchaseRepositoryImpl implements PurchaseRepository {
    private Database database;

    @Inject
    public  PurchaseRepositoryImpl(@NamedDatabase("stock") Database database ){
        this.database = database;
    }

    @Override
    public CompletableFuture<List<Purchase>> getPurchase() throws CompletionException {
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            List<Purchase> purchases = new ArrayList<>();
            String sql = "SELECT * FROM PURCHASE";
            try (CallableStatement stmt = connection.prepareCall(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    purchases.add(deserializePurchase(rs));
                }
                return purchases;
            } catch (CompletionException e) {
                throw e;
            }
        }));
    }

    private Purchase deserializePurchase(ResultSet rs) throws SQLException{
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        String address = rs.getString("address");
        return new Purchase(id, name,address);
    }
}
