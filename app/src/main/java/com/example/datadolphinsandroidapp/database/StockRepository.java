package com.example.datadolphinsandroidapp.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.datadolphinsandroidapp.database.entities.Stock;
import com.example.datadolphinsandroidapp.database.entities.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class StockRepository {

    // Data Access Object (DAO) for interacting with the database.
    private final StockDAO stockDAO;
    private final TransactionDAO transactionDAO;

    // List to store all stocks retrieved from the database.
    private ArrayList<Stock> allStocks;
    private ArrayList<Transaction> allTransactions;

    // A single instance of the repository to ensure only one is created.
    private static StockRepository repository;

    // Tag for logging messages related to this class.
    public static final String TAG = "DAC_STOCK";

    // Private constructor to create the repository.
    // Gets the database instance and initializes the DAO and stock list.
    private StockRepository(Application application) {
        StockPortfolioDatabase db = StockPortfolioDatabase.getDatabase(application);
        stockDAO = db.stockDAO(); // Gets the DAO from the database.

        transactionDAO = db.transactionDAO(); // Gets the DAO from the database.
    }

    // Method to get the single instance of the repository.
    public static StockRepository getRepository(Application application) {
        if (repository != null) {
            return repository; // Return the existing instance if it's already created.
        }

        // Creates the repository in a background thread.
        // This prevents blocking the main thread while initializing.
        Future<StockRepository> future = StockPortfolioDatabase.databaseWriteExecutor.submit(() -> new StockRepository(application));
        try {
            return future.get(); // Waits for the background thread to finish and return the repository.
        } catch (InterruptedException | ExecutionException e) {
            // Logs an error if something goes wrong and throws an exception.
            Log.d(TAG, "Problem getting StockRepository, thread error.", e);
            throw new IllegalStateException("Failed to initialize StockRepository", e);
        }
    }


    // Inserts one or multiple stocks into the database.
    // The "Stock...stock" parameter allows passing multiple Stock objects (varargs).
    // Example: insertStock(appleStock, teslaStock, googleStock) or insertStock(appleStock).
    public void insertStock(Stock... stock) {
        // Runs the insertion in a background thread to avoid slowing down the app.
        StockPortfolioDatabase.databaseWriteExecutor.execute(() -> {
            stockDAO.insert(stock); // Calls the DAO to insert the stocks.
        });
    }

    // Insert multiple transactions into the database
    public void insertTransactions(Transaction... transactions) {
        StockPortfolioDatabase.databaseWriteExecutor.execute(() -> {
            transactionDAO.insert(transactions);
        });
    }

    // Retrieves a specific stock by its ticker symbol as LiveData.
    // LiveData allows observing changes to the data.
    public LiveData<Stock> getStockByTicker(String ticker) {
        return stockDAO.getStockByTicker(ticker); // DAO retrieves the stock by ticker.
    }

    public LiveData<Stock> getStockByStockId(int stockID) {
        return stockDAO.getStockByStockId(stockID); // DAO retrieves the stock by ticker.
    }


    // Retrieves all stocks with a specific stock ID as LiveData.
    public LiveData<List<Stock>> getAllStocksByStockIdLiveData(int stockId) {
        return stockDAO.getRecordersetStockIdLiveData(stockId); // DAO retrieves stocks by ID.
    }

    // Retrieves a list of all stocks in the database.
    public LiveData<List<Stock>> getAllStocks() {
        return stockDAO.getAllStocks();  // DAO retrieves all stock records.
    }

    // Retrieve all transactions
    public LiveData<List<Transaction>> getAllTransactions() {
        return transactionDAO.getAllTransactions();
    }

    // Retrieve transactions by user ID
    public LiveData<List<Transaction>> getTransactionsByUserId(int userId) {
        return transactionDAO.getTransactionsByUserId(userId);
    }

    // Fetch stocks with their quantities using LiveData
    public LiveData<List<StockWithQuantity>> getStocksWithQuantities() {
        return stockDAO.getStocksWithQuantities();
    }

}
