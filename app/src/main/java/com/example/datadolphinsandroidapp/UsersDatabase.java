package com.example.datadolphinsandroidapp;
import android.util.Log;
import androidx.annotation.NonNull;
import com.example.datadolphinsandroidapp.database.entities.Stock;
import com.example.datadolphinsandroidapp.database.entities.Transaction;
import com.example.datadolphinsandroidapp.database.entities.User;

//@Database(entities = {User.class, Stock.class, Transaction.class},version = 1, exportSchema = false)
public abstract class UsersDatabase {
        //extends RoomDatabase{
//
//    private static volatile UsersDatabase INSTANCE;
//
//    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            Log.i(MainActivity.TAG,"DATABASE CREATED!");
//            databaseWriteExecutor. execute(() -> {
//                UserDAO dao = INSTANCE.userDAO();
//                //dao.deleteAll();
//                User admin = new User("a1","a1");
//                admin.setIs_admin(true);
//                dao.insert(admin);
//                User user1 = new User("t1","t1");
//                dao.insert(user1);
//            });
//        }
//    };

//    public abstract UserDAO userDAO();

//    public abstract StockLogDAO stockLogDAO();

}
