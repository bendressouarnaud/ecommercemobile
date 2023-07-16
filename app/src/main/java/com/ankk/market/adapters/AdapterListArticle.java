package com.ankk.market.adapters;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

public class AdapterListArticle extends RecyclerView.Adapter<AdapterListArticle.ArticleViewHolder> {


    // Attributes :
    private final Context context;
    private List<Beanarticlelive> donnee;
    BeanarticledetailRepository beanarticledetailRepository;
    AchatRepository achatRepository;
    OpenApplication app;



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
        holder.binder.prixarticle.setText(String.valueOf(donnee.get(position).getPrix())+" FCFA");
        if(donnee.get(position).getReduction() == 0){
            holder.binder.prixpromotionarticle.setVisibility(View.GONE);
            holder.binder.articlepourcentage.setVisibility(View.GONE);
        }
        // STAR Appreciation
        holder.binder.articlelinearlayout.setVisibility(View.GONE);
        // BUTTONS & Text message
        if(donnee.get(position).getArticlereserve() >= donnee.get(position).getArticlerestant()){
            holder.binder.textalerte.setVisibility(View.VISIBLE);
        }
        else holder.binder.textalerte.setVisibility(View.GONE);

        holder.binder.articlebutmoins.setVisibility(View.GONE);
        holder.binder.quantitearticle.setVisibility(View.GONE);
        holder.binder.articlebutplus.setVisibility(View.GONE);

        // Set Action on 'articlebut' :
        holder.binder.articlebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update field :
                Beanarticledetail bl = beanarticledetailRepository.getItem(donnee.get(position).getIdart());
                if(bl != null){
                    int articleRestant = bl.getArticlerestant();
                    bl.setArticlerestant(articleRestant - 1);

                    // Hit ACHAT :
                    Achat at = new Achat();
                    at.setActif(1);
                    at.setIdart(donnee.get(position).getIdart());
                    achatRepository.insert(at);

                    //holder.binder.articlebutplus.setVisibility(View.VISIBLE);
                    holder.binder.articlebutmoins.setVisibility(View.VISIBLE);
                    holder.binder.quantitearticle.setVisibility(View.VISIBLE);
                    holder.binder.articlebutplus.setVisibility(View.VISIBLE);
                    holder.binder.articlebut.setVisibility(View.GONE);

                    // Update :
                    /*beanarticledetailRepository.insert(bl);

                    Toast.makeText(context,
                            "Ajout",
                            Toast.LENGTH_SHORT).show();*/
                }
            }
        });
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
}