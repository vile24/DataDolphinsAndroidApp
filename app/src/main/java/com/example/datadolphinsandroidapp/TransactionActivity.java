package com.example.datadolphinsandroidapp;

import static android.os.Build.USER;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.datadolphinsandroidapp.database.StockRepository;
import com.example.datadolphinsandroidapp.database.StockWithQuantity;
import com.example.datadolphinsandroidapp.databinding.ActivityTransactionBinding;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;


public class TransactionActivity extends AppCompatActivity {
    private ActivityTransactionBinding binding;
    private StockRepository stockRepository;

    private TransactionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        stockRepository = StockRepository.getRepository(getApplication());

        // Set up RecyclerView
        adapter = new TransactionAdapter(this::navigateToSellActivity);
        binding.transactionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.transactionRecyclerView.setAdapter(adapter);

        stockRepository.getStocksWithQuantities().observe(this, stockWithQuantities -> {
            Log.d("TransactionActivity", "Number of items: " + stockWithQuantities.size());
            for (StockWithQuantity stock : stockWithQuantities) {
                Log.d("TransactionActivity", "Ticker: " + stock.getTicker() + ", Quantity: " + stock.getQuantity());
            }
            adapter.submitList(stockWithQuantities);
        });

        // Set up a button to navigate to BuyActivity
        binding.buyStockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open BuyActivity (no ticker passed from TransactionActivity)
                Intent intent = BuyActivity.buyIntentFactory(TransactionActivity.this);
                startActivity(intent);
            }
        });
    }

    // creating an Intent to navigate from TransactionActivity to BuyActivity.
    public static Intent transactionIntentFactory(Context context) {
        return new Intent(context, TransactionActivity.class);
    }

    private void navigateToSellActivity(StockWithQuantity stock) {

    }

}