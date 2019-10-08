package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

import java.sql.Date;

public class ParcelDetail {
    private Long id;
    private String billNumber;
    private String productName;
    private String productCategory;
    private Long purchaseCost;
    private String modifiedBy;
    private Date modifiedOn;

    public ParcelDetail(Long id, String billNumber, String productName, String productCategory, Long purchaseCost, String modifiedBy, Date modifiedOn){
        this.id = id;
        this.billNumber = billNumber;
        this.productName = productName;
        this.productCategory = productCategory;
        this.purchaseCost = purchaseCost;
        this.modifiedBy = modifiedBy;
        this.modifiedOn = modifiedOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public Long getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(Long purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }
    public JsonNode toJson(){
        ObjectNode json = Json.newObject();
        json.put("id", this.id);
        json.put("billNumber", this.billNumber);
        json.put("productName", this.productName);
        json.put("productCategory", this.productCategory);
        json.put("purchaseCost", this.purchaseCost);
        json.put("modifiedBy", this.modifiedBy);
        json.put("modifiedOn", this.modifiedOn != null ? this.modifiedOn.toString() : "");
        return json;
   }
}
