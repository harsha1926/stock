package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

import java.sql.Date;

public class Product {
    private Long id;
    private String category;
    private String name;
    private String modified_by;
    private Date modified_on;

    public Product(Long id, String name, String category, String modified_by, Date modified_on) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.modified_by = modified_by;
        this.modified_on = modified_on;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        json.put("category", this.category);
        json.put("modified_by", this.modified_by);
        json.put("modified_on", this.modified_on != null ? this.modified_on.toString() : "");
        return json;
    }
}
