package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

public class SupplierTransaction {
        private Long productId;
        private Long supplierId;
        private Long quantity;
        private Long discountPercentage;
        private Long profitPercentage;

        public SupplierTransaction(Long productId, Long supplierId, Long quantity, Long discountPercentage, Long profitPercentage) {
            this.productId = productId;
            this.supplierId = supplierId;
            this.quantity = quantity;
            this.discountPercentage = discountPercentage;
            this.profitPercentage = profitPercentage;
        }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Long discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Long getProfitPercentage() {
        return profitPercentage;
    }

    public void setProfitPercentage(Long profitPercentage) {
        this.profitPercentage = profitPercentage;
    }

    public JsonNode toJson() {
            ObjectNode json = Json.newObject();
            json.put("product_id", this.productId);
            json.put("supplier_id", this.supplierId);
            json.put("quantity", this.quantity);
            json.put("discount_percentage", this.discountPercentage);
            json.put("profit_percentage", this.profitPercentage);

            return json;
        }
}
