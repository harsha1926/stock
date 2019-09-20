package stock.mysql;

import models.SupplierTransaction;
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
public class SupplierTransactionRepositoryImpl implements SupplierTransactionRepository {
    private Database database;

    @Inject
    public SupplierTransactionRepositoryImpl(@NamedDatabase("stock") Database database) {
        this.database = database;
    }

    @Override
    public CompletableFuture<List<SupplierTransaction>> getSupplierTransactions() throws CompletionException {
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            List<SupplierTransaction> supplierTransactions = new ArrayList<>();
            String sql = "select product_id, supplier_id, quantity, discount_percentage, profit_percentage from suppliers_transactions";
            try(CallableStatement stmt = connection.prepareCall(sql)) {
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    supplierTransactions.add(deserializeSupplierTransaction(rs));
                }
                return supplierTransactions;
            } catch (CompletionException e) {
                throw e;
            }
        }));
    }
    private SupplierTransaction deserializeSupplierTransaction(ResultSet rs) throws SQLException {

        Long productId = rs.getLong("product_id");
        Long supplierId = rs.getLong("supplier_id");
        Long quantity = rs.getLong("quantity");
        Long discountPercentage = rs.getLong("discount_percentage");
        Long profitPercentage = rs.getLong("profit_percentage");

        return new SupplierTransaction(productId, supplierId, quantity, discountPercentage, profitPercentage);
    }
}
