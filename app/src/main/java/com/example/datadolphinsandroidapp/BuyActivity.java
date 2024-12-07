package com.example.datadolphinsandroidapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.datadolphinsandroidapp.database.StockRepository;
import com.example.datadolphinsandroidapp.database.entities.Stock;
import com.example.datadolphinsandroidapp.databinding.ActivityBuyBinding;

import java.util.Locale;

public class BuyActivity extends AppCompatActivity {

    public static final String EXTRA_TICKER = "com.example.datadolphinsandroidapp.EXTRA_TICKER";
    private ActivityBuyBinding binding;
    private StockRepository repository;

    public static final String TAG = "DAC_STOCK";

    String ticker = "";
    int mQuantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the ticker passed from MainActivity
        Intent intent = getIntent();
        ticker = intent.getStringExtra(EXTRA_TICKER);

        if (ticker != null) {
            Log.d(TAG, "Ticker received: " + ticker);
            // You can now use `ticker` to perform database queries or update UI
            binding.stock.setText(ticker); // Example: prepopulate stock field
        }

        getInformationFromDisplay();
//        updateDisplay();

        repository = StockRepository.getRepository(getApplication());

        binding.buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyTicker();
            }
        });
    }
//    private void verifyTicker() {
//        String ticker = binding.stock.getText().toString().trim();
//        if (ticker.isEmpty()) {
//            toastMaker("Ticker should not be blank.");
//            return;
//        }
//        // Get quantity input
//        try {
//            mQuantity = Integer.parseInt(binding.quantityInputEditText.getText().toString().trim());
//        } catch (NumberFormatException e) {
//            toastMaker("Please enter a valid quantity.");
//            return;
//        }
//
//        // Query the database for the ticker
//        repository.getStockByTicker(ticker).observe(this, stock -> {
//            if (stock != null) {
//                double cost = stock.getCost();
//                double total = cost * mQuantity;
//
//                binding.costPlaceholder.setText(String.format(Locale.US, "%.2f", total));
//            } else {
//                toastMaker(String.format("%s is not a valid ticker", ticker));
//                // set cursor to the beginning
//                binding.tickerInputEditText.setSelection(0);
//            }
//        });
//
////        // tell database go find this user...database look for user if finds it, return it back
////        LiveData<Stock> userObserver = repository.getStockByTicker(ticker);
////        // Observer is going to wait and observe the call the db and wait for something to come back
////        stockObserver.observe(this, stock -> {
////            if (stock != null) {
////                String inputTicker = binding.tickerInEditText.getText().toString();
////                if (inputTicker.equals(stock.getTicker())) {
////                    startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), stock.getStockId()));
////                } else {
////                    toastMaker("Invalid ticker");
////                    binding.tickerInEditText.setSelection(0);
////                }
////            } else {
////                toastMaker(String.format("%s is not a valid ticker, ", ticker));
////                binding.tickerInEditText.setSelection(0);
////            }
////        });
//    }
//
//

    private void verifyTicker() {
        // Get ticker input from activity_buy.xml
        String ticker = binding.tickerInputEditText.getText().toString().trim();

        // Validate ticker input
        if (ticker.isEmpty()) {
            toastMaker("Ticker should not be blank.");
            return;
        }

        // Get quantity input
        try {
            mQuantity = Integer.parseInt(binding.quantityInputEditText.getText().toString().trim());
        } catch (NumberFormatException e) {
            toastMaker("Please enter a valid quantity.");
            return;
        }

        // Query the database for the ticker
        repository.getStockByTicker(ticker).observe(this, stock -> {
            if (stock != null) {
                double cost = stock.getCost();
                double total = cost * mQuantity;

                // Update the cost placeholder with the calculated total
                binding.costPlaceholder.setText(String.format(Locale.US, "%.2f", total));
            } else {
                toastMaker(String.format("%s is not a valid ticker", ticker));
                // Reset cursor to the beginning of the input
                binding.tickerInputEditText.setSelection(0);
            }
        });
    }

    // this is just for testing, putting stock in the cost placeholder.
//    public void updateDisplay() {
//        String currentInfo = binding.companyTickerEditText.getText().toString();
//        Log.d(TAG,"Current info: "+currentInfo);
//        String newDisplay = String.format(Locale.US, "%s", mTicker);
//        binding.costPlaceholder.setText(newDisplay);
//    }

    private void getInformationFromDisplay() {
        // Grab user "company ticker" input and store in mTicker
        ticker = binding.stock.getText().toString(); // Fix: added mTicker

        // mQuantity needs to try/catch because input might be a string instead of a number
        try {
            mQuantity = Integer.parseInt(binding.quantityInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.i(TAG, "Error reading value from quantity edit text.");
        }
    }

    // TODO: read in user input, compare and get cost

    // TODO: read in user input for quantity

    // When there's buy activity, create a transaction that have Ticker, quantity, cost
    // for display you need total.

    public void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // creating an Intent to navigate from MainActivity to BuyActivity.
    public static Intent buyIntentFactory(Context context) {
        return new Intent(context, BuyActivity.class);
    }


}