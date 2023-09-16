package com.ankk.market.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ankk.market.ProduitActivity;
import com.ankk.market.R;
import com.ankk.market.SousproduitActivity;
import com.ankk.market.databinding.CardviewoffreBinding;
import com.ankk.market.mesenums.Modes;
import com.ankk.market.models.Produit;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.facebook.shimmer.Shimmer;

import java.util.ArrayList;
import java.util.List;

public class AdapterProduit extends RecyclerView.Adapter<
        AdapterProduit.OffreViewHolder> {

    // Attributes :
    private final Context context;
    private List<Produit> donnee;
    AlertDialog alerdialogSynchro;
    String nom, prenom;
    Modes lemode;


    // methods :
    public AdapterProduit(Context context, Modes lemode) {
        this.context = context;
        donnee = new ArrayList<>();
        this.lemode = lemode;
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterProduit.OffreViewHolder holder,
                                 int position) {

        // Set code here :
        holder.binder.cardviewoffremain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, ProduitActivity.class);
                it.putExtra("idprd", donnee.get(position).getIdprd());
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(it);
            }
        });

        // Get DATA :
        // Hide elements :
        holder.binder.prixoffre.setVisibility(View.GONE);
        holder.binder.itemrestantoffre.setVisibility(View.GONE);
        holder.binder.itemrestantoffre.setVisibility(View.GONE);
        holder.binder.baroffre.setVisibility(View.GONE);
        holder.binder.textpourcentage.setVisibility(View.GONE);
        // increase IMG width :
        holder.binder.constraintmaincrdimg.getLayoutParams().width = (int) context.getResources().getDimension(
                R.dimen.productimg);
        // Change picture :
        Glide.with(context)
                .load("https://firebasestorage.googleapis.com/v0/b/gestionpanneaux.appspot.com/o/"+
                        donnee.get(position).getLienweb()+"?alt=media")
                .onlyRetrieveFromCache(false)
                .transition(DrawableTransitionOptions.withCrossFade(1000))
                //.placeholder(R.drawable.ic_panier)
                .into(holder.binder.imgoffre);
        holder.binder.liboffre.setText(donnee.get(position).getLibelle());

        /*switch (position){
            case 0:
                Glide.with(context)
                        .load("https://firebasestorage.googleapis.com/v0/b/gestionpanneaux.appspot.com/o/27cdea7a-b472-4459-8dca-71721f9278c9.jpg?alt=media")
                        .onlyRetrieveFromCache(false)
                        .transition(DrawableTransitionOptions.withCrossFade(1000))
                        .placeholder(R.drawable.ic_panier)
                        .into(holder.binder.imgoffre);
                //holder.binder.imgoffre.setImageDrawable(context.getResources().getDrawable(R.drawable.informatique, null));
                holder.binder.liboffre.setText("Informatique");
                break;

            case 1:
                Glide.with(context)
                        .load("https://firebasestorage.googleapis.com/v0/b/gestionpanneaux.appspot.com/o/c0876346-6551-440b-92e3-36d266920904.png?alt=media")
                        .onlyRetrieveFromCache(false)
                        .transition(DrawableTransitionOptions.withCrossFade(700))
                        .into(holder.binder.imgoffre);
                //holder.binder.imgoffre.setImageDrawable(context.getResources().getDrawable(R.drawable.dietetique, null));
                holder.binder.liboffre.setText("Diététique");
                break;

            case 2:
                Glide.with(context)
                        .load("https://firebasestorage.googleapis.com/v0/b/gestionpanneaux.appspot.com/o/f7f06bc6-bbb6-42e1-b12e-2bf1313296d2.jpg?alt=media")
                        .onlyRetrieveFromCache(false)
                        .transition(DrawableTransitionOptions.withCrossFade(5000))
                        .into(holder.binder.imgoffre);
                //holder.binder.imgoffre.setImageDrawable(context.getResources().getDrawable(R.drawable.chemise, null));
                holder.binder.liboffre.setText("Vêtement");
                break;

            case 3:
                holder.binder.imgoffre.setImageDrawable(context.getResources().getDrawable(R.drawable.sante, null));
                holder.binder.liboffre.setText("Santé");
                break;

            case 4:
                holder.binder.imgoffre.setImageDrawable(context.getResources().getDrawable(R.drawable.services, null));
                holder.binder.liboffre.setText("Services");
                break;

            default:
                holder.binder.imgoffre.setImageDrawable(context.getResources().getDrawable(R.drawable.sante, null));
                holder.binder.liboffre.setText("SANTE");
                break;
        }
        */
    }


    @NonNull
    @Override
    public AdapterProduit.OffreViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new AdapterProduit.OffreViewHolder(DataBindingUtil.inflate(inflater,
                R.layout.cardviewoffre,
                parent,
                false));
    }


    // our ViewHolder :
    static class OffreViewHolder extends RecyclerView.ViewHolder {
        CardviewoffreBinding binder;

        public OffreViewHolder(CardviewoffreBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }


    @Override
    public int getItemCount() {
        return donnee.size();
    }

    public void addItems(Produit data) {
        donnee.add(data);
        notifyItemInserted(donnee.size() - 1);
    }

    public void clearEverything() {
        int size = donnee.size();
        donnee.clear();
        notifyItemRangeRemoved(0, size);
    }

    // Display DETAIL :
    private void displayCatgories(){
        Intent it = new Intent(context, SousproduitActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(it);
    }
}