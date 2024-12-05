package com.example.datadolphinsandroidapp.database.entities;
import java.math.BigDecimal;
public class User {
    private String user_name;
    private String email;
    private String password;
    private Boolean is_admin = false;
    private BigDecimal cash_balance= BigDecimal.valueOf(0.00);

    /*
    Standard user constructor
     */
    public User(String user_name, String password) {
        this.user_name = user_name;
        this.password = password;
    }



    /*
    Basic Get-Sets
     */
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
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

    public BigDecimal getCash_balance() {
        return cash_balance;
    }

    public void setCash_balance(BigDecimal cash_balance) {
        this.cash_balance = cash_balance;
    }
}
