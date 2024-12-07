package com.example.datadolphinsandroidapp.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.datadolphinsandroidapp.BuyActivity;
import com.example.datadolphinsandroidapp.database.entities.Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class StockRepository {
    private final StockDAO stockDAO;
    private ArrayList<Stock> allStocks;
    private static StockRepository repository;

    public static final String TAG = "DAC_STOCK";

    private StockRepository(Application application) {
        StockDatabase db = StockDatabase.getDatabase(application);
        stockDAO = db.stockDAO();
        allStocks = (ArrayList<Stock>) stockDAO.getAllRecords();
    }
    public static StockRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        // TODO Fix: Log the error and throw a meaningful exception. If the Future call fails, null is returned, which can cause NullPointerException later.
        Future<StockRepository> future = StockDatabase.databaseWriteExecutor.submit(() -> new StockRepository(application));
        try {
            return future.get();
        }catch (InterruptedException | ExecutionException e) {
            Log.d(TAG, "Problem getting StockRepository, thread error.", e);
            throw new IllegalStateException("Failed to initialize StockRepository", e);
        }
    }

    //create a public method named getAllStocks that returns an ArrayList of Stock


    public void insertStock(Stock stock) {
        StockDatabase.databaseWriteExecutor.execute(() ->
        {
            stockDAO.insert(stock);
        });
    }

    // Stock...stock "varargs" parameter, you can pass one or multiple Stock objects to this method.
    // e.g insertStock(appleStock, teslaStock, googleStock);
    // or insertStock(appleStock); insert on stock at a time, delete "..."
    public void insertStock(Stock...stock) {
        // ensures the insertion happens on a background thread (not the main thread).
        StockDatabase.databaseWriteExecutor.execute(() ->
        {
            // insert the Stock objects into the database using the DAO.
            stockDAO.insert(stock);
        });
    }

    public LiveData<Stock> getStockByTicker(String ticker) {
        return stockDAO.getStockByTicker(ticker);
    }

    public LiveData<List<Stock>>getAllStocksByStockIdLiveData(int stockId) {
        return stockDAO.getRecordersetStockIdLiveData(stockId);
    }

    public List<Stock> getAllStocks() {
        return stockDAO.getAllRecords();
    }

}
