package com.example.datadolphinsandroidapp.database.entities;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
@Entity
public class Stock {

    @PrimaryKey
    @NonNull
    private Integer stock;
    @ColumnInfo(name = "company_name")
    private String company_name;
    @ColumnInfo(name = "ticker_symbol")
    private String ticker_symbol;
    @ColumnInfo(name = "base_price")
    private double  base_price;
    /*
    Stock constructor
     */
    public Stock(String company_name,Integer stock, String ticker_symbol, double base_price) {
        this.stock = stock;
        this.ticker_symbol = ticker_symbol;
        this.company_name = company_name;
        this.base_price = base_price;
    }

    /*
    Basic Get-Sets
     */

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getTicker_symbol() {
        return ticker_symbol;
    }

    public void setTicker_symbol(String ticker_symbol) {
        this.ticker_symbol = ticker_symbol;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public double getBase_price() {
        return base_price;
    }

    public void setBase_price(double base_price) {
        this.base_price = base_price;
    }
}
