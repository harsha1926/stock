package stock.mysql;

import models.FinancialTransactionDetails;
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
public class FinancialTransactionDetailsRepositoryImpl implements FinancialTransactionDetailsRepository{
    private Database database;

    @Inject
    public FinancialTransactionDetailsRepositoryImpl(@NamedDatabase("stock") Database database) {
        this.database = database;
    }

    @Override
    public CompletableFuture<List<FinancialTransactionDetails>> getTransactionDetails() throws CompletionException {
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            List<FinancialTransactionDetails> transactionDetails = new ArrayList<>();
            String sql = "select financial_transaction_id, amount from financial_transactions_details";
            try(CallableStatement stmt = connection.prepareCall(sql)) {
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    transactionDetails.add(deserializeTransactionDetails(rs));
                }
                return transactionDetails;
            } catch (CompletionException e) {
                throw e;
            }
        }));
    }

    private FinancialTransactionDetails deserializeTransactionDetails(ResultSet rs) throws SQLException {
        Long transactionId = rs.getLong("financial_transaction_id");
        Long amount = rs.getLong("amount");
        return new FinancialTransactionDetails(transactionId, amount);
    }
}
