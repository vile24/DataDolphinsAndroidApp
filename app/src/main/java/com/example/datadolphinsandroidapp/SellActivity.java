package com.example.datadolphinsandroidapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datadolphinsandroidapp.database.StockRepository;
import com.example.datadolphinsandroidapp.database.entities.Stock;
import com.example.datadolphinsandroidapp.database.entities.Transaction;
import com.example.datadolphinsandroidapp.databinding.ActivitySellBinding;

public class SellActivity extends AppCompatActivity {
    // Key to get the ticker
    public static final String EXTRA_TICKER = "com.example.datadolphinsandroidapp.EXTRA_TICKER";
    private ActivitySellBinding binding;
    private StockRepository repository;
    public static final String TAG = "DAC_STOCK";

    double total = 0.0;
    double balance = 10000;
    Stock stock;
    Transaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initializes the binding object for activity_sell.xml layout.
        binding = ActivitySellBinding.inflate(getLayoutInflater());
        // Sets the root view of the binding object - inflated layout (activity_sell.xml) as the content view
        setContentView(binding.getRoot());

        // This retrieves the Intent object that started the current activity.
        // Intents are used to pass data between activities in Android.
        Intent intent = getIntent();
        transaction = new Transaction(999, "TSLA", 100, 500, 0);
        // set repository
        repository = StockRepository.getRepository(getApplication());
        // get the stock from stock_table using transaction stockId
        repository.getStockByTicker(transaction.getTicker()).observe(this, stock -> {
            if (stock != null) {
                this.stock = stock;
                // set the ticker in the UI
                binding.sellTickerInputEditText.setText(stock.getTicker());
                Log.d(TAG, "Stock retrieved from stock database: " + stock);
            } else {
                Log.d(TAG, "No stocks found in database.");
            }
        });

        // set quantity
        binding.availableSharePlaceholder.setText(String.valueOf(transaction.getQuantity()));

        // TODO: get the real transaction

        // Allow the user to input the quantity of the stock they want to sell
        binding.sellQuantityInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0)
                    verifyQuantity();
            }
        });

        // TODO: delete record if sell all or
        //  calculate diff if partial sell and update qty in transaction db and update user balance
        // TODO: update user balance

        binding.sellButton.setOnClickListener(v -> {
            int quantity = Integer.parseInt(binding.sellQuantityInputEditText.getText().toString().trim());
            if (quantity > 0 &&  quantity < transaction.getQuantity()) {
                //  calculate diff if partial sell and TODO: update qty in transaction db
                int qtyDiff = transaction.getQuantity() - quantity;
                total = quantity * stock.getCost();


               // repository = StockRepository.getRepository(getApplication());
                stock = repository.getStockByTicker(stock.getTicker()).getValue(); // Example query



                if (stock != null && transaction.getQuantity() >= quantity) {
                    // TODO: insert qtyDiff in transaction qty column

//                    stock.setQuantity(qtyDiff);
//                    repository.updateStock(stock);

                    Log.d(TAG, "Stock sold. Remaining quantity: " + qtyDiff);
                    finish(); // Close the activity
                } else {
                    Log.e(TAG, "Invalid quantity or stock not found.");
                }
            } else {
                Log.e(TAG, "Quantity must be greater than 0.");
            }
        });


        //getInformationFromDisplay();
        // updateDisplay();



        // Trigger database initialization. run a query so it is created immediately
        repository.getAllStocks().observe(this, stocks -> {
            if (stocks != null && !stocks.isEmpty()) {
                Log.d(TAG, "Stocks retrieved from database: " + stocks.size());
                for (Stock stock : stocks) {
                    Log.d(TAG, "Stock: " + stock.getTicker() + ", " + stock.getCompany() + ", " + stock.getCost());
                }
            } else {
                Log.d(TAG, "No stocks found in database.");
            }
        });

    }

    private void verifyQuantity() {
        // Get quantity input
        try {
            if (transaction.getQuantity() != 0 ) {
                int quantity = Integer.parseInt(binding.sellQuantityInputEditText.getText().toString().trim());
                if (quantity > transaction.getQuantity()) {
                    Toast.makeText(SellActivity.this, "Your quantity is larger than what you currently have.", Toast.LENGTH_LONG).show();
                }
                else {
                    double total = quantity * stock.getCost();
                    String totalStr = "$" + String.valueOf(total);
                    binding.sellCostPlaceHolder.setText(totalStr);
                }
            }
        } catch (NumberFormatException e) {
            Toast.makeText(SellActivity.this, "Please enter a valid quantity.", Toast.LENGTH_LONG).show();
        }

    }


    // creating an Intent to navigate from MainActivity to SellActivity.
    public static Intent sellIntentFactory(Context context) {
        return new Intent(context, SellActivity.class);
    }


}
