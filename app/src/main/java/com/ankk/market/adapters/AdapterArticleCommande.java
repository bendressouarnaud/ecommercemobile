package com.ankk.market.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ankk.market.HistoriqueCommande;
import com.ankk.market.OpenApplication;
import com.ankk.market.R;
import com.ankk.market.beans.BeanCommandeProjection;
import com.ankk.market.beans.Beanresumearticle;
import com.ankk.market.databinding.CardviewarticlenotificationBinding;
import com.ankk.market.databinding.CardviewhistoriquecommandeBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterArticleCommande extends RecyclerView.Adapter<AdapterArticleCommande.ArticleViewHolder> {

    // A t t r i b u t e s   :
    private final Context context;
    private List<Beanresumearticle> donnee;
    OpenApplication app;


    // M e t h o d s   :
    public AdapterArticleCommande(Context context) {
        this.context = context;
        donnee = new ArrayList<>();
        app = (OpenApplication) context.getApplicationContext();
    }

    @NonNull
    @Override
    public AdapterArticleCommande.ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new AdapterArticleCommande.ArticleViewHolder(DataBindingUtil.inflate(inflater,
                R.layout.cardviewarticlenotification,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterArticleCommande.ArticleViewHolder holder,
                                 int position) {
        //
        Glide.with(context)
                .load("https://firebasestorage.googleapis.com/v0/b/gestionpanneaux.appspot.com/o/"+
                        donnee.get(position).getLienweb()+"?alt=media")
                .onlyRetrieveFromCache(false)
                .transition(DrawableTransitionOptions.withCrossFade(1000))
                .placeholder(R.drawable.ic_panier)
                .into(holder.binder.imgnotif);

        //
        holder.binder.libellenotif.setText(donnee.get(position).getLibelle());
        holder.binder.prixnotif.setText( NumberFormat.getInstance(Locale.FRENCH).format(donnee.get(position).getPrix())+" FCFA");
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        CardviewarticlenotificationBinding binder;

        public ArticleViewHolder(CardviewarticlenotificationBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }

    @Override
    public int getItemCount() {
        return donnee.size();
    }

    public void addItems(Beanresumearticle data) {
        donnee.add(data);
        notifyItemInserted(donnee.size() - 1);
    }

}