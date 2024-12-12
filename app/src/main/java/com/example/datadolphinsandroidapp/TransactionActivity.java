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
import com.example.datadolphinsandroidapp.databinding.ActivityMainBinding;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
//import com.example.datadolphinsandroidapp.databinding.ActivityPortfolioBinding;

public class TransactionActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private StockRepository stockRepository;

    //private ActivityPortfolioBinding binding;

    private PortfolioAdapter adapter;

    double fortfolioBalance = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        stockRepository = StockRepository.getRepository(getApplication());

        // Set up RecyclerView
        adapter = new PortfolioAdapter(this::navigateToSellActivity);
        binding.portfolioRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.portfolioRecyclerView.setAdapter(adapter);

        // Observe LiveData from Repository
//        stockRepository.getStocksWithQuantities().observe(this, stockWithQuantities -> {
//            adapter.submitList(stockWithQuantities);
//        });

        stockRepository.getStocksWithQuantities().observe(this, stockWithQuantities -> {
            Log.d("TransactionActivity", "Number of items: " + stockWithQuantities.size());
            for (StockWithQuantity stock : stockWithQuantities) {
                Log.d("TransactionActivity", "Ticker: " + stock.getTicker() + ", Quantity: " + stock.getQuantity());
                fortfolioBalance += stock.getPurchasePrice() * stock.getQuantity();
            }
            adapter.submitList(stockWithQuantities);
            NumberFormat formatter = NumberFormat.getCurrencyInstance();

            String formattedAmount = formatter.format(fortfolioBalance);

            binding.portfolioBalancePlaceHolder.setText((formattedAmount));
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

        // Set up a button to navigate to SellActivity ---no use
//        binding.sellButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Open SellActivity (no ticker passed from TransactionActivity)
//                Intent intent = SellActivity.sellIntentFactory(TransactionActivity.this);
//                startActivity(intent);
//            }
//        });
    }

    private void navigateToSellActivity(StockWithQuantity stock) {
        Intent intent = SellActivity.sellIntentFactory(this, stock.getTicker(), stock.getQuantity());
        startActivity(intent);

    }



    // Bind Portfolio Button
//        binding.btnPortfolio.setOnClickListener(v -> {
//            // Navigate to TransactionActivity
//            Intent intent = new Intent(TransactionActivity.this, TransactionActivity.class);
//            startActivity(intent);
//        });


    public static Intent openMain(Context context, String user){
        Intent intent = new Intent(context, TransactionActivity.class);
        intent.putExtra(USER, user);
        //intent.getDataString();
        return intent;
    }

}