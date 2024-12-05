package com.example.datadolphinsandroidapp.database.entities;
import java.math.BigDecimal;
public class Transaction {

    private Integer transaction;
    private int user_id;
    private Integer stock;
    private Integer quantity;
    private BigDecimal purchase_price;
    private BigDecimal sell_price = BigDecimal.valueOf(-1); // Might want this for stock history

    /*
    Transaction constructor
     */
    public Transaction(Integer transaction, int user_id, Integer stock, Integer quantity, BigDecimal purchase_price) {
        this.transaction = transaction;
        this.user_id = user_id;
        this.stock = stock;
        this.quantity = quantity;
        this.purchase_price = purchase_price;
    }

    public Integer getTransaction() {
        return transaction;
    }

    public void setTransaction(Integer transaction) {
        this.transaction = transaction;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(BigDecimal purchase_price) {
        this.purchase_price = purchase_price;
    }

    public BigDecimal getSell_price() {
        return sell_price;
    }

    public void setSell_price(BigDecimal sell_price) {
        this.sell_price = sell_price;
    }
}
