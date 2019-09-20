package stock.mysql;

import models.Customer;
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
public class CustomerRepositoryImpl implements CustomerRepository{
    private Database database;

    @Inject
    public CustomerRepositoryImpl(@NamedDatabase("stock") Database database) {
        this.database = database;
    }

    @Override
    public CompletableFuture<List<Customer>> getCustomers() throws CompletionException {
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            List<Customer> customers = new ArrayList<>();
            String sql = "select name, reference, phone, address from customers";
            try(CallableStatement stmt = connection.prepareCall(sql)) {
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    customers.add(deserializeCustomer(rs));
                }
                return customers;
            } catch (CompletionException e) {
                throw e;
            }
        }));
    }
    @Override
    public CompletableFuture<Customer> getCustomer(Long id) throws CompletionException {
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            String sql = "select name, reference, phone, address, email from customers where id = ?";
            try (CallableStatement stmt = connection.prepareCall(sql)) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return deserializeCustomerDetail(rs);
                } else {
                    return null;
                }
            } catch (CompletionException e) {
                throw e;
            }
        }));
    }

    private Customer deserializeCustomer(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        String reference = rs.getString("reference");
        String phone = rs.getString("phone");
        String address = rs.getString("address");

        return new Customer(name, reference, phone, address);
    }

    private Customer deserializeCustomerDetail(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        String reference = rs.getString("reference");
        String phone = rs.getString("phone");
        String address = rs.getString("address");
        String email = rs.getString("email");

        return new Customer(name, reference, phone, address, email);
    }
}
