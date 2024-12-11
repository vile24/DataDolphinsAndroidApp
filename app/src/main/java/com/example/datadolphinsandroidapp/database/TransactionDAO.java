package com.example.datadolphinsandroidapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.datadolphinsandroidapp.database.entities.Transaction;

import java.util.List;

// Track users buy stocks
@Dao
public interface TransactionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Transaction transaction);    //Insert a single transaction

    // Insert multiple transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Transaction... transaction);    // Accepts varargs or an array of Stock

    // Delete all transactions from the table
    @Query("DELETE FROM " + StockPortfolioDatabase.TRANSACTION_TABLE)
    void deleteAll();

    // Retrieve a specific user's transactions by their userId
    @Query("SELECT * FROM " + StockPortfolioDatabase.TRANSACTION_TABLE + " WHERE userId = :userId")
    LiveData<List<Transaction>> getTransactionsByUserId(int userId);

    // Retrieve all transactions from the table
    @Query("SELECT * FROM " + StockPortfolioDatabase.TRANSACTION_TABLE)
    LiveData<List<Transaction>> getAllTransactions();

}
