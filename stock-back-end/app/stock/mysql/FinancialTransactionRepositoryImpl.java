package stock.mysql;

import models.FinancialTransaction;
import play.db.Database;
import play.db.NamedDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Singleton
public class FinancialTransactionRepositoryImpl implements FinancialTransactionRepository {
    private Database database;

    @Inject
    public FinancialTransactionRepositoryImpl(@NamedDatabase("stock") Database database) {
        this.database = database;
    }

    @Override
    public CompletableFuture<List<FinancialTransaction>> getTransactions() throws CompletionException {
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            List<FinancialTransaction> transactions = new ArrayList<>();
            String sql = "select transaction_id, amount, transaction_date, bank_name from financial_transactions";
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
    private FinancialTransaction deserializeTransaction(ResultSet rs) throws SQLException {
        String transactionId = rs.getString("transaction_id");
        Long amount = rs.getLong("amount");
        Date transactionDate = rs.getDate("transaction_date");
        String bankName =rs.getString("bank_name");

        return new FinancialTransaction(transactionId, amount, transactionDate,bankName);

    }
}
