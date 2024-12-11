package com.example.datadolphinsandroidapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import com.example.datadolphinsandroidapp.database.entities.User;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insert(User user);
    @Delete
    void delete (User user);
    @Query("SELECT * FROM userTable WHERE user_name = :username")
    LiveData<User> getUserByUserName(String username);

    @Query("DELETE FROM " + UserDatabase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + UserDatabase.USER_TABLE + " ORDER BY user_name")
    LiveData<List<User>> getAllUsers();

}
