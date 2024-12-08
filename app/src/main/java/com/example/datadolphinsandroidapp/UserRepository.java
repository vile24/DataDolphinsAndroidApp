package com.example.datadolphinsandroidapp;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;

import com.example.datadolphinsandroidapp.LoginActivity;
import com.example.datadolphinsandroidapp.NewUserActivity;
import com.example.datadolphinsandroidapp.MainActivity;
import com.example.datadolphinsandroidapp.database.entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class UserRepository {
    private final UserDAO userDAO;

    private ArrayList<User> UserLog;

    private static UserRepository repository;

    private UserRepository(Application application) {
        UserDatabase db = UserDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
        // this.UserLog = (ArrayList<User>) this.UserDAO.getAllRecords();
    }

    public static UserRepository getRepository(Application application){
        if (repository == null){
            repository = new UserRepository(application);
        }
        return repository;
    }


    public  void insertUser(User... users){
        UserDatabase.databaseWriteExecutor.execute(() ->{
            for (User user : users) {
                userDAO.insert(user);
            }
        });
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }
}