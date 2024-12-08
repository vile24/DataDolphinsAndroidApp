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

    //void delete (User user);
    @Insert
    void insert(User user);

    @Query("SELECT * FROM userTable WHERE user_name = :username")
    LiveData<User> getUserByUserName(String username);

    @Query("DELETE FROM " + UserDatabase.USER_TABLE)
    void deleteAll();

    //@Query("SELECT * FROM " + UsersDatabase.USER_TABLE + " ORDER BY user_name")
    //LiveData<List<User>> getAllUsers();




}
