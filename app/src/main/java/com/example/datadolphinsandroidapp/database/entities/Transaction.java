package com.example.datadolphinsandroidapp.database.entities;
import java.math.BigDecimal;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
@Entity
public class Transaction {

    @PrimaryKey
    @NonNull
    private Integer stock;
    @ColumnInfo(name = "transaction")
    private Integer transaction;
    @ColumnInfo(name = "user_id")
    private int user_id;
    @ColumnInfo(name = "quantity")
    private Integer quantity;
    @ColumnInfo(name = "purchase_price")
    private double purchase_price;
    @ColumnInfo(name = "sell_price")
    private double sell_price; // Might want this for stock history

    /*
    Transaction constructor
     */
    public Transaction(Integer transaction, int user_id, Integer stock, Integer quantity, double purchase_price) {
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

    public double getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(double purchase_price) {
        this.purchase_price = purchase_price;
    }

    public double getSell_price() {
        return sell_price;
    }

    public void setSell_price(double sell_price) {
        this.sell_price = sell_price;
    }
}
