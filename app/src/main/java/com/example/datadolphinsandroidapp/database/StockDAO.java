package com.example.datadolphinsandroidapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.datadolphinsandroidapp.database.entities.Stock;

import java.util.List;

@Dao
public interface StockDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<Stock> stocks);

    // action performs on our db
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Stock... stocks); // Accepts varargs or an array of Stock - multiple insert


    @Query("DELETE FROM " + StockPortfolioDatabase.STOCK_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + StockPortfolioDatabase.STOCK_TABLE + " ORDER BY ticker ASC")
    LiveData<List<Stock>> getAllStocks();

    @Query("SELECT * FROM " + StockPortfolioDatabase.STOCK_TABLE + " WHERE ticker == :ticker LIMIT 1")
    LiveData<Stock> getStockByTicker(String ticker);

    @Query("SELECT * FROM " + StockPortfolioDatabase.STOCK_TABLE + " WHERE stockId == :stockId")
    LiveData<List<Stock>> getRecordersetStockIdLiveData(int stockId);

    @Query("SELECT * FROM " + StockPortfolioDatabase.STOCK_TABLE + " WHERE stockId == :stockId")
    LiveData<Stock> getStockByStockId(int stockId);

        // Query to get stock names and quantities using LiveData
//        @Query("SELECT s.ticker AS stockName, t.quantity AS stockQuantity " +
//                "FROM stockTable AS s " +
//                "LEFT JOIN transactionTable AS t " +
//                "ON s.stockId = t.stockId")
//        LiveData<List<StockWithQuantity>> getStocksWithQuantities();

//    @Query("SELECT s.ticker AS stockName, t.quantity AS stockQuantity " +
//            "FROM stockTable AS s " +
//            "LEFT JOIN transactionTable AS t " +
//            "ON s.stockId = t.stockId")
//    LiveData<List<StockWithQuantity>> getStocksWithQuantities();

    @Query("SELECT s.ticker AS stockName, SUM(t.quantity) AS stockQuantity " +
            "FROM stockTable AS s " +
            "LEFT JOIN transactionTable AS t ON s.stockId = t.stockId " +
            "GROUP BY s.stockId")
    LiveData<List<StockWithQuantity>> getStocksWithQuantities();
}




























