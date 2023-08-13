package com.ankk.market.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ankk.market.ArticleActivity;
import com.ankk.market.R;
import com.ankk.market.SousproduitActivity;
import com.ankk.market.beans.Beanresumearticle;
import com.ankk.market.beans.Detail;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

public class AdapterGridViewResumeArticle extends BaseAdapter {

    private List<Beanresumearticle> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterGridViewResumeArticle(Context aContext, List<Beanresumearticle> listData) {
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
        AdapterGridViewResumeArticle.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.cardgriditemdisplay, null);
            holder = new AdapterGridViewResumeArticle.ViewHolder();
            holder.imggriditemdisplay = (ImageView) convertView.findViewById(R.id.imggriditemdisplay);
            holder.libgriditemdisplay = (TextView) convertView.findViewById(R.id.libgriditemdisplay);
            holder.prixgriditemdisplay = (TextView) convertView.findViewById(R.id.prixgriditemdisplay);
            convertView.setTag(holder);
        } else {
            holder = (AdapterGridViewResumeArticle.ViewHolder) convertView.getTag();
        }

        Beanresumearticle detail = this.listData.get(position);
        holder.libgriditemdisplay.setText(detail.getLibelle());
        holder.prixgriditemdisplay.setText(String.valueOf(detail.getPrix()) +" FCFA");
        Glide.with(context)
                .load("https://firebasestorage.googleapis.com/v0/b/gestionpanneaux.appspot.com/o/" +
                        detail.getLienweb() + "?alt=media")
                .onlyRetrieveFromCache(false)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imggriditemdisplay);

        holder.libgriditemdisplay.setOnClickListener(d -> openInterface(detail.getIdart()));
        holder.imggriditemdisplay.setOnClickListener(d -> openInterface(detail.getIdart()));

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
        ImageView imggriditemdisplay;
        TextView libgriditemdisplay, prixgriditemdisplay;
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