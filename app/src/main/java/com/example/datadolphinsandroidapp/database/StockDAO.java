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
    // action performs on our db
    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    //void insert(Stock stock);  // insert one or more stocks

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Stock... stocks); // Accepts varargs or an array of Stock - multiple insert




    @Query("DELETE FROM " + StockDatabase.STOCK_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + StockDatabase.STOCK_TABLE + " WHERE ticker == :ticker LIMIT 1")
    LiveData<Stock> getStockByTicker(String ticker);

    @Query("SELECT * FROM " + StockDatabase.STOCK_TABLE + " WHERE stockId == :stockId")
    LiveData<List<Stock>> getRecordersetStockIdLiveData(int stockId);


    @Query("SELECT * FROM " + StockDatabase.STOCK_TABLE)
    List<Stock> getAllRecords();

}




























