package com.example.datadolphinsandroidapp.database;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.datadolphinsandroidapp.BuyActivity;
import com.example.datadolphinsandroidapp.database.entities.Stock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// change shape of our db, add another table, need to increment version number.

@Database(entities = {Stock.class}, version = 1, exportSchema = false)
public abstract class StockDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "stockDatabase";
    public static final String STOCK_TABLE = "stockTable";

    // volatile only live in RAM, don't want multiple things (instances) access your db at any given time
    // Prevent two processes writing to the db at the same time, ended up with deadlock (write/query at the same time)
    private static volatile StockDatabase INSTANCE;

    // Don't want to multiple queries on Main thread. Rather run on Main thread, let it runs on child background
    // db only have maximum of 4 threads
    private static final int NUMBER_OF_THREADS = 4;

    // create a service, supply thread at startup as we need to do db operation, pull them out from the pool
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract StockDAO stockDAO();

    static StockDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (StockDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    StockDatabase.class,
                                    DATABASE_NAME)
                            // wipe db and start over if db being edit
                            .fallbackToDestructiveMigration()
                            .addCallback(getAddDefaultValuesCallback())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // add items to your database
    //private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {

    // Custom callback to pass context
    private static RoomDatabase.Callback getAddDefaultValuesCallback() {
        return new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                //run some quote when db is created. Use it as a way to insert default record into our db
                // when our db is created, we called this code ".addCallback(addDefaultValues)" perform some actions when our db is initially created
                Log.i(BuyActivity.TAG, "DATABASE CREATED!");

                databaseWriteExecutor.execute(() -> {

                    StockDAO dao = INSTANCE.stockDAO();
                    dao.deleteAll();
                    INSTANCE.stockDAO().deleteAll(); // Clear existing data

                    Stock appleStock = new Stock("AAPL", "Apple", 200.0);
                    Stock teslaStock = new Stock("TSLA", "Tesla", 300.00);
                    dao.insert(appleStock, teslaStock);

                    Log.i(BuyActivity.TAG, "Default stocks inserted.");

                    // Access the DAO
//                DAO making it harder to test or refactor. Need to manage threading yourself,
//                as DAO methods run on the main thread by default (which could block the UI).
                    // StockDAO dao = INSTANCE.stockDAO();


                    // Clear existing data
                   // dao.deleteAll();

                    // Insert Apple stock
//                Stock appleStock = new Stock("AAPL", "Apple", 200.0);
//                dao.insert(appleStock);
//                Log.i(BuyActivity.TAG, "Apple stock inserted: AAPL - $200");

                    // Insert Tesla stock
//                Stock teslaStock = new Stock("TSLA", "Tesla", 300.00);
//                dao.insert(teslaStock);
//                Log.i(BuyActivity.TAG, "Tesla stock inserted: TSLA - $300");
                });
            }
        };
    }


//    public void insertStock(String ticker, String company, double cost) {
//        databaseWriteExecutor.execute(() -> {
//            Stock stock = new Stock(ticker, company, cost);
//            INSTANCE.stockDAO().insert(stock);
//            Log.i(BuyActivity.TAG, "Stock inserted: " + ticker + " - $" + cost);
//        });
//
//    }
}













