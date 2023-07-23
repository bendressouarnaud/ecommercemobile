package com.ankk.market.adapters;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ankk.market.OpenApplication;
import com.ankk.market.R;
import com.ankk.market.beans.Beanarticledetail;
import com.ankk.market.beans.Beanarticlelive;
import com.ankk.market.beans.Beancategorie;
import com.ankk.market.databinding.CardviewarticleBinding;
import com.ankk.market.databinding.CardviewdetailsousproduitBinding;
import com.ankk.market.models.Achat;
import com.ankk.market.repositories.AchatRepository;
import com.ankk.market.repositories.BeanarticledetailRepository;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterListArticle extends RecyclerView.Adapter<AdapterListArticle.ArticleViewHolder> {


    // Attributes :
    private final Context context;
    private List<Beanarticlelive> donnee;
    BeanarticledetailRepository beanarticledetailRepository;
    AchatRepository achatRepository;
    OpenApplication app;
    int nbreCommande = 0;



    public AdapterListArticle(Context context) {
        this.context = context;
        donnee = new ArrayList<>();
        app = (OpenApplication) context.getApplicationContext();
        beanarticledetailRepository = new BeanarticledetailRepository(app);
        achatRepository = new AchatRepository(app);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterListArticle.ArticleViewHolder holder,
                                 int position) {
        // Get DATA :
        Glide.with(context)
                .load("https://firebasestorage.googleapis.com/v0/b/gestionpanneaux.appspot.com/o/"+
                        donnee.get(position).getLienweb()+"?alt=media")
                .onlyRetrieveFromCache(false)
                .transition(DrawableTransitionOptions.withCrossFade(1000))
                //.placeholder(R.drawable.ic_panier)
                .into(holder.binder.imgarticle);
        holder.binder.libellearticle.setText(donnee.get(position).getLibelle());
        holder.binder.prixarticle.setText(
                NumberFormat.getInstance(Locale.FRENCH).format(donnee.get(position).getPrix())+" FCFA");
        if(donnee.get(position).getReduction() == 0){
            holder.binder.prixpromotionarticle.setVisibility(View.INVISIBLE);
            holder.binder.articlepourcentage.setVisibility(View.INVISIBLE);
        }
        // STAR Appreciation
        //holder.binder.articlelinearlayout.setVisibility(View.GONE);

        // Article restants :
        holder.binder.articlerestant.setText( String.valueOf(donnee.get(position).getArticlerestant() - donnee.get(position).getArticlereserve()) + " article(s) restant(s)" );

        // BUTTONS & Text message
        if(donnee.get(position).getArticlereserve() >= donnee.get(position).getArticlerestant()){
            holder.binder.textalerte.setVisibility(View.VISIBLE);
            // So DISABLE button :
            //holder.binder.articlebut.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            holder.binder.articlebut.setText("ÉPUISÉ");
            //holder.binder.articlebut.setEnabled(false);
        }
        else holder.binder.textalerte.setVisibility(View.GONE);

        holder.binder.articlebutmoins.setVisibility(View.GONE);
        holder.binder.quantitearticle.setVisibility(View.GONE);
        holder.binder.articlebutplus.setVisibility(View.GONE);

        // Button PLUS :
        holder.binder.articlebutplus.setOnClickListener(d -> addarticle(holder, position, 1));
        holder.binder.articlebutmoins.setOnClickListener(d -> addarticle(holder, position, -1));

        // Set Action on 'articlebut' :
        holder.binder.articlebut.setOnClickListener(d -> addarticle(holder, position, 1));
    }


    @NonNull
    @Override
    public AdapterListArticle.ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                    int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new AdapterListArticle.ArticleViewHolder(DataBindingUtil.inflate(inflater,
                R.layout.cardviewarticle,
                parent,
                false));
    }


    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        CardviewarticleBinding binder;

        public ArticleViewHolder(CardviewarticleBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }

    @Override
    public int getItemCount() {
        return donnee.size();
    }

    public void addItems(Beanarticlelive data) {
        donnee.add(data);
        notifyItemInserted(donnee.size() - 1);
    }

    public void clearEverything(){
        int size = donnee.size();
        donnee.clear();
        notifyItemRangeRemoved(0, size);
    }

    private void addarticle(AdapterListArticle.ArticleViewHolder holder, int position, int nbreElement){
        // Update field :
        Beanarticledetail bl = beanarticledetailRepository.getItem(donnee.get(position).getIdart());
        if(bl != null){
            int articleRestant = bl.getArticlerestant();
            bl.setArticlerestant(articleRestant - 1);

            // First HIDE texteview :
            holder.binder.quantitearticle.setVisibility(View.GONE);
            holder.binder.progressarticle.setVisibility(View.VISIBLE);

            // Pick ARTICLE BOOKED :
            List<Achat> lCommande = achatRepository.getAllByIdartAndActif(donnee.get(position).getIdart(), 1);
            nbreCommande = 0;
            if(lCommande != null){
                nbreCommande = lCommande.size();
            }

            // Set :
            if(nbreElement == 1) nbreCommande++;
            else nbreCommande--;

            // Hit ACHAT :
            if(nbreElement == 1) {
                Achat at = new Achat();
                at.setActif(1);
                at.setIdart(donnee.get(position).getIdart());
                achatRepository.insert(at);

                // Disable but +
                if(articleRestant == nbreCommande){
                    holder.binder.textalerte.setVisibility(View.VISIBLE);
                    holder.binder.articlebutplus.setEnabled(false);
                    holder.binder.articlebutmoins.setEnabled(true);
                }
            }
            else{
                // Delete the last ACHATS :
                achatRepository.delete(lCommande.get(0));
                if(nbreCommande == 0){
                    holder.binder.articlebutmoins.setEnabled(false);
                    holder.binder.articlebutplus.setEnabled(true);
                    holder.binder.textalerte.setVisibility(View.INVISIBLE);
                }
            }

            // Update :
            //beanarticledetailRepository.insert(bl);

            // Define the HANDLER :
            Handler handlerAsynchLoad = new Handler();
            Runnable runAsynchLoad = new Runnable() {
                @Override
                public void run() {
                    handlerAsynchLoad.removeCallbacks(this);

                    // Now, display ARTICLE requested :
                    holder.binder.articlerestant.setText(
                            String.valueOf(donnee.get(position).getArticlerestant() -
                                    nbreCommande) +
                                    " article(s) restant(s)");
                    // Update
                    holder.binder.quantitearticle.setText(String.valueOf(nbreCommande));
                    holder.binder.progressarticle.setVisibility(View.GONE);
                    holder.binder.quantitearticle.setVisibility(View.VISIBLE);

                    //holder.binder.articlebutplus.setVisibility(View.VISIBLE);
                    holder.binder.articlebutmoins.setVisibility(View.VISIBLE);
                    holder.binder.articlebutplus.setVisibility(View.VISIBLE);
                    holder.binder.articlebut.setVisibility(View.GONE);
                }
            };

            //
            handlerAsynchLoad.postDelayed(runAsynchLoad, 2500);
        }
    }
}