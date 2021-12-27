package com.tiendat.voliotest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tiendat.voliotest.databinding.ItemImageBinding;
import com.tiendat.voliotest.databinding.ItemTypeTwoBinding;

import java.util.ArrayList;

public class ItemTypeFourAdapter extends RecyclerView.Adapter<ItemTypeFourAdapter.ViewHolder> {

    Context context;
    ArrayList<String> imageURLs;
    int parentWidth = 0;

    public ItemTypeFourAdapter(Context context , ArrayList<String> imageURLs , int parentWidth){
        this.context = context;
        this.imageURLs = imageURLs;
        this.parentWidth = parentWidth;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImageBinding binding = ItemImageBinding.inflate(LayoutInflater.from(parent.getContext()) , parent , false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(parentWidth / 3 , parentWidth / 4);
        params.setMarginEnd(20);
        holder.imageView.setLayoutParams(params);
        Glide.with(context).load(imageURLs.get(position)).diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageURLs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(ItemImageBinding binding) {
            super(binding.getRoot());
            imageView = binding.imageView;
        }
    }
}
