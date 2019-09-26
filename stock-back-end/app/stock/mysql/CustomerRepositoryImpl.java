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
            String sql = "select id, name, reference, phone, address,email from customers";
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
            String sql = "select id,name, reference, phone, address, email from customers where id = ?";
            try (CallableStatement stmt = connection.prepareCall(sql)) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return deserializeCustomer(rs);
                } else {
                    return null;
                }
            } catch (CompletionException e) {
                throw e;
            }
        }));
    }
    @Override
    public CompletableFuture<Boolean> addNewCustomer  (String name, String reference, String address, String phone,
                                                     String email) throws CompletionException {
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            String sql = "insert into `customers` (`name`, `reference`, `address`, `phone`, `email`, `modified_by`, " +
                    "`modified_on`) VALUES (?, ?, ?, ?, ?,?, now())";
            try (CallableStatement stmt = connection.prepareCall(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, reference);
                stmt.setString(3, address);
                stmt.setString(4, phone);
                stmt.setString(5, email);
                stmt.setString(6, "admin");
                int rows = stmt.executeUpdate();
                if (rows > 0)
                    return true;
                else
                    return false;
            } catch (CompletionException e) {
                throw e;
            }
        }));
    }
    @Override
    public CompletableFuture<Boolean> deleteCustomer(Long id) throws CompletionException{
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            String sql ="delete from customers where id = ? ";
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

        private Customer deserializeCustomer(ResultSet rs) throws SQLException {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            String reference = rs.getString("reference");
            String phone = rs.getString("phone");
            String address = rs.getString("address");
            String email = rs.getString("email");
            return new Customer(id, name, reference, phone, address, email);
        }
}
