package com.ankk.market.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ankk.market.OpenApplication;
import com.ankk.market.R;
import com.ankk.market.beans.BeanCommentaireContenu;
import com.ankk.market.beans.Beanresumearticle;
import com.ankk.market.beans.Commentaire;
import com.ankk.market.databinding.CardviewaffichagecommentaireBinding;
import com.ankk.market.databinding.CardviewarticlenotificationBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterCommentaireArticle extends RecyclerView.Adapter<AdapterCommentaireArticle.CommentaireViewHolder> {

    // A t t r i b u t e s   :
    private final Context context;
    private List<BeanCommentaireContenu> donnee;
    OpenApplication app;


    // M e t h o d s   :
    public AdapterCommentaireArticle(Context context) {
        this.context = context;
        donnee = new ArrayList<>();
        app = (OpenApplication) context.getApplicationContext();
    }

    @NonNull
    @Override
    public AdapterCommentaireArticle.CommentaireViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new AdapterCommentaireArticle.CommentaireViewHolder(DataBindingUtil.inflate(inflater,
                R.layout.cardviewaffichagecommentaire,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCommentaireArticle.CommentaireViewHolder holder,
                                 int position) {
        // Set NOTE
        switch (donnee.get(position).getNote()){
            case 1:
                holder.binder.cmtun.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                //
                holder.binder.cmtdeux.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
                holder.binder.cmttroix.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
                holder.binder.cmtquatre.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
                holder.binder.cmtcinq.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
                break;

            case 2:
                holder.binder.cmtun.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                holder.binder.cmtdeux.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                //
                holder.binder.cmttroix.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
                holder.binder.cmtquatre.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
                holder.binder.cmtcinq.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
                break;

            case 3:
                holder.binder.cmtun.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                holder.binder.cmtdeux.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                holder.binder.cmttroix.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                //
                holder.binder.cmtquatre.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
                holder.binder.cmtcinq.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
                break;

            case 4:
                holder.binder.cmtun.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                holder.binder.cmtdeux.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                holder.binder.cmttroix.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                holder.binder.cmtquatre.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                //
                holder.binder.cmtcinq.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
                break;

            case 5:
                holder.binder.cmtun.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                holder.binder.cmtdeux.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                holder.binder.cmttroix.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                holder.binder.cmtquatre.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                holder.binder.cmtcinq.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                break;
        }
        // Date
        holder.binder.textdatecomment.setText(donnee.get(position).getDates());
        holder.binder.textappreciationcomment.setText("");
        holder.binder.textcontenucomment.setText(donnee.get(position).getCommentaire());
        holder.binder.textclientcomment.setText( donnee.get(position).getClient());
    }

    static class CommentaireViewHolder extends RecyclerView.ViewHolder {
        CardviewaffichagecommentaireBinding binder;

        public CommentaireViewHolder(CardviewaffichagecommentaireBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }

    @Override
    public int getItemCount() {
        return donnee.size();
    }

    public void addItems(BeanCommentaireContenu data) {
        donnee.add(data);
        notifyItemInserted(donnee.size() - 1);
    }

}
