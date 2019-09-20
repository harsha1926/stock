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
            String sql = "select name, category from products";
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
            String sql = "SELECT NAME , CATEGORY  FROM PRODUCTS WHERE ID = ?";
            try(CallableStatement stmt = connection.prepareCall(sql)) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) {
                    return deserializeProductDetail(rs);
                } else {
                    return null;
                }
            } catch (CompletionException e) {
                throw e;
            }
        }));
    }

    private Product deserializeProduct(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        String category = rs.getString("category");
        return new Product(name, category);
    }

    private Product deserializeProductDetail(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        String category = rs.getString("category");

        return new Product(name, category);
    }
}
