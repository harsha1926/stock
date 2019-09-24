package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.Customer;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import stock.mysql.CustomerRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;

@Singleton
public class CustomersController extends Controller {
    private CustomerRepository customerRepository;
    private HttpExecutionContext httpExecutionContext;

    @Inject
    public CustomersController(CustomerRepository customerRepository, HttpExecutionContext httpExecutionContext) {
        this.customerRepository = customerRepository;
        this.httpExecutionContext = httpExecutionContext;
    }
    public CompletableFuture<Result> index() throws Exception {
        return this.customerRepository.getCustomers().thenApplyAsync(customers -> {
            ArrayNode arr = Json.newArray();
            for (int i = 0; i < customers.size(); i++) {
                Customer customer = customers.get(i);
                arr.add(customer.toJson());
            }
            return ok(arr);
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });
    }
    public CompletableFuture<Result> read(Long id) throws Exception {
        return this.customerRepository.getCustomer(id).thenApplyAsync(customer -> {
            return ok(customer.toJson());
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });

    }
    public CompletableFuture<Result> create(Http.Request request) throws Exception {
        JsonNode requestJson = request.body().asJson();
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
        String name = requestJson.hasNonNull("name") ? requestJson.get("name").asText():"";
        String reference = requestJson.hasNonNull("reference") ? requestJson.get("reference").asText(): "";
        String address = requestJson.hasNonNull("address") ? requestJson.get("address").asText():"";
        String phone = requestJson.hasNonNull("phone") ? requestJson.get("phone").asText():"";
        String email = requestJson.get("email").asText();

        return this.customerRepository.addNewCustomer(name, reference, address, phone, email).thenApplyAsync(isInserted -> {
            return ok(Json.toJson(isInserted));
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });
    }
}
