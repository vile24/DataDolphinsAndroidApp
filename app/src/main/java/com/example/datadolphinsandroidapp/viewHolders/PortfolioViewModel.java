/**
 * Manages data for Portfolio
 */
package com.example.datadolphinsandroidapp.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.datadolphinsandroidapp.database.StockRepository;
import com.example.datadolphinsandroidapp.database.entities.Stock;

import java.util.List;

public class PortfolioViewModel extends AndroidViewModel {

    private final StockRepository repository;
    private final LiveData<List<Stock>> allStocks;

    //constructor
    public PortfolioViewModel(Application application) {
        super(application);
        repository = StockRepository.getRepository(application);
        allStocks = repository.getAllStocks();
    }
    //access to LiveData from list of stocks
    public LiveData<List<Stock>> getAllLogsById() {
        return allStocks;
    }
}
