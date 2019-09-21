package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

public class Product {
    private Long id;
    private String category;
    private String name;

    public Product(Long id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
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

    public JsonNode toJson() {
        ObjectNode json = Json.newObject();
        json.put("id", this.id);
        json.put("name", this.name);
        json.put("category", this.category);
        return json;
    }
}
