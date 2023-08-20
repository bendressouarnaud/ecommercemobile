package com.ankk.market.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ankk.market.ArticleActivity;
import com.ankk.market.R;
import com.ankk.market.beans.Beanarticledetail;
import com.ankk.market.beans.Beanresumearticle;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

public class AdapterGridViewPromotionArticle extends BaseAdapter {

    private List<Beanarticledetail> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    final int limitProduitName = 10;

    public AdapterGridViewPromotionArticle(Context aContext, List<Beanarticledetail> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterGridViewPromotionArticle.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.cardviewarticlepromotion, null);
            holder = new AdapterGridViewPromotionArticle.ViewHolder();
            holder.imgpromart = (ImageView) convertView.findViewById(R.id.imgpromart);
            holder.libpromart = (TextView) convertView.findViewById(R.id.libpromart);
            holder.prixpromart = (TextView) convertView.findViewById(R.id.prixpromart);
            holder.itempromrestart = (TextView) convertView.findViewById(R.id.itempromrestart);
            holder.textprompourcentage = (TextView) convertView.findViewById(R.id.textprompourcentage);
            holder.barpromart = (ProgressBar) convertView.findViewById(R.id.barpromart);
            convertView.setTag(holder);
        } else {
            holder = (AdapterGridViewPromotionArticle.ViewHolder) convertView.getTag();
        }

        Beanarticledetail detail = this.listData.get(position);
        holder.libpromart.setText(detail.getLibelle().length() <= limitProduitName ?
                detail.getLibelle() : (detail.getLibelle().substring(0, (limitProduitName-1)) + "...") );
        holder.itempromrestart.setText(String.valueOf(detail.getArticlerestant())+" article(s) restant(s)");
        holder.prixpromart.setText(String.valueOf(detail.getPrix()) +" FCFA");
        if(detail.getReduction() > 0){
            holder.textprompourcentage.setText("-"+String.valueOf(detail.getReduction())+"%");
        }
        else{
            holder.textprompourcentage.setVisibility(View.INVISIBLE);
        }
        Glide.with(context)
                .load("https://firebasestorage.googleapis.com/v0/b/gestionpanneaux.appspot.com/o/" +
                        detail.getLienweb() + "?alt=media")
                .onlyRetrieveFromCache(false)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imgpromart);

        holder.imgpromart.setOnClickListener(d -> openInterface(detail.getIdart()));
        holder.libpromart.setOnClickListener(d -> openInterface(detail.getIdart()));
        holder.prixpromart.setOnClickListener(d -> openInterface(detail.getIdart()));

        return convertView;
    }

    // Find Image ID corresponding to the name of the image (in the directory mipmap).
    public int getMipmapResIdByName(String resName) {
        String pkgName = context.getPackageName();

        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName, "mipmap", pkgName);
        return resID;
    }

    static class ViewHolder {
        ImageView imgpromart;
        TextView libpromart, prixpromart, itempromrestart, textprompourcentage;
        ProgressBar barpromart;
    }

    // Open INTERFACE :
    private void openInterface(int detail) {
        Intent it = new Intent(context, ArticleActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Set Mode : 3 -> DETAIL
        /*it.putExtra("mode", 3);
        it.putExtra("id", detail);*/
        it.putExtra("idart", detail);
        context.startActivity(it);
    }
}