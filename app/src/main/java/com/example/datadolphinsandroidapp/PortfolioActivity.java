/*This file manages the RecyclerView and data binding.*/

package com.example.datadolphinsandroidapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.datadolphinsandroidapp.database.StockRepository;
import com.example.datadolphinsandroidapp.database.StockWithQuantity;
import com.example.datadolphinsandroidapp.databinding.ActivityPortfolioBinding;

public class PortfolioActivity extends AppCompatActivity {

    private ActivityPortfolioBinding binding;
    private PortfolioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize View Binding
        binding = ActivityPortfolioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Repository
        StockRepository stockRepository = StockRepository.getRepository(getApplication());

        // Set up RecyclerView
        adapter = new PortfolioAdapter(stock -> navigateToSellActivity(stock));
        binding.portfolioRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.portfolioRecyclerView.setAdapter(adapter);

        // Observe LiveData from Repository
        stockRepository.getStocksWithQuantities().observe(this, stockWithQuantities -> {
            adapter.submitList(stockWithQuantities);
        });
    }

    private void navigateToSellActivity(StockWithQuantity stock) {
        Intent intent = new Intent(this, SellActivity.class);
        intent.putExtra(SellActivity.EXTRA_TICKER, stock.getTicker());
        startActivity(intent);
    }
}
