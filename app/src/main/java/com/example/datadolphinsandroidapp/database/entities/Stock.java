package com.example.datadolphinsandroidapp.database.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.datadolphinsandroidapp.database.StockDatabase;

import java.util.Objects;

@Entity(tableName = StockDatabase.STOCK_TABLE)
public class Stock {
    @PrimaryKey(autoGenerate = true)
    private int stockId;
    private String ticker;
    private String company;
    private double cost;

    /*
    Stock constructor
     */
    public Stock(String ticker, String company, double cost) {

        this.ticker = ticker;
        this.company = company;
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return stockId == stock.stockId && Double.compare(cost, stock.cost) == 0 && Objects.equals(ticker, stock.ticker) && Objects.equals(company, stock.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockId, ticker, company, cost);
    }

  /*
    Basic Get-Sets
     */

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}