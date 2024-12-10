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

    private int stockId;

    private int quantity;

    private double purchasePrice;

    private double sellPrice;

    /*
    Transaction constructor
     */

    public Transaction(int userId, int stockId, int quantity, double purchasePrice, double sellPrice) {
        this.userId = userId;
        this.stockId = stockId;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.sellPrice = sellPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionId == that.transactionId && userId == that.userId
                && stockId == that.stockId && quantity == that.quantity
                && Double.compare(purchasePrice, that.purchasePrice) == 0
                && Double.compare(sellPrice, that.sellPrice) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, userId, stockId, quantity, purchasePrice, sellPrice);
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

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
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