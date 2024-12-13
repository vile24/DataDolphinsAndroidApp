/**
 * displays user stock portfolio
 */
package com.example.datadolphinsandroidapp;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.datadolphinsandroidapp.viewHolders.PortfolioAdapter;
import com.example.datadolphinsandroidapp.viewHolders.PortfolioViewModel;

public class PortfolioActivity extends AppCompatActivity {

    private RecyclerView stocksRecyclerView;
    private PortfolioAdapter adapter;
    private PortfolioViewModel portfolioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio); //layout for activity

        //initialize RecyclerView
        stocksRecyclerView = findViewById(R.id.stockDisplayRecyclerView);
        stocksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initialize adapter
        adapter = new PortfolioAdapter(new PortfolioAdapter.PortfolioLogDiff());
        stocksRecyclerView.setAdapter(adapter);

        //initialize ViewModel
        portfolioViewModel = new ViewModelProvider(this).get(PortfolioViewModel.class);

        //observe list of stocks from viewmodel
        portfolioViewModel.getAllLogsById().observe(this, stocks -> {
            // Submit the list to the adapter
            adapter.submitList(stocks);
        });

        // onclick listener
        Button sellButton = findViewById(R.id.sellButton);
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verify sell button click event
                Log.d("PortfolioActivity", "Sell Button clicked");
                //show toast
                Toast.makeText(PortfolioActivity.this, "Navigating to Sell Page", Toast.LENGTH_SHORT).show();

                //intent to got to sellactivity
                Intent intent = SellActivity.sellIntentFactory(PortfolioActivity.this);
                startActivity(intent);
            }
        });
    }
}
