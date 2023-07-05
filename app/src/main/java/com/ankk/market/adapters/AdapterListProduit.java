package com.ankk.market.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ankk.market.R;
import com.ankk.market.databinding.CardviewlisteproduitBinding;
import com.ankk.market.databinding.CardviewoffreBinding;
import com.ankk.market.mesenums.Modes;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.List;

public class AdapterListProduit extends RecyclerView.Adapter<AdapterListProduit.ListeViewHolder> {


    // Attributes :
    private final Context context;
    private List<String> donnee;



    public AdapterListProduit(Context context) {
        this.context = context;
        donnee = new ArrayList<>();
    }


    @Override
    public void onBindViewHolder(@NonNull ListeViewHolder holder,
                                 int position) {
        // Get DATA :
        holder.binder.libproduit.setText(donnee.get(position));
    }


    @NonNull
    @Override
    public ListeViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                           int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new ListeViewHolder(DataBindingUtil.inflate(inflater,
                R.layout.cardviewlisteproduit,
                parent,
                false));
    }


    static class ListeViewHolder extends RecyclerView.ViewHolder {
        CardviewlisteproduitBinding binder;

        public ListeViewHolder(CardviewlisteproduitBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }

    @Override
    public int getItemCount() {
        return donnee.size();
    }

    public void addItems(String data) {
        donnee.add(data);
        notifyItemInserted(donnee.size() - 1);
    }
}
