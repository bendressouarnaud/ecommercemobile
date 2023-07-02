package com.ankk.market.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ankk.market.R;
import com.ankk.market.databinding.CardviewoffreBinding;
import com.ankk.market.mesenums.Modes;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.List;

public class AdapterOffre extends RecyclerView.Adapter<
        AdapterOffre.ProduitViewHolder> {

    // Attributes :
    private final Context context;
    private List<Object> donnee;
    AlertDialog alerdialogSynchro;
    String nom, prenom;
    Modes lemode;


    // methods :
    public AdapterOffre(Context context, Modes lemode) {
        this.context = context;
        donnee = new ArrayList<>();
        this.lemode = lemode;
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterOffre.ProduitViewHolder holder,
                                 int position) {
        // Get DATA :
        if(position==1) holder.binder.imgoffre.setImageDrawable(context.getResources().getDrawable(R.drawable.medicament, null));
        holder.binder.itemrestantoffre.setText(String.valueOf(position + 1) + " articles restants");
        holder.binder.prixoffre.setText(String.valueOf(position + 1) + " FCFA");
        /*BeanClientJournalier p = donnee.get(position);
        // Set montant :
        holder.binder.montanthisto.setText(
                NumberFormat.getInstance(Locale.FRENCH).format(p.getMontant()) + " FCFA"
        );

        holder.binder.titrehisto.setText("Paiement Taxe");
        holder.binder.datehisto.setText(p.getDates());
        holder.binder.heurehisto.setText(p.getHeure());
        //
        holder.binder.rapporteurhisto.setText(p.getUsr());
        holder.binder.clienthisto.setText(p.getClt());*/
    }


    @NonNull
    @Override
    public AdapterOffre.ProduitViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new AdapterOffre.ProduitViewHolder(DataBindingUtil.inflate(inflater,
                R.layout.cardviewoffre,
                parent,
                false));
    }


    // our ViewHolder :
    static class ProduitViewHolder extends RecyclerView.ViewHolder {
        CardviewoffreBinding binder;

        public ProduitViewHolder(CardviewoffreBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }


    @Override
    public int getItemCount() {
        return donnee.size();
    }

    public void addItems(Object data) {
        donnee.add(data);
        notifyItemInserted(donnee.size() - 1);
    }

    public void clearEverything() {
        int size = donnee.size();
        donnee.clear();
        notifyItemRangeRemoved(0, size);
    }
}