package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

public class FinancialTransactionDetails {
    private Long financialTransactionId;
    private Long amount;

    public FinancialTransactionDetails(Long financialTransactionId, Long amount) {
        this.financialTransactionId = financialTransactionId;
        this.amount = amount;
    }

    public Long getFinancialTransactionId() {
        return financialTransactionId;
    }

    public void setFinancialTransactionId(Long financialTransactionId) {
        this.financialTransactionId = financialTransactionId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public JsonNode toJson() {
        ObjectNode json = Json.newObject();
        json.put("financial_transaction_id", this.financialTransactionId);
        json.put("amount", this.amount);

        return json;
    }
}
