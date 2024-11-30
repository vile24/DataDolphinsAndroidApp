/**
 * To store database files
 */

package com.example.datadolphinsandroidapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.datadolphinsandroidapp.StockValue;

import java.util.List;

/**Data access object*/
@Dao
public interface StockValueDAO {

    @Insert
    void insert(StockValue... StockValues);

    @Update
    void update(StockValue... StockValues);

    @Delete
    void deleted(StockValue... StockValues);

    /**
     * @return a list of stock value objects from table
     */
    @Query("SELECT * FROM " + AppDataBase.STOCK_VALUE_TABLE)
    List<StockValue> getStockValues();

    @Query("SELECT * FROM " + AppDataBase.STOCK_VALUE_TABLE + "WHERE stockID = :stockID")
    StockValue getStockValuesByID(int stockID);





}
