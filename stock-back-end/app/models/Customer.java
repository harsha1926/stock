package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

public class Customer {
    private Long id;
    private String phone;
    private String name;
    private String reference;
    private String address;
    private String email;

    public Customer(Long id, String name, String reference,String phone,String address, String email) {
        this.id = id;
        this.name = name;
        this.reference = reference;
        this.phone = phone;
        this.address = address;


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public JsonNode toJson() {
        ObjectNode json = Json.newObject();
        json.put("id", this.id);
        json.put("name", this.name);
        json.put("reference", this.reference);
        json.put("phone", this.phone);
        json.put("address", this.address);
        json.put("email", this.email);

        return json;
    }
}
