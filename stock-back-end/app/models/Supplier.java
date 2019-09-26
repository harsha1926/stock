package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

public class Supplier {
    private Long id;
    private String phone;
    private String reference;
    private String name;
    private String address;
    private String email;
    private String country;
    private String state;
    private String city;
    private String postal_code;



    public Supplier(Long id, String name, String reference, String phone, String address, String email, String country, String state, String city, String postal_code ) {
        this.id = id;
        this.name = name;
        this.reference = reference;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.reference = country;
        this.phone = state;
        this.address = city;
        this.email = postal_code;
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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public JsonNode toJson() {
        ObjectNode json = Json.newObject();
        json.put("id", this.id);
        json.put("name", this.name);
        json.put("reference", this.reference);
        json.put("phone", this.phone);
        json.put("address", this.address);
        json.put("email", this.email);
        json.put("email", this.country);
        json.put("email", this.state);
        json.put("email", this.city);
        json.put("email", this.postal_code);

        return json;
    }
}
