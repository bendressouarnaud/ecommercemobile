package com.ankk.market.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ankk.market.R;
import com.ankk.market.beans.ProduitBean;
import com.ankk.market.databinding.CardviewdetailsousproduitBinding;
import com.ankk.market.databinding.CardviewlisteproduitBinding;

import java.util.ArrayList;
import java.util.List;

public class AdapterDetailProduit extends RecyclerView.Adapter<AdapterDetailProduit.DetailViewHolder> {


    // Attributes :
    private final Context context;
    private List<String> donnee;



    public AdapterDetailProduit(Context context) {
        this.context = context;
        donnee = new ArrayList<>();
    }


    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder,
                                 int position) {
        // Get DATA :
        holder.binder.libsousproduit.setText(donnee.get(position));
        // Work on GRIDVIEW :
        List<ProduitBean> lt = getListData();
        if(position == 1)
            holder.binder.gridview.setAdapter(new AdapterGridView(context, lt.subList(0,2)));
        else holder.binder.gridview.setAdapter(new AdapterGridView(context, lt));
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

    public void addItems(String data) {
        donnee.add(data);
        notifyItemInserted(donnee.size() - 1);
    }

    private  List<ProduitBean> getListData() {
        List<ProduitBean> list = new ArrayList<>();
        ProduitBean Mangue = new ProduitBean("https://firebasestorage.googleapis.com/v0/b/gestionpanneaux.appspot.com/o/27cdea7a-b472-4459-8dca-71721f9278c9.jpg?alt=media", "Mangue");
        ProduitBean Raisin = new ProduitBean("https://firebasestorage.googleapis.com/v0/b/gestionpanneaux.appspot.com/o/27cdea7a-b472-4459-8dca-71721f9278c9.jpg?alt=media", "Raisin");
        ProduitBean Avocat = new ProduitBean("https://firebasestorage.googleapis.com/v0/b/gestionpanneaux.appspot.com/o/27cdea7a-b472-4459-8dca-71721f9278c9.jpg?alt=media", "Avocat");
        ProduitBean Orange = new ProduitBean("https://firebasestorage.googleapis.com/v0/b/gestionpanneaux.appspot.com/o/27cdea7a-b472-4459-8dca-71721f9278c9.jpg?alt=media", "Orange");

        list.add(Mangue);
        list.add(Raisin);
        list.add(Avocat);
        list.add(Orange);

        return list;
    }
}