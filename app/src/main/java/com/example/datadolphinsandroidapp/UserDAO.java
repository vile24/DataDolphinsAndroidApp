package com.example.datadolphinsandroidapp;

import com.example.datadolphinsandroidapp.database.entities.User;

//@Dao
public interface UserDAO {

    //TODO make this work for admin
    // @Delete
    void delete (User user);

}
