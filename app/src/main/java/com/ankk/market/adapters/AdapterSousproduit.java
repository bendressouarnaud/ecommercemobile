package com.ankk.market.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ankk.market.DetailActivity;
import com.ankk.market.ProduitActivity;
import com.ankk.market.R;
import com.ankk.market.SousproduitActivity;
import com.ankk.market.beans.Beansousproduit;
import com.ankk.market.databinding.CardviewdisplaysousproduitBinding;
import com.ankk.market.databinding.CardviewoffreBinding;
import com.ankk.market.mesenums.Modes;
import com.ankk.market.models.Produit;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.List;

public class AdapterSousproduit extends RecyclerView.Adapter<
        AdapterSousproduit.SousproduitViewHolder> {

    // Attributes :
    private final Context context;
    private List<Beansousproduit> donnee;


    // methods :
    public AdapterSousproduit(Context context) {
        this.context = context;
        donnee = new ArrayList<>();
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterSousproduit.SousproduitViewHolder holder,
                                 int position) {
        // Set code here :
        holder.binder.cardviewdisplaysousprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, DetailActivity.class);
                it.putExtra("idspr", donnee.get(position).getIdspr());
                it.putExtra("libelle", donnee.get(position).getLibelle());
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(it);
            }
        });

        // Get DATA :
        // Change picture :
        Glide.with(context)
                .load("https://firebasestorage.googleapis.com/v0/b/gestionpanneaux.appspot.com/o/"+
                        donnee.get(position).getLienweb()+"?alt=media")
                .onlyRetrieveFromCache(false)
                .transition(DrawableTransitionOptions.withCrossFade(1000))
                .placeholder(R.drawable.ic_panier)
                .into(holder.binder.imgdisplayproduit);
        holder.binder.textlibproduit.setText(donnee.get(position).getLibelle());
    }


    @NonNull
    @Override
    public AdapterSousproduit.SousproduitViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new AdapterSousproduit.SousproduitViewHolder(DataBindingUtil.inflate(inflater,
                R.layout.cardviewdisplaysousproduit,
                parent,
                false));
    }


    // our ViewHolder :
    static class SousproduitViewHolder extends RecyclerView.ViewHolder {
        CardviewdisplaysousproduitBinding binder;

        public SousproduitViewHolder(CardviewdisplaysousproduitBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }


    @Override
    public int getItemCount() {
        return donnee.size();
    }

    public void addItems(Beansousproduit data) {
        donnee.add(data);
        notifyItemInserted(donnee.size() - 1);
    }

    public void clearEverything() {
        int size = donnee.size();
        donnee.clear();
        notifyItemRangeRemoved(0, size);
    }

}
