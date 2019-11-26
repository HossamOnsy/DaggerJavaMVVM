package com.sam.daggerjavamvvm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder> {
  public ArrayList<CatModel> data;
  public CatAdapter(ArrayList<CatModel> data){
    this.data = data;
  }

  @Override
  public CatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cat, parent, false);
    return new ViewHolder(rowItem);
  }

  @Override
  public void onBindViewHolder(CatAdapter.ViewHolder holder, int position) {

    CatModel cat = this.data.get(position);
    Glide.with(holder.itemView.getContext())
            .load(cat.getUrl())
            .into(holder.itemCatImageView);
  }

  @Override
  public int getItemCount() {
    return this.data.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    private ImageView itemCatImageView;

    public ViewHolder(View view) {
      super(view);
      this.itemCatImageView = view.findViewById(R.id.itemCatImageView);
    }


  }
}