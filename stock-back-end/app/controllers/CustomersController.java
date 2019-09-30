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
        if(!requestJson.hasNonNull("address1")) {
            return CompletableFuture.completedFuture(internalServerError("Invalid address1"));
        }
        if(!requestJson.hasNonNull("address2")) {
            return CompletableFuture.completedFuture(internalServerError("Invalid address2"));
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
        if(!requestJson.hasNonNull("country")) {
            return CompletableFuture.completedFuture(internalServerError("Invalid country"));
        }
        if(!requestJson.hasNonNull("state")) {
            return CompletableFuture.completedFuture(internalServerError("Invalid state"));
        }
        if(!requestJson.hasNonNull("city")) {
            return CompletableFuture.completedFuture(internalServerError("Invalid city"));
        }
        if(!requestJson.hasNonNull("postalCode")) {
            return CompletableFuture.completedFuture(internalServerError("Invalid postalCode"));
        }
        String name = requestJson.hasNonNull("name") ? requestJson.get("name").asText():"";
        String reference = requestJson.hasNonNull("reference") ? requestJson.get("reference").asText(): "";
        String address1 = requestJson.hasNonNull("address1") ? requestJson.get("address1").asText():"";
        String address2 = requestJson.get("address2").asText();
        String phone = requestJson.hasNonNull("phone") ? requestJson.get("phone").asText():"";
        String email = requestJson.get("email").asText();
        String country = requestJson.hasNonNull("country") ? requestJson.get("country").asText():"";
        String state = requestJson.hasNonNull("state") ? requestJson.get("state").asText():"";
        String city = requestJson.hasNonNull("city") ? requestJson.get("city").asText():"";
        String postalCode = requestJson.hasNonNull("postalCode") ? requestJson.get("postalCode").asText():"";

        return this.customerRepository.addNewCustomer(name, reference, address1, address2, phone, email,  country, state, city, postalCode ).thenApplyAsync(isInserted -> {
            return ok(Json.toJson(isInserted));
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });
    }
    public CompletableFuture<Result> delete(Long id) throws Exception {
        return this.customerRepository.deleteCustomer(id).thenApplyAsync(isDeleted -> {
            return ok(Json.toJson(isDeleted));
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });

    }
    public CompletableFuture<Result> update(Http.Request request, Long id) throws Exception {
        JsonNode requestJson =  request.body().asJson();
        String name = requestJson.get("name").asText();
        String reference =requestJson.get("reference").asText();
        String address1 = requestJson.get("address1").asText();
        String address2 = requestJson.get("address2").asText();
        String phone = requestJson.get("phone").asText();
        String email = requestJson.get("email").asText();
        String country = requestJson.get("country").asText();
        String state = requestJson.get("state").asText();
        String city = requestJson.get("city").asText();
        String postalCode = requestJson.get("postalCode").asText();

        return this.customerRepository.updateCustomer(id, name, reference, address1, address2, phone, email, country, state, city, postalCode).thenApplyAsync(isUpdated -> {
            return ok(Json.toJson(isUpdated));
        }, httpExecutionContext.current()).exceptionally(e -> {
            e.printStackTrace();
            return internalServerError(e.toString());
        });

    }
}
