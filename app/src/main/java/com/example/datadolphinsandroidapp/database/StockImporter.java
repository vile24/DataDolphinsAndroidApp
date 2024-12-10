package com.example.datadolphinsandroidapp.database;
import android.content.Context;
import android.util.Log;
import com.example.datadolphinsandroidapp.BuyActivity;
import com.example.datadolphinsandroidapp.database.entities.Stock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class StockImporter {

    /**
     * HashMap that stores each word and the list of words that follow it.
     */
    private final ArrayList<Stock> stockList = new ArrayList<>();

    public void addFromAssets(Context context, String filename) {
        try (InputStream inputStream = context.getAssets().open(filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Log.e(BuyActivity.TAG, "Processing: " + line);
                addLine(line);
            }
        } catch (IOException e) {
            Log.e(BuyActivity.TAG, "Error reading file: " + filename + ", " + e.getMessage());
        }
    }

    private void addLine(String line) {
        if (!line.isEmpty()) {
            String[] parts = line.trim().split(",");

            if (parts.length < 3) {
                Log.e("StockImporter", "Invalid line: " + line);
                return;
            }

            String ticker = parts[0].trim();
            String company = parts[1].trim();
            double costDouble = Double.parseDouble(parts[2].trim());

            Log.i("StockImporter", "Parsed stock: " + ticker + ", " + company + ", " + costDouble);
            Stock stock = new Stock(ticker, company, costDouble);
            stockList.add(stock);
        }
    }

    /**
     * Returns the stock data.
     *
     * @return HashMap of stock data.
     */
    public ArrayList<Stock> getStockList() {
        return stockList;
    }
}
