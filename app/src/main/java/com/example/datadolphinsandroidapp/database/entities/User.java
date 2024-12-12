package com.example.datadolphinsandroidapp.database.entities;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

import com.example.datadolphinsandroidapp.StockLogDAO;
import com.example.datadolphinsandroidapp.database.StockPortfolioDatabase;

@Entity(tableName = StockPortfolioDatabase.USER_TABLE)
public class User {
    @PrimaryKey
    @NonNull
    private String user_name;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "is_admin")
    private Boolean is_admin = false;
    @ColumnInfo(name = "cash_balance")
    private double cash_balance = 100.00;

    /*
    Standard user constructor
     */
    public User(@NonNull String user_name, String password) {
        this.user_name = user_name;
        this.password = password;
    }

    public boolean newUser(String user_name, String password1,String password2){
        //TODO check if user already exists
        if (password1.equals(password2)){
            User test = new User(user_name,password1);
            return true;
        }
        return false;
    }

    /*
    Basic Get-Sets
     */
    @NonNull
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(@NonNull String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(Boolean is_admin) {
        this.is_admin = is_admin;
    }

    public double getCash_balance() {
        return cash_balance;
    }

    public void setCash_balance(double cash_balance) {
        this.cash_balance = cash_balance;
    }
}
