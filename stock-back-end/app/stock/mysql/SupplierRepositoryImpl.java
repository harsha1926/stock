package stock.mysql;

import models.Supplier;
import org.joda.time.DateTime;
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
public class SupplierRepositoryImpl implements SupplierRepository {
    private Database database;

    @Inject
    public SupplierRepositoryImpl(@NamedDatabase("stock") Database database) {
        this.database = database;
    }

    @Override
    public CompletableFuture<List<Supplier>> getSuppliers() throws CompletionException {
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            List<Supplier> suppliers = new ArrayList<>();
            String sql = "select id, name, reference, address, email, phone, country, state, city, postal_code from suppliers";
            try(CallableStatement stmt = connection.prepareCall(sql)) {
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    suppliers.add(deserializeSupplier(rs));
                }
                return suppliers;
            } catch (CompletionException e) {
                throw e;
            }
        }));
    }
    @Override
    public CompletableFuture<Supplier> getSupplier(Long id) throws CompletionException  {
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            String sql = "select id, name, reference, address, email, phone from suppliers where id = ?";
            try(CallableStatement stmt = connection.prepareCall(sql)) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) {
                    return deserializeSupplier(rs);
                } else {
                    return null;
                }
            } catch (CompletionException e) {
                throw e;
            }
        }));
    }

    @Override
    public CompletableFuture<Boolean> addNewSupplier(String name, String reference, String address, String phone,
                               String email, String country, String state, String city, String postal_code) throws CompletionException {
         return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            String sql = "insert into `suppliers` (`name`, `reference`, `address`, `phone`, `email`, `modified_by`, " +
                    "`modified_on`, country, state, city, postal_code) VALUES (?, ?, ?, ?, ?, ?, now(), ?, ?, ?, ?)";
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
                if(rows > 0)
                    return true;
                 else
                    return false;
            } catch (CompletionException e) {
                throw e;
            }
        }));
    }
    @Override
    public CompletableFuture<Boolean> deleteSupplier(Long id) throws CompletionException{
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            String sql ="delete from suppliers where id = ? ";
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
    public CompletableFuture<Boolean> updateSupplier(Long id, String name, String reference, String address, String phone,
                                                     String email, String country, String state, String city, String postal_code) throws CompletionException{
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            String sql ="update suppliers set name=?, reference=?, address= ?, phone=?, email=?, country=?, state=?, city=?, postal_code=?  where id = ?;";
            try(CallableStatement stmt = connection.prepareCall(sql)){
                stmt.setLong(1, id);
                stmt.setString(2, name);
                stmt.setString(3, reference);
                stmt.setString(4, address);
                stmt.setString(5, phone);
                stmt.setString(6, email);

                stmt.setString(7, country);
                stmt.setString(8, state);
                stmt.setString(9, city);
                stmt.setString(10, postal_code);
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

    private Supplier deserializeSupplier(ResultSet rs) throws SQLException {
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
        return new Supplier(id, name, reference, phone, address, email, country, state, city, postal_code);
    }
}
