package com.example.datadolphinsandroidapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.datadolphinsandroidapp.database.StockPortfolioDatabase;
import com.example.datadolphinsandroidapp.database.entities.User;
import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insert(User user);

    // @Delete
    // void delete(User user);

    @Query("SELECT * FROM " + StockPortfolioDatabase.USER_TABLE + " WHERE user_name = :username LIMIT 1")
    LiveData<User> getUserByUserName(String username);

    @Query("DELETE FROM " + StockPortfolioDatabase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + StockPortfolioDatabase.USER_TABLE + " ORDER BY user_name")
    LiveData<List<User>> getAllUsers();
}