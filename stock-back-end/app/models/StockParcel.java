package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

public class StockParcel {
    private Long id;
    private String supplierName;
    private String billNumber;
    private Long totalAmount;

    public StockParcel(Long id, String supplierName, String billNumber, Long totalAmount){
        this.id = id;
        this.supplierName = supplierName;
        this.billNumber = billNumber;
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public JsonNode toJson(){
        ObjectNode json = Json.newObject();
        json.put("id", this.id);
        json.put("supplierName", this.supplierName);
        json.put("billNumber", this.billNumber);
        json.put("totalAmount", this.totalAmount);
        return json;
    }
}
