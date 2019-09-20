package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

import java.util.Date;

public class FinancialTransaction {
    private String transactionId;
    private Long amount;
    private Date transactionDate;
    private String bankName;
    public FinancialTransaction(String transactionId, Long amount, Date transactionDate, String bankName) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.bankName = bankName;

    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public JsonNode toJson() {
        ObjectNode json = Json.newObject();
        json.put("transaction_id", this.transactionId);
        json.put("amount", this.amount);
        json.put("transaction_date", String.valueOf(this.transactionDate));
        json.put("bank_name", this.bankName);
        return json;
    }
}
