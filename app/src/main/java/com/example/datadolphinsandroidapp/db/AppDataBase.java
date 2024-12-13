/**
 *Database
 */

package com.example.datadolphinsandroidapp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.datadolphinsandroidapp.Stock;

//storing stock values
@Database(entities = {Stock.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public static final String DB_NAME = "com.example.datadolphinsandroidapp.STOCKVALUE_DATABASE";
    public static final String STOCK_VALUE_TABLE = "com.example.datadolphinsandroidapp.STOCKVALUE_TABLE";

    public abstract StockDAO getStockValueDAO();

}
