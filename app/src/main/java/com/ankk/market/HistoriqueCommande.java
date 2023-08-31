package com.ankk.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.ankk.market.beans.BeanArticleHistoCommande;
import com.ankk.market.beans.Beanarticledatahistory;
import com.ankk.market.beans.RequeteBean;
import com.ankk.market.beans.RequeteHistoCommande;
import com.ankk.market.databinding.ActivityArticleBinding;
import com.ankk.market.databinding.ActivityHistoriqueCommandeBinding;
import com.ankk.market.mesobjets.RetrofitTool;
import com.ankk.market.proxies.ApiProxy;
import com.ankk.market.viewmodels.ArticleViewmodel;
import com.ankk.market.viewmodels.HistoriqueViewmodel;
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

public class HistoriqueCommande extends AppCompatActivity {


    // A t t r i b u t e s  :
    ActivityHistoriqueCommandeBinding binder;
    ApiProxy apiProxy;
    RequeteHistoCommande re;
    HistoriqueViewmodel viewmodel;



    // M e t h o d s  :
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binder = ActivityHistoriqueCommandeBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

        getWindow().setStatusBarColor(getResources().getColor(R.color.black, null));

        // Set the VIEWMODEL :
        viewmodel = new ViewModelProvider(this,
                new VMFactory(getApplication()))
                .get(HistoriqueViewmodel.class);

        // Get "EXTRAS" :
        Bundle extras = getIntent().getExtras();
        re = new RequeteHistoCommande();
        if (extras != null) {
            re.setDates( extras.getString("dates") );
            re.setHeure( extras.getString("heure") );
            re.setIdcli(viewmodel.getClientRepository().getAll().get(0).getIdcli());
        }

        // Process :
        getcustomercommandearticle();
    }

    private void initProxy() {
        apiProxy = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BCK_URL)
                .client(new RetrofitTool().getClient(false, ""))
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiProxy.class);
    }


    public void getcustomercommandearticle(){
        if(apiProxy ==null) initProxy();
        apiProxy.getcustomercommandearticle(re).enqueue(new Callback<BeanArticleHistoCommande>() {
            @Override
            public void onResponse(Call<BeanArticleHistoCommande> call, Response<BeanArticleHistoCommande> response) {
                // STOP SIMMMER :
                binder.shimarticlehisto.stopShimmer();

                if (response.code() == 200) {
                    // Call ADAPTER
                    binder.shimarticlehisto.setVisibility(View.GONE);
                    binder.constraintarticlehisto.setVisibility(View.VISIBLE);

                    binder.datenotif.setText(re.getDates());
                    binder.heurenotif.setText(re.getHeure());
                    binder.nbrearticlenotif.setText("Total article: " + String.valueOf(response.body().getTotalarticle()));
                    binder.prixarticlenotif.setText(NumberFormat.getInstance(Locale.FRENCH).format(response.body().getTotalprix())+" FCFA");

                    //
                    binder.recyclernotif.setAdapter(viewmodel.getAdapterArticleCommande());
                    response.body().getListearticle().forEach(
                        d -> {
                            viewmodel.getAdapterArticleCommande().addItems(d);
                        }
                    );
                }
                //else onErreur();
            }

            @Override
            public void onFailure(Call<BeanArticleHistoCommande> call, Throwable t) {
                //Toast.makeText(ArticleActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}