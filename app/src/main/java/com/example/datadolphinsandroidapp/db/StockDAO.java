/**
 * To store database files
 */

package com.example.datadolphinsandroidapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.datadolphinsandroidapp.Stock;

import java.util.List;

/**Data access object*/
@Dao
public interface StockDAO {

    @Insert
    void insert(Stock... StockValues);

    @Update
    void update(Stock... StockValues);

    @Delete
    void deleted(Stock... StockValues);

    /**
     * @return a list of stock value objects from table
     */
//    @Query("SELECT * FROM " + AppDataBase.STOCK_VALUE_TABLE)
//    List<Stock> getStockValues();

//    @Query("SELECT * FROM " + AppDataBase.STOCK_VALUE_TABLE + "WHERE stockID = :stockID")
//    Stock getStockValuesByID(int stockID);
//
//



}
