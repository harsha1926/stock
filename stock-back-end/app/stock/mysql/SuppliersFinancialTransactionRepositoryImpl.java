package stock.mysql;

import models.SuppliersFinancialTransaction;
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
public class SuppliersFinancialTransactionRepositoryImpl implements SuppliersFinancialTransactionRepository {
    private Database database;

    @Inject
    public SuppliersFinancialTransactionRepositoryImpl(@NamedDatabase("stock") Database database) {
        this.database = database;
    }

    @Override
    public CompletableFuture<List<SuppliersFinancialTransaction>> getTransactions() throws CompletionException {
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            List<SuppliersFinancialTransaction> transactions = new ArrayList<>();
            String sql = "select supplier_transaction_id, financial_transaction_detail_id from suppliers_financial_transactions";
            try(CallableStatement stmt = connection.prepareCall(sql)) {
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    transactions.add(deserializeTransaction(rs));
                }
                return transactions;
            } catch (CompletionException e) {
                throw e;
            }
        }));
    }
    private SuppliersFinancialTransaction deserializeTransaction(ResultSet rs) throws SQLException {
        Long supplierTransactionId = rs.getLong("supplier_transaction_id");
        Long financialTransactionId = rs.getLong("financial_transaction_detail_id");
        return new SuppliersFinancialTransaction(supplierTransactionId, financialTransactionId);
    }
}
