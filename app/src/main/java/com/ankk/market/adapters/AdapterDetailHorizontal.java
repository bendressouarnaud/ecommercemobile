package com.ankk.market.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ankk.market.R;
import com.ankk.market.SousproduitActivity;
import com.ankk.market.beans.Beansousproduit;
import com.ankk.market.beans.Detail;
import com.ankk.market.databinding.CardviewdisplayarticleBinding;
import com.ankk.market.databinding.CardviewdisplaysousproduitBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.List;

public class AdapterDetailHorizontal extends RecyclerView.Adapter<
        AdapterDetailHorizontal.ViewHolder> {

    // Attributes :
    private final Context context;
    private List<Detail> donnee;


    // methods :
    public AdapterDetailHorizontal(Context context) {
        this.context = context;
        donnee = new ArrayList<>();
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterDetailHorizontal.ViewHolder holder,
                                 int position) {
        // Set code here :
        holder.binder.cardviewdisplaydets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, SousproduitActivity.class);
                //it.putExtra("id", detail);
                it.putExtra("id", donnee.get(position).getIddet());
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
                .into(holder.binder.imgdisplaydetails);
        holder.binder.textlibdetail.setText(donnee.get(position).getLibelle());
    }


    @NonNull
    @Override
    public AdapterDetailHorizontal.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                       int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new AdapterDetailHorizontal.ViewHolder(DataBindingUtil.inflate(inflater,
                R.layout.cardviewdisplayarticle,
                parent,
                false));
    }


    // our ViewHolder :
    static class ViewHolder extends RecyclerView.ViewHolder {
        CardviewdisplayarticleBinding binder;

        public ViewHolder(CardviewdisplayarticleBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }


    @Override
    public int getItemCount() {
        return donnee.size();
    }

    public void addItems(Detail data) {
        donnee.add(data);
        notifyItemInserted(donnee.size() - 1);
    }

    public void clearEverything() {
        int size = donnee.size();
        donnee.clear();
        notifyItemRangeRemoved(0, size);
    }

}