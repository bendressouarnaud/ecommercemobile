package com.ankk.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.ankk.market.adapters.AdapterListArticle;
import com.ankk.market.beans.Beanarticledatahistory;
import com.ankk.market.beans.Beanarticledetail;
import com.ankk.market.beans.Beanarticlelive;
import com.ankk.market.beans.RequeteBean;
import com.ankk.market.databinding.ActivityArticleBinding;
import com.ankk.market.databinding.ActivitySousproduitBinding;
import com.ankk.market.mesobjets.RetrofitTool;
import com.ankk.market.models.Achat;
import com.ankk.market.proxies.ApiProxy;
import com.ankk.market.viewmodels.AccueilViewmodel;
import com.ankk.market.viewmodels.ArticleViewmodel;
import com.ankk.market.viewmodels.VMFactory;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleActivity extends AppCompatActivity {

    // A t t r i b u t e s :
    ActivityArticleBinding binder;
    ApiProxy apiProxy;
    ArticleViewmodel viewmodel;
    int nbreCommande = 0;



    // M e t h o d s :
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_article);

        binder = ActivityArticleBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

        setSupportActionBar(binder.toolbararticle);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black, null));

        // Set the VIEWMODEL :
        viewmodel = new ViewModelProvider(this,
                new VMFactory(getApplication()))
                .get(ArticleViewmodel.class);

        // Get "EXTRAS" :
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int idart = extras.getInt("idart",0);

            // In case the ARTICLE has been already BOOKED, take it into account :
            Achat act = viewmodel.getAchatRepository().getAllByIdartAndActif(idart, 1).stream().findFirst().orElse(null);
            if(act != null){
                // Hide articlebutart :
                binder.articlebutart.setVisibility(View.GONE);
                // Update the number of ARTICLES :
                binder.quantitearticleart.setText(String.valueOf(viewmodel.getAchatRepository().getAllByIdartAndActif(idart, 1).size()));
            }
            else{
                // Hide BUTTONS - & + and TEXT
                binder.articlebutplusart.setVisibility(View.INVISIBLE);
                binder.articlebutmoinsart.setVisibility(View.INVISIBLE);
                binder.quantitearticleart.setVisibility(View.INVISIBLE);
            }

            // Add actions :
            binder.articlebutart.setOnClickListener( d -> addarticle(idart, 1));
            binder.articlebutplusart.setOnClickListener( d -> addarticle(idart, 1));
            binder.articlebutmoinsart.setOnClickListener( d -> addarticle(idart, -1));

            // idart :
            getmobilearticleinformationbyidart(idart);
        }
    }


    private void initProxy() {
        apiProxy = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BCK_URL)
                .client(new RetrofitTool().getClient(false, ""))
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiProxy.class);
    }


    public void getmobilearticleinformationbyidart(int idprd){
        if(apiProxy ==null) initProxy();
        // Set Object :
        RequeteBean rn = new RequeteBean();
        rn.setIdprd(idprd);

        apiProxy.getmobilearticleinformationbyidart(rn).enqueue(new Callback<Beanarticledatahistory>() {
            @Override
            public void onResponse(Call<Beanarticledatahistory> call, Response<Beanarticledatahistory> response) {
                // STOP SIMMMER :

                if (response.code() == 200) {
                    // Now save it :

                    // Call ADAPTER
                    binder.shimarticleloading.stopShimmer();
                    binder.shimarticleloading.setVisibility(View.GONE);
                    binder.constraintcontenuarticle.setVisibility(View.VISIBLE);

                    // Set DATA :
                    binder.carouselarticle.registerLifecycle(getLifecycle());
                    List<CarouselItem> list = new ArrayList<>();
                    // Image URL with caption
                    response.body().getImages().forEach(
                        d -> {
                            list.add(
                                    new CarouselItem(
                                            "https://firebasestorage.googleapis.com/v0/b/gestionpanneaux.appspot.com/o/"+d.getLienweb()+"?alt=media",
                                            ""
                                    )
                            );
                        }
                    );

                    binder.carouselarticle.setData(list);

                    binder.textarticle.setText(response.body().getArticle());
                    binder.nomentreprisearticle.setText(response.body().getEntreprise());

                    // Get PRICE :
                    if(response.body().getReduction() > 0){
                        binder.prixreductionaticle.setText(
                                NumberFormat.getInstance(Locale.FRENCH).format(response.body().getPrix())+" FCFA"
                        );
                        binder.prixreductionaticle.setPaintFlags(
                                binder.prixreductionaticle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
                        );

                        binder.textarticlepourcentage.setText("-"+String.valueOf(response.body().getReduction())+"%");
                        // Compute PRICE :
                        float tpPrice = ((response.body().getPrix() * response.body().getReduction()) / 100);
                        binder.prixarticle.setText(
                                NumberFormat.getInstance(Locale.FRENCH).format(response.body().getPrix() - tpPrice)+" FCFA");
                    }
                    else {
                        binder.prixreductionaticle.setVisibility(View.GONE);
                        binder.textarticlepourcentage.setVisibility(View.GONE);
                        binder.prixarticle.setText(NumberFormat.getInstance(Locale.FRENCH).format(response.body().getPrix()) + " FCFA");
                    }

                    // Article restant :
                    viewmodel.setArticleRestant(response.body().getNombrearticle());
                    if(response.body().getNombrearticle() > 5){
                        // Hide :
                        //binder.imgalertearticle.setVisibility(View.INVISIBLE);
                        binder.imgalertearticle.setImageDrawable(
                                getResources().getDrawable(R.drawable.ic_article_restant, null));
                        // Change COLOR :
                        binder.textarticlerestant.setTextColor(getResources().getColor(R.color.color_green, null));
                    }
                    binder.textarticlerestant.setText(String.valueOf(response.body().getNombrearticle()) + " articles restants");

                    // Contenu du TEXT :
                    binder.textcontenudetail.setText(response.body().getDescriptionproduit());

                    // Modalité :
                    binder.contenumodaliteretour.setText(response.body().getModaliteretour());


                    /*binder.recyclerarticle.setAdapter(viewmodel.getAdapter());
                    response.body().forEach(
                            d -> {
                                // Save each :
                                viewmodel.insertArticle(d);

                                //
                                Beanarticlelive be = new Beanarticlelive();
                                be.setIdart(d.getIdart());
                                be.setPrix(d.getPrix());
                                be.setReduction(d.getReduction());
                                be.setNote(d.getNote());
                                be.setArticlerestant(d.getArticlerestant());
                                be.setLibelle(d.getLibelle());
                                be.setLienweb(d.getLienweb());
                                // Article reserve :
                                List<Achat> getAchat = viewmodel.getAllByIdart(d.getIdart());
                                be.setArticlereserve(getAchat != null ? getAchat.size() : 0);
                                viewmodel.getAdapter().addItems(be);
                            }
                    );

                    // Call
                    notifyUser();*/
                }
                //else onErreur();
            }

            @Override
            public void onFailure(Call<Beanarticledatahistory> call, Throwable t) {
                //Toast.makeText(ArticleActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addarticle(int idart, int nbreElement){
        // Update field :
        int articleRestant = viewmodel.getArticleRestant();
        viewmodel.setArticleRestant(articleRestant - 1);

        // First HIDE texteview :
        binder.quantitearticleart.setVisibility(View.GONE);
        binder.progressarticlesingle.setVisibility(View.VISIBLE);

        // Pick ARTICLE BOOKED :
        List<Achat> lCommande = viewmodel.getAchatRepository().getAllByIdartAndActif(idart, 1);
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
            at.setIdart(idart);
            viewmodel.getAchatRepository().insert(at);

            // Disable but +
            if(articleRestant == nbreCommande){
                binder.articlebutplusart.setEnabled(false);
                binder.articlebutmoinsart.setEnabled(true);
            }
        }
        else{
            // Delete the last ACHATS :
            viewmodel.getAchatRepository().delete(lCommande.get(0));
            if(nbreCommande == 0){
                binder.articlebutmoinsart.setEnabled(false);
                binder.articlebutplusart.setEnabled(true);
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
                binder.textarticlerestant.setText(
                        String.valueOf(viewmodel.getArticleRestant() -
                                nbreCommande) +
                                " article(s) restant(s)");
                // Update
                binder.quantitearticleart.setText(String.valueOf(nbreCommande));
                binder.progressarticlesingle.setVisibility(View.GONE);
                binder.quantitearticleart.setVisibility(View.VISIBLE);

                //holder.binder.articlebutplus.setVisibility(View.VISIBLE);
                binder.articlebutmoinsart.setVisibility(View.VISIBLE);
                binder.articlebutplusart.setVisibility(View.VISIBLE);
                binder.articlebutart.setVisibility(View.GONE);

                // Hide :
                binder.constraintnotifyarticlenew.setVisibility(View.INVISIBLE);
            }
        };

        // Display :
        binder.constraintnotifyarticlenew.setVisibility(View.VISIBLE);

        //
        handlerAsynchLoad.postDelayed(runAsynchLoad, 2500);
    }
}