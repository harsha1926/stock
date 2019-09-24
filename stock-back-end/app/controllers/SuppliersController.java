package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.Supplier;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import stock.mysql.SupplierRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.CompletableFuture;

@Singleton
public class SuppliersController extends Controller {
    private SupplierRepository supplierRepository;
    private HttpExecutionContext httpExecutionContext;

    @Inject
    public SuppliersController(SupplierRepository supplierRepository, HttpExecutionContext httpExecutionContext) {
        this.supplierRepository = supplierRepository;
        this.httpExecutionContext = httpExecutionContext;
    }
    public CompletableFuture<Result> index() throws Exception {
        return this.supplierRepository.getSuppliers().thenApplyAsync(suppliers -> {
            ArrayNode arr = Json.newArray();
            for (int i = 0; i < suppliers.size(); i++) {
                Supplier supplier = suppliers.get(i);
                arr.add(supplier.toJson());
            }
            return ok(arr);
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });
    }
    public CompletableFuture<Result> read(Long id) throws Exception {
        return this.supplierRepository.getSupplier(id).thenApplyAsync(supplier -> {
            return ok(supplier.toJson());
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });

    }

    public CompletableFuture<Result> create(Http.Request request) throws Exception {
        JsonNode requestJson = request.body().asJson();

        //if valid save in database else throw error
        if(!(requestJson.hasNonNull("name")
                && requestJson.get("name").asText().length() > 3 &&
                requestJson.get("name").asText().length() <= 50)) {
            return CompletableFuture.completedFuture(internalServerError("Invalid name"));
        }
        if(!requestJson.hasNonNull("address")) {
            return CompletableFuture.completedFuture(internalServerError("Invalid address"));
        }
        if(!(requestJson.hasNonNull("phone") && requestJson.get("phone").asText().length() ==10))
        {
            return CompletableFuture.completedFuture(internalServerError("Invalid phone"));
        }
        if(!(requestJson.hasNonNull("email") && requestJson.get("email").asText().matches
                ("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")))
        {
            return CompletableFuture.completedFuture(internalServerError("Invalid email"));
        }



        String name = requestJson.get("name").asText();
        String reference = requestJson.hasNonNull("reference") ? requestJson.get("reference").asText(): "";
        String address = requestJson.hasNonNull("address") ? requestJson.get("address").asText():"";
        String phone = requestJson.hasNonNull("phone") ? requestJson.get("phone").asText():"";
        String email = requestJson.get("email").asText();

        return this.supplierRepository.addNewSupplier(name, reference, address, phone, email).thenApplyAsync(isInserted -> {
            return ok(Json.toJson(isInserted));
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });
    }

}


