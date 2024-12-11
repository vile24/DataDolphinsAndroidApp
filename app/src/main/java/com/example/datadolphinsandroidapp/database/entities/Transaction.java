package com.example.datadolphinsandroidapp.database.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.datadolphinsandroidapp.database.StockPortfolioDatabase;

import java.util.Objects;

@Entity(tableName = StockPortfolioDatabase.TRANSACTION_TABLE)

public class Transaction {

    @PrimaryKey(autoGenerate = true)
    private int transactionId;

    private int userId;
    private String ticker = "";

   // private int stockId;

    private int quantity;

    private double purchasePrice;

    private double sellPrice;

    /*
    Transaction constructor
     */

    public Transaction(int userId, String ticker, int quantity, double purchasePrice, double sellPrice) {
        this.userId = userId;
        this.ticker = ticker;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.sellPrice = sellPrice;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }
}