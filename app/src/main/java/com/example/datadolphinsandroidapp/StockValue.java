/**
 * POJO
 */
package com.example.datadolphinsandroidapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.datadolphinsandroidapp.db.AppDataBase;

@Entity(tableName = AppDataBase.STOCK_VALUE_TABLE)
public class StockValue {
    /**
     * variables
     */
    @PrimaryKey(autoGenerate = true)
    private int stockID;
    private String Name;
    private String symbol;
    private double price;

    /**
     * Getters and Setters
     * @return
     */
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;


    }

    public int getStockID() {
        return stockID;
    }

    public void setStockID(int stockID) {
        this.stockID = stockID;
    }
}
