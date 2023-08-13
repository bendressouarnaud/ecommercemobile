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
import com.ankk.market.beans.Beansousproduitarticle;
import com.ankk.market.databinding.CardviewdetailsousproduitBinding;
import com.ankk.market.databinding.CardviewdisplaygridsousproduitBinding;

import java.util.ArrayList;
import java.util.List;

public class AdapterDisplayProduit extends RecyclerView.Adapter<AdapterDisplayProduit.DisplayViewHolder> {


    // Attributes :
    private final Context context;
    private List<Beansousproduitarticle> donnee;



    public AdapterDisplayProduit(Context context) {
        this.context = context;
        donnee = new ArrayList<>();
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterDisplayProduit.DisplayViewHolder holder,
                                 int position) {
        // Get DATA :
        holder.binder.textdisplayproduit.setText(donnee.get(position).getDetail());

        // Set ACTION on 'VOIR TOUT' :
        holder.binder.textproduittout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(context, SousproduitActivity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // Set Mode : 2 -> SOUS-PRODUIT
                it.putExtra("mode",2);
                it.putExtra("lib", donnee.get(position).getDetail());
                context.startActivity(it);
            }
        });

        // Work on GRIDVIEW :
        if(donnee.get(position).getListe().size() > 3)
            holder.binder.gridviewdisplaysousproduit.getLayoutParams().height = (int)context.getResources().getDimension(R.dimen.artcleproduitgrid);
        holder.binder.gridviewdisplaysousproduit.setAdapter(
                new AdapterGridViewResumeArticle(context, donnee.get(position).getListe()));
    }


    @NonNull
    @Override
    public AdapterDisplayProduit.DisplayViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                    int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new AdapterDisplayProduit.DisplayViewHolder(DataBindingUtil.inflate(inflater,
                R.layout.cardviewdisplaygridsousproduit,
                parent,
                false));
    }


    static class DisplayViewHolder extends RecyclerView.ViewHolder {
        CardviewdisplaygridsousproduitBinding binder;

        public DisplayViewHolder(CardviewdisplaygridsousproduitBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }

    @Override
    public int getItemCount() {
        return donnee.size();
    }

    public void addItems(Beansousproduitarticle data) {
        donnee.add(data);
        notifyItemInserted(donnee.size() - 1);
    }

    public void clearEverything(){
        int size = donnee.size();
        donnee.clear();
        notifyItemRangeRemoved(0, size);
    }
}