package com.example.datadolphinsandroidapp;

import static android.os.Build.USER;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.datadolphinsandroidapp.database.StockRepository;
import com.example.datadolphinsandroidapp.database.StockWithQuantity;
import com.example.datadolphinsandroidapp.databinding.ActivityMainBinding;
//import com.example.datadolphinsandroidapp.databinding.ActivityPortfolioBinding;

public class MainActivity extends AppCompatActivity {
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
            Log.d("PortfolioActivity", "Number of items: " + stockWithQuantities.size());
            for (StockWithQuantity stock : stockWithQuantities) {
                Log.d("PortfolioActivity", "Ticker: " + stock.getTicker() + ", Quantity: " + stock.getQuantity());
                fortfolioBalance+= stock.getPurchasePrice() * stock.getQuantity();
            }
            adapter.submitList(stockWithQuantities);
            binding.portfolioBalancePlaceHolder.setText("$" + String.valueOf(fortfolioBalance));
        });

        // Set up a button to navigate to BuyActivity
        binding.buyStockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open BuyActivity (no ticker passed from MainActivity)
                Intent intent = BuyActivity.buyIntentFactory(MainActivity.this);
                startActivity(intent);
            }
        });

        // Set up a button to navigate to SellActivity ---no use
//        binding.sellButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Open SellActivity (no ticker passed from MainActivity)
//                Intent intent = SellActivity.sellIntentFactory(MainActivity.this);
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
//            // Navigate to PortfolioActivity
//            Intent intent = new Intent(MainActivity.this, PortfolioActivity.class);
//            startActivity(intent);
//        });


    public static Intent openMain(Context context, String user){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER, user);
        //intent.getDataString();
        return intent;
    }

}