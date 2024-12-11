/*This file represents the data model for your portfolio.*/

package com.example.datadolphinsandroidapp.database;

public class StockWithQuantity {

    private String ticker;    // Matches 'ticker' column from the query
    private int quantity;     // Matches 'quantity' column from the query (SUM(quantity))

    public StockWithQuantity(String ticker, int quantity) {
        this.ticker = ticker;
        this.quantity = quantity;
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
}
