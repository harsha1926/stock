package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

public class Stock {
    private Long supplierID;
    private Boolean sold;


    public Stock(Boolean sold, Long supplierID) {
        this.sold = sold;
        this.supplierID = supplierID;
    }

    public Long getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Long supplierID) {
        this.supplierID = supplierID;
    }

    public Boolean getSold() {
        return sold;
    }

    public void setSold(Boolean name) {
        this.sold = name;
    }
    public JsonNode toJson() {
        ObjectNode json = Json.newObject();
        json.put("supplierID", this.supplierID);
        json.put("isSold", this.sold);

        return json;
    }
}
