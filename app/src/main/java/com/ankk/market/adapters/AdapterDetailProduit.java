package com.ankk.market.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ankk.market.R;
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
        donnee.add(data);
        notifyItemInserted(donnee.size() - 1);
    }

    public void clearEverything(){
        int size = donnee.size();
        donnee.clear();
        notifyItemRangeRemoved(0, size);
    }
}