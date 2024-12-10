package com.example.datadolphinsandroidapp.database;

import android.content.Context;
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

// This is the database class for the app.
// When adding a new table, make sure to increase the version number.
@Database(entities = {Stock.class}, version = 1, exportSchema = false)
public abstract class StockPortfolioDatabase extends RoomDatabase {

    // Name of the database file.
    private static final String DATABASE_NAME = "stockPortfolioDatabase";
    public static final String STOCK_TABLE = "stockTable";

    // This ensures only one instance of the database is created in memory (RAM).
    // Having multiple instances could cause errors if two parts of the app try to access
    // or write to the database at the same time.
    private static volatile StockPortfolioDatabase INSTANCE;

    // The database should not run operations on the main thread (to avoid slowing down the app).
    // Instead, database tasks are handled in the background, using a maximum of 4 threads.
    private static final int NUMBER_OF_THREADS = 4;

    // A pool of background threads created for handling database tasks efficiently.
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Abstract method to get access to the StockDAO for database operations.
    public abstract StockDAO stockDAO();

    // This method provides the database instance. If it doesn't exist, it creates one.
    static StockPortfolioDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (StockPortfolioDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    StockPortfolioDatabase.class,
                                    DATABASE_NAME)
                            // If there are changes to the database structure, this will reset it to avoid errors.
                            .fallbackToDestructiveMigration()
                            // Adds default data to the database when it is created for the first time.
                            .addCallback(getAddDefaultValuesCallback())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // This callback is triggered when the database is created for the first time.
    private static RoomDatabase.Callback getAddDefaultValuesCallback() {
        return new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                Log.i(BuyActivity.TAG, "DATABASE CREATED!");

                // Uses a background thread to add default data to the database.
                databaseWriteExecutor.execute(() -> {

                    StockDAO dao = INSTANCE.stockDAO(); // Gets the DAO to interact with the database.
                    dao.deleteAll(); // Clears any existing data in the database.
                    INSTANCE.stockDAO().deleteAll(); // Clear existing data

                    // Adds some example data to the database for testing purposes.
                    // TODO: read from csv - Juan ?
                    Stock appleStock = new Stock("AAPL", "Apple", 200.0);
                    Stock teslaStock = new Stock("TSLA", "Tesla", 300.00);
                    dao.insert(appleStock, teslaStock);

                    Log.i(BuyActivity.TAG, "Default stocks inserted.");
                });
            }
        };
    }
}













