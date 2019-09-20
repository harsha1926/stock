package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import models.Customer;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
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
}
