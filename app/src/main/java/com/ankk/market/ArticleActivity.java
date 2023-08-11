package com.ankk.market;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ankk.market.beans.Beanarticledatahistory;
import com.ankk.market.beans.Beanarticledetail;
import com.ankk.market.beans.Beanarticlelive;
import com.ankk.market.beans.RequeteBean;
import com.ankk.market.databinding.ActivityArticleBinding;
import com.ankk.market.databinding.ActivitySousproduitBinding;
import com.ankk.market.mesobjets.RetrofitTool;
import com.ankk.market.models.Achat;
import com.ankk.market.proxies.ApiProxy;

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


    // M e t h o d s :
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_article);

        binder = ActivityArticleBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

        setSupportActionBar(binder.toolbararticle);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black, null));

        // Get "EXTRAS" :
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // idart :
            getmobilearticleinformationbyidart(extras.getInt("idart",0));
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
                    binder.prixarticle.setText(NumberFormat.getInstance(Locale.FRENCH).format(response.body().getPrix()) + " FCFA");
                    if(response.body().getNombrearticle() > 5){
                        // Hide :
                        binder.imgalertearticle.setVisibility(View.INVISIBLE);
                        // Change COLOR :
                        binder.textarticlerestant.setTextColor(getResources().getColor(R.color.color_green, null));
                    }
                    binder.textarticlerestant.setText(String.valueOf(response.body().getNombrearticle()) + " articles restants");

                    // Contenu du TEXT :
                    binder.textcontenudetail.setText(response.body().getDescriptionproduit());


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


}