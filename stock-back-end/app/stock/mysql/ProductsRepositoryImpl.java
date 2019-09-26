package stock.mysql;

import models.Product;
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
public class ProductsRepositoryImpl implements ProductsRepository {
    private Database database;

    @Inject
    public ProductsRepositoryImpl(@NamedDatabase("stock") Database database) {
        this.database = database;
    }

    @Override
    public CompletableFuture<List<Product>> getProducts() throws CompletionException  {
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            List<Product> products = new ArrayList<>();
            String sql = "select id, name, category from products";
            try(CallableStatement stmt = connection.prepareCall(sql)) {
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    products.add(deserializeProduct(rs));
                }
                return products;
            } catch (CompletionException e) {
                throw e;
            }
        }));
    }

    @Override
    public CompletableFuture<Product> getProduct(Long id) throws CompletionException  {
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            String sql = "select id, name, category from products where id = ?";
            try(CallableStatement stmt = connection.prepareCall(sql)) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) {
                    return deserializeProduct(rs);
                } else {
                    return null;
                }
            } catch (CompletionException e) {
                throw e;
            }
        }));
    }
    @Override
    public CompletableFuture<Boolean> addNewProduct(String name, String category) throws CompletionException {
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            String sql = "insert into `products` (`name`, `category`, `modified_by`, " +
                    "`modified_on`) VALUES (?, ?,?, now())";
            try (CallableStatement stmt = connection.prepareCall(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, category);
                stmt.setString(3, "admin");
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
    public CompletableFuture<Boolean> deleteProduct(Long id) throws CompletionException{
        return CompletableFuture.supplyAsync(() -> this.database.withConnection(connection -> {
            String sql ="delete from products where id = ? ";
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
    private Product deserializeProduct(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        String category = rs.getString("category");
        return new Product(id, name, category);
    }
}
