package com.ankk.market.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ankk.market.R;
import com.ankk.market.beans.Beansousproduitarticle;
import com.ankk.market.databinding.CardviewdisplaygridsousproduitBinding;

import java.util.ArrayList;
import java.util.List;

public class AdapterDisplayDetail extends RecyclerView.Adapter<AdapterDisplayDetail.ViewHolder> {


    // Attributes :
    private final Context context;
    private List<Beansousproduitarticle> donnee;



    public AdapterDisplayDetail(Context context) {
        this.context = context;
        donnee = new ArrayList<>();
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterDisplayDetail.ViewHolder holder,
                                 int position) {
        // Get DATA :
        holder.binder.textdisplayproduit.setText(donnee.get(position).getDetail());
        // Work on GRIDVIEW :
        if(donnee.get(position).getListe().size() > 3)
            holder.binder.gridviewdisplaysousproduit.getLayoutParams().height = (int)context.getResources().getDimension(R.dimen.artcleproduitgrid);
        holder.binder.gridviewdisplaysousproduit.setAdapter(
                new AdapterGridViewResumeArticle(context, donnee.get(position).getListe()));
    }


    @NonNull
    @Override
    public AdapterDisplayDetail.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                      int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new AdapterDisplayDetail.ViewHolder(DataBindingUtil.inflate(inflater,
                R.layout.cardviewdisplaygridsousproduit,
                parent,
                false));
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        CardviewdisplaygridsousproduitBinding binder;

        public ViewHolder(CardviewdisplaygridsousproduitBinding binder) {
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
