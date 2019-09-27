package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

import java.sql.Date;

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
    private String postalCode;
    private String modified_by;
    private Date modified_on;




    public Supplier(Long id, String name, String reference, String phone, String address, String email, String country, String state, String city, String postalCode, String modified_by, Date modified_on ) {
        this.id = id;
        this.name = name;
        this.reference = reference;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.country = country;
        this.state = state;
        this.city = city;
        this.postalCode = postalCode;
        this.modified_by = modified_by;
        this.modified_on = modified_on;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public Date getModified_on() {
        return modified_on;
    }

    public void setModified_on(Date modified_on) {
        this.modified_on = modified_on;
    }

    public JsonNode toJson() {
        ObjectNode json = Json.newObject();
        json.put("id", this.id);
        json.put("name", this.name);
        json.put("reference", this.reference);
        json.put("phone", this.phone);
        json.put("address", this.address);
        json.put("email", this.email);
        json.put("country", this.country);
        json.put("state", this.state);
        json.put("city", this.city);
        json.put("postal_code", this.postalCode);
        json.put("modified_by", this.modified_by);
        json.put("modified_on", this.modified_on != null ? this.modified_on.toString() : "");
        return json;
    }
}
