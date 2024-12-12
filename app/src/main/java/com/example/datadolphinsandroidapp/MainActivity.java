package com.example.datadolphinsandroidapp;

import static android.os.Build.USER;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datadolphinsandroidapp.database.StockRepository;
import com.example.datadolphinsandroidapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Set up a button to navigate to transactions page
        binding.transactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open BuyActivity (no ticker passed from MainActivity)
                Intent intent = TransactionActivity.transactionIntentFactory(MainActivity.this);
                startActivity(intent);
            }
        });


        binding.portBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open SellActivity (no ticker passed from MainActivity)
                Intent intent = LoginActivity.loginIntentFactory(MainActivity.this);
                startActivity(intent);
            }
        });

    }

    public static Intent openMain(Context context, String user){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("USER", user);
        // intent.getDataString();
        return intent;
    }




}