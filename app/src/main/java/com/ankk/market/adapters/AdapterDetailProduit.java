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
import com.ankk.market.beans.Beancategorie;
import com.ankk.market.beans.ProduitBean;
import com.ankk.market.databinding.CardviewdetailsousproduitBinding;
import com.ankk.market.databinding.CardviewlisteproduitBinding;

import java.util.ArrayList;
import java.util.List;

public class AdapterDetailProduit extends RecyclerView.Adapter<AdapterDetailProduit.DetailViewHolder> {


    // Attributes :
    private final Context context;
    private List<Beancategorie> donnee;



    public AdapterDetailProduit(Context context) {
        this.context = context;
        donnee = new ArrayList<>();
    }


    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder,
                                 int position) {
        // Get DATA :
        holder.binder.libsousproduit.setText(donnee.get(position).getSousproduit());

        // Set ACTION on 'VOIR TOUT' :
        holder.binder.libsousproduittout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(context, SousproduitActivity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // Set Mode : 2 -> SOUS-PRODUIT
                it.putExtra("mode",2);
                it.putExtra("lib", donnee.get(position).getSousproduit());
                context.startActivity(it);
            }
        });

        // Work on GRIDVIEW :
        holder.binder.gridview.setAdapter(new AdapterGridView(context, donnee.get(position).getDetails()));
    }


    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                 int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new DetailViewHolder(DataBindingUtil.inflate(inflater,
                R.layout.cardviewdetailsousproduit,
                parent,
                false));
    }


    static class DetailViewHolder extends RecyclerView.ViewHolder {
        CardviewdetailsousproduitBinding binder;

        public DetailViewHolder(CardviewdetailsousproduitBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }

    @Override
    public int getItemCount() {
        return donnee.size();
    }

    public void addItems(Beancategorie data) {
        // Limit DATA to three ITEM :
        if(donnee.size() < 3) {
            donnee.add(data);
            notifyItemInserted(donnee.size() - 1);
        }
    }

    public void clearEverything(){
        int size = donnee.size();
        donnee.clear();
        notifyItemRangeRemoved(0, size);
    }
}