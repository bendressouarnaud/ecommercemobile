package com.ankk.market.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ankk.market.OpenApplication;
import com.ankk.market.R;
import com.ankk.market.beans.BeanArticlestatusresponse;
import com.ankk.market.beans.BeanItemPanier;
import com.ankk.market.beans.Beanarticlelive;
import com.ankk.market.databinding.CardviewarticleBinding;
import com.ankk.market.models.Achat;
import com.ankk.market.repositories.AchatRepository;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterListPanier extends RecyclerView.Adapter<AdapterListPanier.PanierViewHolder> {

    // A t t r i b u t e s   :
    private final Context context;
    private List<BeanItemPanier> donnee;
    Activity activity;
    OpenApplication app;
    AchatRepository achatRepository;


    // M e t h o d s   :
    public AdapterListPanier(Context context) {
        this.context = context;
        donnee = new ArrayList<>();
        app = (OpenApplication) context.getApplicationContext();
        achatRepository = new AchatRepository(app);
    }

    @NonNull
    @Override
    public AdapterListPanier.PanierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new AdapterListPanier.PanierViewHolder(DataBindingUtil.inflate(inflater,
                R.layout.cardviewarticle,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListPanier.PanierViewHolder holder,
        int position) {

        // Get DATA :
        Glide.with(context)
                .load("https://firebasestorage.googleapis.com/v0/b/gestionpanneaux.appspot.com/o/"+
                        donnee.get(position).getArticle().getLienweb()+"?alt=media")
                .onlyRetrieveFromCache(false)
                .transition(DrawableTransitionOptions.withCrossFade(1000))
                //.placeholder(R.drawable.ic_panier)
                .into(holder.binder.imgarticle);
        holder.binder.libellearticle.setText(donnee.get(position).getArticle().getLibelle());
        holder.binder.prixarticle.setText(
                NumberFormat.getInstance(Locale.FRENCH).format(donnee.get(position).getArticle().getPrix())+" FCFA");
        //------------------
        if(donnee.get(position).getArticle().getReduction() == 0){
            holder.binder.prixpromotionarticle.setVisibility(View.INVISIBLE);
            holder.binder.articlepourcentage.setVisibility(View.INVISIBLE);
        }
        else{
            holder.binder.prixpromotionarticle.setText(
                    NumberFormat.getInstance(Locale.FRENCH).format(donnee.get(position).getArticle().getPrix())+" FCFA"
            );
            holder.binder.prixpromotionarticle.setPaintFlags(
                    holder.binder.prixpromotionarticle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
            );
            holder.binder.articlepourcentage.setText("-"+String.valueOf(donnee.get(position).getArticle().getReduction())+"%");
            // Compute PRICE :
            float tpPrice = ((donnee.get(position).getArticle().getPrix() * donnee.get(position).getArticle().getReduction()) / 100);
            holder.binder.prixarticle.setText(
                    NumberFormat.getInstance(Locale.FRENCH).format(donnee.get(position).getArticle().getPrix() - tpPrice)+" FCFA");
        }

        // Quantité article :
        holder.binder.quantitearticle.setText( String.valueOf(donnee.get(position).getArticle().getArticlereserve()) );
        holder.binder.articlerestant.setText( String.valueOf(donnee.get(position).getRestant()) + " article(s) restant(s)" );

        // Set STARs :
        holder.binder.nbrecommentairearticle.setText("("+String.valueOf(
                donnee.get(position).getTotalcomment()) +")");
        if(donnee.get(position).getNote() == 0) {
            holder.binder.artstarun.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
            holder.binder.artstardeux.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
            holder.binder.artstartroix.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
            holder.binder.artstarquatre.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
            holder.binder.artstarcinq.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
        }
        else if(donnee.get(position).getNote() == 1) {
            holder.binder.artstarun.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
            //
            holder.binder.artstardeux.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
            holder.binder.artstartroix.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
            holder.binder.artstarquatre.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
            holder.binder.artstarcinq.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
        }
        else if(donnee.get(position).getNote() == 2) {
            holder.binder.artstarun.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
            holder.binder.artstardeux.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
            //
            holder.binder.artstartroix.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
            holder.binder.artstarquatre.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
            holder.binder.artstarcinq.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
        }
        else if(donnee.get(position).getNote() == 3) {
            holder.binder.artstarun.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
            holder.binder.artstardeux.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
            holder.binder.artstartroix.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
            //
            holder.binder.artstarquatre.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
            holder.binder.artstarcinq.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
        }
        else if(donnee.get(position).getNote() == 4) {
            holder.binder.artstarun.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
            holder.binder.artstardeux.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
            holder.binder.artstartroix.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
            holder.binder.artstarquatre.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
            //
            holder.binder.artstarcinq.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
        }
        else if(donnee.get(position).getNote() == 5) {
            holder.binder.artstarun.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
            holder.binder.artstardeux.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
            holder.binder.artstartroix.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
            holder.binder.artstarquatre.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
            holder.binder.artstarcinq.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
        }
        else{
            if((donnee.get(position).getNote() > 1) && (donnee.get(position).getNote() < 2)){
                holder.binder.artstarun.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                holder.binder.artstardeux.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_half_full, null));
                //
                holder.binder.artstartroix.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
                holder.binder.artstarquatre.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
                holder.binder.artstarcinq.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
            }
            else if((donnee.get(position).getNote() > 2) && (donnee.get(position).getNote() < 3)){
                holder.binder.artstarun.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                holder.binder.artstardeux.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                holder.binder.artstartroix.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_half_full, null));
                //
                holder.binder.artstarquatre.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
                holder.binder.artstarcinq.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
            }
            else if((donnee.get(position).getNote() > 3) && (donnee.get(position).getNote() < 4)){
                holder.binder.artstarun.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                holder.binder.artstardeux.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                holder.binder.artstartroix.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                holder.binder.artstarquatre.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_half_full, null));
                //
                holder.binder.artstarcinq.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_empty, null));
            }
            else if(donnee.get(position).getNote() > 4){
                holder.binder.artstarun.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                holder.binder.artstardeux.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                holder.binder.artstartroix.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                holder.binder.artstarquatre.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                holder.binder.artstarcinq.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_half_full, null));
            }
        }
        // Hide
        holder.binder.textalerte.setVisibility(View.INVISIBLE);
        holder.binder.articlebut.setVisibility(View.GONE);
        holder.binder.articlebutplus.setVisibility(View.GONE);
        holder.binder.articlebutmoins.setVisibility(View.GONE);

        // Display :
        holder.binder.imgdeletearticle.setVisibility(View.VISIBLE);
        holder.binder.textsupprimer.setVisibility(View.VISIBLE);

        // Supprimer item :
        holder.binder.imgdeletearticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog(donnee.get(position).getArticle().getIdart());
            }
        });

        holder.binder.textsupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog(donnee.get(position).getArticle().getIdart());
            }
        });
    }

    static class PanierViewHolder extends RecyclerView.ViewHolder {
        CardviewarticleBinding binder;

        public PanierViewHolder(CardviewarticleBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }

    @Override
    public int getItemCount() {
        return donnee.size();
    }

    public void addItems(BeanItemPanier data) {
        donnee.add(data);
        notifyItemInserted(donnee.size() - 1);
    }

    public void clearEverything(){
        int size = donnee.size();
        donnee.clear();
        notifyItemRangeRemoved(0, size);
    }

    // Display DIALOGBOX :
    protected void displayDialog(int idart){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // Set Title and Message:
        builder.setTitle("Confirmation").setMessage("Souhaitez-vous supprimer cet article ?");

        //
        builder.setCancelable(true);
        builder.setIcon(R.mipmap.ic_launcher_final);

        // Create "Positive" button with OnClickListener.
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Call ALL ACHAT related to this ARTICLE :
                List<Achat> articlesAchete = achatRepository.getAllByIdart(idart);
                // Delete ARTICLE :
                achatRepository.deleteAll(articlesAchete.toArray(new Achat[0]));
            }
        });
        //builder.setPositiveButtonIcon(positiveIcon);

        // Create "Negative" button with OnClickListener.
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //  Cancel
                dialog.cancel();
            }
        });
        //builder.setNegativeButtonIcon(negativeIcon);

        // Create AlertDialog:
        AlertDialog alert = builder.create();
        alert.show();
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
