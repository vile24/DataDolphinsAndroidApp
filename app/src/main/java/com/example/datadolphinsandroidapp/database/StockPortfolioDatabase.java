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
import com.example.datadolphinsandroidapp.database.entities.Transaction;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// This is the database class for the app.
// When adding a new table, make sure to increase the version number.
@Database(entities = {Stock.class, Transaction.class}, version = 4, exportSchema = false)
public abstract class StockPortfolioDatabase extends RoomDatabase {

    // Name of the database file.
    private static final String DATABASE_NAME = "stockPortfolioDatabase";
    public static final String STOCK_TABLE = "stockTable";
    public static final String TRANSACTION_TABLE = "transactionTable";

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

    public abstract TransactionDAO transactionDAO();

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
                            .addCallback(getAddDefaultValuesCallback(context))
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // This callback is triggered when the database is created for the first time.
    private static RoomDatabase.Callback getAddDefaultValuesCallback(Context context) {
        return new RoomDatabase.Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Log.d(BuyActivity.TAG, "Database created: onCreate callback triggered.");

                databaseWriteExecutor.execute(() -> {
                    StockDAO dao = INSTANCE.stockDAO();
                    dao.deleteAll(); // Clear existing data

                    StockImporter importer = new StockImporter();
                    importer.addFromAssets(context, "nasdaq100.csv");


                    ArrayList<Stock> stocks = importer.getStockList();
                    for (Stock item : stocks) {
                        Log.d(BuyActivity.TAG, "Inserting stock: " + item.getTicker());
                        dao.insert(item);
                    }
                });
                Log.i("StockPortfolioDatabase", "Default stocks inserted.");
            }
        };
    }
}











