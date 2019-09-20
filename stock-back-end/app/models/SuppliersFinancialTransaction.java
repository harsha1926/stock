package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

public class SuppliersFinancialTransaction {
    private Long supplierTransactionId;
    private Long financialTransactionId;

    public SuppliersFinancialTransaction(Long supplierTransactionId, Long financialTransactionId) {
        this.supplierTransactionId = supplierTransactionId;
        this.financialTransactionId = financialTransactionId;
    }
    public Long getSupplierTransactionId() {
        return supplierTransactionId;
    }

    public void setSupplierTransactionId(Long supplierTransactionId) {
        this.supplierTransactionId = supplierTransactionId;
    }

    public Long getFinancialTransactionId() {
        return financialTransactionId;
    }

    public void setFinancialTransactionId(Long financialTransactionId) {
        this.financialTransactionId = financialTransactionId;
    }
    public JsonNode toJson() {
        ObjectNode json = Json.newObject();
        json.put("supplier_transaction_id", this.supplierTransactionId);
        json.put("financial_transaction_detail_id", this.financialTransactionId);

        return json;
    }
}
