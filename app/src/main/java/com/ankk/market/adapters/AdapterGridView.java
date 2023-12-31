package com.ankk.market.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ankk.market.R;
import com.ankk.market.SousproduitActivity;
import com.ankk.market.beans.Detail;
import com.ankk.market.beans.ProduitBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

public class AdapterGridView extends BaseAdapter {

    private List<Detail> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterGridView(Context aContext, List<Detail> listData) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.cardviewgriditem, null);
            holder = new ViewHolder();
            holder.imggriditem = (ImageView) convertView.findViewById(R.id.imggriditem);
            holder.libgriditem = (TextView) convertView.findViewById(R.id.libgriditem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Detail detail = this.listData.get(position);
        holder.libgriditem.setText(detail.getLibelle());
        Glide.with(context)
                .load("https://firebasestorage.googleapis.com/v0/b/gestionpanneaux.appspot.com/o/"+
                        detail.getLienweb() + "?alt=media" )
                .onlyRetrieveFromCache(false)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imggriditem);

        holder.libgriditem.setOnClickListener(d -> openInterface(detail.getIddet()));
        holder.imggriditem.setOnClickListener(d -> openInterface(detail.getIddet()));

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
        ImageView imggriditem;
        TextView libgriditem;
    }

    // Open INTERFACE :
    private void openInterface(int detail){
        Intent it = new Intent(context, SousproduitActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Set Mode : 3 -> DETAIL
        it.putExtra("mode",3);
        it.putExtra("id", detail);
        context.startActivity(it);
    }
}
