package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

public class Customer {
    private String phone;
    private String name;
    private String reference;
    private String address;
    private String email;

    public Customer(String name, String reference,String phone,String address) {
        this.name = name;
        this.reference = reference;
        this.phone = phone;
        this.address = address;

    }

    public Customer(String name, String reference, String phone, String address, String email) {
        this.name = name;
        this.reference = reference;
        this.phone = phone;
        this.address = address;
        this.email = email;
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
        json.put("name", this.name);
        json.put("reference", this.reference);
        json.put("phone", this.phone);
        json.put("address", this.address);
        json.put("email", this.email);

        return json;
    }
}
