package com.ankk.market.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ankk.market.R;
import com.ankk.market.beans.Beanarticlelive;
import com.ankk.market.databinding.CardviewarticleBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.List;

public class AdapterListPanier extends RecyclerView.Adapter<AdapterListPanier.PanierViewHolder> {

    // A t t r i b u t e s   :
    private final Context context;
    private List<Beanarticlelive> donnee;


    // M e t h o d s   :
    public AdapterListPanier(Context context) {
        this.context = context;
        donnee = new ArrayList<>();
    }

    @NonNull
    @Override
    public AdapterListPanier.PanierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new AdapterListPanier.PanierViewHolder(DataBindingUtil.inflate(inflater,
                R.layout.cardviewarticle,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListPanier.PanierViewHolder holder,
        int position) {

        // Get DATA :
        Glide.with(context)
                .load("https://firebasestorage.googleapis.com/v0/b/gestionpanneaux.appspot.com/o/"+
                        donnee.get(position).getLienweb()+"?alt=media")
                .onlyRetrieveFromCache(false)
                .transition(DrawableTransitionOptions.withCrossFade(1000))
                //.placeholder(R.drawable.ic_panier)
                .into(holder.binder.imgarticle);
        holder.binder.libellearticle.setText(donnee.get(position).getLibelle());
        if(donnee.get(position).getReduction() == 0){
            holder.binder.prixpromotionarticle.setVisibility(View.INVISIBLE);
            holder.binder.articlepourcentage.setVisibility(View.INVISIBLE);
        }

        // Quantité article :
        holder.binder.quantitearticle.setText( String.valueOf(donnee.get(position).getArticlereserve()) );

        // Hide
        holder.binder.textalerte.setVisibility(View.INVISIBLE);
        holder.binder.articlebut.setVisibility(View.GONE);
        holder.binder.articlebutplus.setVisibility(View.GONE);
        holder.binder.articlebutmoins.setVisibility(View.GONE);

        // Display :
        holder.binder.imgdeletearticle.setVisibility(View.VISIBLE);
        holder.binder.textsupprimer.setVisibility(View.VISIBLE);
    }

    static class PanierViewHolder extends RecyclerView.ViewHolder {
        CardviewarticleBinding binder;

        public PanierViewHolder(CardviewarticleBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }

    @Override
    public int getItemCount() {
        return donnee.size();
    }

    public void addItems(Beanarticlelive data) {
        donnee.add(data);
        notifyItemInserted(donnee.size() - 1);
    }

    public void clearEverything(){
        int size = donnee.size();
        donnee.clear();
        notifyItemRangeRemoved(0, size);
    }
}
