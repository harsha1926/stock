package stock.mysql;

import models.Customer;
import play.db.Database;
import play.db.NamedDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.CallableStatement;
import java.sql.Date;
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
            String sql = "select id, name, reference, address, email, phone, modified_by, modified_on, country, state, city, postal_code from customers where id = ?";
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
                                                       String email, String country, String state, String city, String postal_code) throws CompletionException {
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            String sql = "insert into `customers` (`name`, `reference`, `address`, `phone`, `email`, `modified_by`, \" +\n" +
                    "                    \"`modified_on`, country, state, city, postal_code) VALUES (?, ?, ?, ?, ?, ?, now(), ?, ?, ?, ?)";
            try (CallableStatement stmt = connection.prepareCall(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, reference);
                stmt.setString(3, address);
                stmt.setString(4, phone);
                stmt.setString(5, email);
                stmt.setString(6, "admin");
                stmt.setString(7, country);
                stmt.setString(8, state);
                stmt.setString(9, city);
                stmt.setString(10, postal_code);
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
    @Override
    public CompletableFuture<Boolean> updateCustomer(Long id, String name, String reference, String address, String phone,
                                                     String email, String country, String state, String city, String postal_code) throws CompletionException{
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            String sql ="update customers set name=?, reference=?, address= ?, phone=?, email=?, country=?, state=?, city=?, postal_code=?, modified_by=?, modified_on=now()  where id = ?;";
            try(CallableStatement stmt = connection.prepareCall(sql)){
                stmt.setString(1, name);
                stmt.setString(2, reference);
                stmt.setString(3, address);
                stmt.setString(4, phone);
                stmt.setString(5, email);
                stmt.setString(6, country);
                stmt.setString(7, state);
                stmt.setString(8, city);
                stmt.setString(9, postal_code);
                stmt.setString(10,"admin");
                stmt.setLong(11, id);
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
        String country = rs.getString("country");
        String state = rs.getString("state");
        String city = rs.getString("city");
        String postal_code = rs.getString("postal_code");
        String modified_by = rs.getString("modified_by");
        Date modified_on = rs.getDate("modified_on");
            return new Customer(id, name, reference, phone, address, email, country, state, city, postal_code, modified_by, modified_on);
        }
}
