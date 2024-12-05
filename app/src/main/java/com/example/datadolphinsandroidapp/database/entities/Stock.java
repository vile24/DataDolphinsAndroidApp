package com.example.datadolphinsandroidapp.database.entities;
import java.math.BigDecimal;
public class Stock {
    private Integer stock;
    private String ticker_symbol;
    private String company_name;
    private BigDecimal base_price;

    /*
    Stock constructor
     */
    public Stock(Integer stock, String ticker_symbol, String company_name, BigDecimal base_price) {
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

    public BigDecimal getBase_price() {
        return base_price;
    }

    public void setBase_price(BigDecimal base_price) {
        this.base_price = base_price;
    }
}
