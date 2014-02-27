package com.paypal.pos.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

/**
 * Created by pderoxas on 2/24/14.
 */
public class Transaction {
    private String id;
    private String storeId;
    private String registerId;
    private String cashierName;
    private Date transactionDate;
    private TransactionType type;
    private List<Item> items = new ArrayList<Item>();
    private Payment payment;

    public Transaction(String id, String storeId, String registerId, String cashierName, Date transactionDate, TransactionType type) {
        this.id = id;
        this.storeId = storeId;
        this.registerId = registerId;
        this.cashierName = cashierName;
        this.transactionDate = transactionDate;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
