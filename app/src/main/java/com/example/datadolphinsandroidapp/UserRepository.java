package com.example.datadolphinsandroidapp;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.datadolphinsandroidapp.database.StockPortfolioDatabase;
import com.example.datadolphinsandroidapp.database.entities.User;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final UserDAO userDAO;

    private ArrayList<User> UserLog;

    private static UserRepository repository;

    private UserRepository(Application application) {
        StockPortfolioDatabase db = StockPortfolioDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
        LiveData<List<User>> allLogs = userDAO.getAllUsers();
    }

    public static UserRepository getRepository(Application application){
        if (repository == null){
            repository = new UserRepository(application);
        }
        return repository;
    }

    public  void insertUser(User... users){
        StockPortfolioDatabase.databaseWriteExecutor.execute(() ->{
            for (User user : users) {
                userDAO.insert(user);
            }
        });
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }
}