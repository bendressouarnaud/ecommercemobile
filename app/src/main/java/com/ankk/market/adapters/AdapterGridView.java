package com.ankk.market.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ankk.market.R;
import com.ankk.market.beans.ProduitBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

public class AdapterGridView extends BaseAdapter {

    private List<ProduitBean> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterGridView(Context aContext, List<ProduitBean> listData) {
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

        ProduitBean country = this.listData.get(position);
        holder.libgriditem.setText(country.getLib());
        Glide.with(context)
                .load(country.getUrl())
                .onlyRetrieveFromCache(false)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imggriditem);

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
}
