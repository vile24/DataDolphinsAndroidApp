/*This file determines how each item is displayed in the RecyclerView.*/


package com.example.datadolphinsandroidapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datadolphinsandroidapp.database.StockWithQuantity;
import com.example.datadolphinsandroidapp.databinding.RecyclerItemStockBinding;

import java.util.function.Consumer;

public class PortfolioAdapter extends ListAdapter<StockWithQuantity, PortfolioAdapter.ViewHolder> {

    private final Consumer<StockWithQuantity> onSellClickListener;

    public PortfolioAdapter(Consumer<StockWithQuantity> onSellClickListener) {
        super(new DiffUtil.ItemCallback<StockWithQuantity>() {
            @Override
            public boolean areItemsTheSame(@NonNull StockWithQuantity oldItem, @NonNull StockWithQuantity newItem) {
                return oldItem.getTicker().equals(newItem.getTicker());
            }

            @Override
            public boolean areContentsTheSame(@NonNull StockWithQuantity oldItem, @NonNull StockWithQuantity newItem) {
                return oldItem.getQuantity() == newItem.getQuantity();
            }
        });
        this.onSellClickListener = onSellClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerItemStockBinding binding = RecyclerItemStockBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.bind(getItem(position), onSellClickListener);
//    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StockWithQuantity stock = getItem(position);
        Log.d("PortfolioAdapter", "Binding item at position " + position + ": Ticker = " + stock.getTicker() + ", Quantity = " + stock.getQuantity());
        holder.bind(stock, onSellClickListener);
    }





    static class ViewHolder extends RecyclerView.ViewHolder {

        private final RecyclerItemStockBinding binding;

        public ViewHolder(@NonNull RecyclerItemStockBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(StockWithQuantity stock, Consumer<StockWithQuantity> onSellClickListener) {
            binding.stockName.setText(stock.getTicker());
            binding.stockQuantity.setText(String.valueOf(stock.getQuantity()));
            binding.sellRecycler.setOnClickListener(v -> onSellClickListener.accept(stock));
        }
    }
}
