package com.ankk.market.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ankk.market.OpenApplication;
import com.ankk.market.R;
import com.ankk.market.beans.BeanCommande;
import com.ankk.market.beans.Beanarticlelive;
import com.ankk.market.databinding.CardviewarticleBinding;
import com.ankk.market.databinding.CardviewhistoriquecommandeBinding;
import com.ankk.market.models.Achat;
import com.ankk.market.repositories.AchatRepository;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterListCommande extends RecyclerView.Adapter<AdapterListCommande.CommandeViewHolder> {

    // A t t r i b u t e s   :
    private final Context context;
    private List<BeanCommande> donnee;
    OpenApplication app;


    // M e t h o d s   :
    public AdapterListCommande(Context context) {
        this.context = context;
        donnee = new ArrayList<>();
        app = (OpenApplication) context.getApplicationContext();
    }

    @NonNull
    @Override
    public AdapterListCommande.CommandeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new AdapterListCommande.CommandeViewHolder(DataBindingUtil.inflate(inflater,
                R.layout.cardviewhistoriquecommande,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListCommande.CommandeViewHolder holder,
                                 int position) {
        // Hide
        holder.binder.histocomtextdate.setText(donnee.get(position).getDates());
        holder.binder.histocomtextheure.setText(donnee.get(position).getHeure());
        holder.binder.histocomtextcmd.setText( String.valueOf(donnee.get(position).getTot()) + " article(s) commandé(s)");
        holder.binder.histocomprixcmd.setText( NumberFormat.getInstance(Locale.FRENCH).format(donnee.get(position).getMontant()) + " FCFA");
    }

    static class CommandeViewHolder extends RecyclerView.ViewHolder {
        CardviewhistoriquecommandeBinding binder;

        public CommandeViewHolder(CardviewhistoriquecommandeBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }

    @Override
    public int getItemCount() {
        return donnee.size();
    }

    public void addItems(BeanCommande data) {
        donnee.add(data);
        notifyItemInserted(donnee.size() - 1);
    }

    public void clearEverything(){
        int size = donnee.size();
        donnee.clear();
        notifyItemRangeRemoved(0, size);
    }
}