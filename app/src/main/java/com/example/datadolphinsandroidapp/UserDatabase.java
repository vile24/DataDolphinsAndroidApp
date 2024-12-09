package com.example.datadolphinsandroidapp;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.example.datadolphinsandroidapp.database.entities.Stock;
import com.example.datadolphinsandroidapp.database.entities.Transaction;
import com.example.datadolphinsandroidapp.database.entities.User;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Room;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Stock.class, User.class, Transaction.class},version = 2, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase{

    public static final String USER_TABLE = "userTable";
    private static final String STOCK_PORTFOLIO = "UserDatabase";
    private static volatile UserDatabase INSTANCE;
    private static final String TAG = "UserDatabase";
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract UserDAO userDAO();

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(TAG,"DATABASE CREATED!");
            databaseWriteExecutor. execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin1 = new User("a1","a1");
                admin1.setIs_admin(true);
                admin1.setCash_balance(999999999.99);
                dao.insert(admin1);
                User test1 = new User("t1","t1");
                dao.insert(test1);
            });
        }
    };

    static UserDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    UserDatabase.class, "user_database").addCallback(addDefaultValues).build();
                }
            }
        }
        return INSTANCE;
    }

    //private static class Callback extends RoomDatabase.Callback {
    //}
}
