package com.example.datadolphinsandroidapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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
    @Query("SELECT * FROM " + StockPortfolioDatabase.TRANSACTION_TABLE + " WHERE ticker = :ticker")
    LiveData<List<Transaction>> getTransactionsByTicker(String ticker);

    // Retrieve all transactions from the table
    @Query("SELECT * FROM " + StockPortfolioDatabase.TRANSACTION_TABLE)
    LiveData<List<Transaction>> getAllTransactions();

    @Query("SELECT * FROM " + StockPortfolioDatabase.TRANSACTION_TABLE + " WHERE userId = :userId")
    LiveData<List<Transaction>> getTransactionsByUserId(int userId);

    // Query with no aliases, matches fields in StockWithQuantity
    @Query("SELECT ticker, SUM(quantity) AS quantity FROM " + StockPortfolioDatabase.TRANSACTION_TABLE + " GROUP BY ticker")
    LiveData<List<StockWithQuantity>> getStocksWithQuantities();

    // For debug only
    @Query("SELECT * FROM transactionTable")
    List<Transaction> getAllTransactionsRaw();


    @Query("SELECT * FROM " + StockPortfolioDatabase.TRANSACTION_TABLE + " WHERE userId = :userId AND ticker = :ticker")
    List<Transaction> getAllTransactionsUserIdAndTicker(int userId, String ticker);

    @Update
    void update(Transaction transaction);


}
