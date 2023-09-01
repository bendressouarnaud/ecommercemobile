package com.ankk.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ankk.market.beans.Beanarticledetail;
import com.ankk.market.beans.Beanarticlelive;
import com.ankk.market.beans.RequestBean;
import com.ankk.market.beans.RequeteBean;
import com.ankk.market.databinding.ActivitySousproduitBinding;
import com.ankk.market.mesobjets.RetrofitTool;
import com.ankk.market.models.Achat;
import com.ankk.market.proxies.ApiProxy;
import com.ankk.market.viewmodels.DetailViewmodel;
import com.ankk.market.viewmodels.VMFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SousproduitActivity extends AppCompatActivity {

    //
    ActivitySousproduitBinding binder;
    ApiProxy apiProxy;
    int iddet = 0, mode =0 ;
    String libSousProduit = "";
    DetailViewmodel viewmodel;
    boolean temoin = false;
    int cptTimer = 0;
    TextView textPanierCount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binder = ActivitySousproduitBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

        // Set the VIEWMODEL :
        viewmodel = new ViewModelProvider(this,
                new VMFactory(getApplication()))
                .get(DetailViewmodel.class);

        setSupportActionBar(binder.toolbarsousproduit);

        getWindow().setStatusBarColor(getResources().getColor(R.color.black));

        //
        binder.shimarticledetail.startShimmer();

        binder.toolbarsousproduit.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kill
                finish();
            }
        });

        // Get "EXTRAS" :
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // iddet :
            mode = extras.getInt("mode",0);
            //Toast.makeText(SousproduitActivity.this, "mode : "+String.valueOf(mode), Toast.LENGTH_SHORT).show();
            switch (mode){
                case 2:
                    // SOUS-PRODUIT :
                    libSousProduit = extras.getString("lib");
                    getarticlesBasedonLib();
                    break;

                case 3:
                    // DETAIL
                    iddet = extras.getInt("id", 0);
                    // Call to get DATA :
                    getarticlesbasedoniddet(iddet);
                    break;

                case 4:
                    // Display All PROMOTED ARTICLE
                    getpromotedarticles();
                    break;

                default:
                    // DETAIL
                    iddet = extras.getInt("id", 0);
                    // Call to get DATA :
                    getarticlesbasedoniddet(iddet);
                    break;
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sousproduit, menu);
        final MenuItem menuItemPanier = menu.findItem(R.id.actionbookssp);
        View actionViewPanier = menuItemPanier.getActionView();
        textPanierCount = (TextView) actionViewPanier.findViewById(R.id.cart_badge_shop);

        // Hide :
        textPanierCount.setVisibility(View.GONE);

        actionViewPanier.setOnClickListener( d -> onOptionsItemSelected(menuItemPanier));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionbookssp:
                if(!viewmodel.getAchatRepository().getAllByActif(1).isEmpty()) {
                    // Set FLAG
                    viewmodel.setValideCommande(true);
                    Intent it = new Intent(this, PanierActivity.class);
                    startActivity(it);
                }
                else
                    Toast.makeText(getApplicationContext(), "Aucune commande en cours ...", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }



    private void initProxy() {
        apiProxy = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BCK_URL)
                .client(new RetrofitTool().getClient(false, ""))
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiProxy.class);
    }


    public void getarticlesbasedoniddet(int idprd){
        if(apiProxy ==null) initProxy();
        // Set Object :
        RequeteBean rn = new RequeteBean();
        rn.setIdprd(idprd);

        apiProxy.getarticlesbasedoniddet(rn).enqueue(new Callback<List<Beanarticledetail>>() {
            @Override
            public void onResponse(Call<List<Beanarticledetail>> call, Response<List<Beanarticledetail>> response) {
                // STOP SIMMMER :

                if (response.code() == 200) {
                    // Now save it :

                    // Call ADAPTER
                    //AdapterListArticle adapter = new AdapterListArticle(getApplicationContext());
                    binder.shimarticledetail.stopShimmer();
                    binder.shimarticledetail.setVisibility(View.GONE);
                    binder.recyclerarticle.setVisibility(View.VISIBLE);
                    binder.recyclerarticle.setAdapter(viewmodel.getAdapter());
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
                    notifyUser();
                }
                //else onErreur();
            }

            @Override
            public void onFailure(Call<List<Beanarticledetail>> call, Throwable t) {
                Toast.makeText(SousproduitActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getpromotedarticles(){
        if(apiProxy ==null) initProxy();
        // Set Object :
        RequeteBean rn = new RequeteBean();
        rn.setIdprd(1);

        apiProxy.getpromotedarticles(rn).enqueue(new Callback<List<Beanarticledetail>>() {
            @Override
            public void onResponse(Call<List<Beanarticledetail>> call, Response<List<Beanarticledetail>> response) {
                // STOP SIMMMER :

                if (response.code() == 200) {
                    // Now save it :

                    // Call ADAPTER
                    //AdapterListArticle adapter = new AdapterListArticle(getApplicationContext());
                    binder.shimarticledetail.stopShimmer();
                    binder.shimarticledetail.setVisibility(View.GONE);
                    binder.recyclerarticle.setVisibility(View.VISIBLE);
                    binder.recyclerarticle.setAdapter(viewmodel.getAdapter());
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
                    notifyUser();
                }
                //else onErreur();
            }

            @Override
            public void onFailure(Call<List<Beanarticledetail>> call, Throwable t) {
                Toast.makeText(SousproduitActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getarticlesBasedonLib(){
        if(apiProxy ==null) initProxy();
        // Set Object :
        RequestBean rn = new RequestBean();
        rn.setId(0);
        rn.setLib(libSousProduit);

        apiProxy.getmobilearticlesBasedonLib(rn).enqueue(new Callback<List<Beanarticledetail>>() {
            @Override
            public void onResponse(Call<List<Beanarticledetail>> call, Response<List<Beanarticledetail>> response) {
                // STOP SIMMMER :

                if (response.code() == 200) {
                    // Now save it :

                    // Call ADAPTER
                    //AdapterListArticle adapter = new AdapterListArticle(getApplicationContext());
                    binder.shimarticledetail.stopShimmer();
                    binder.shimarticledetail.setVisibility(View.GONE);
                    binder.recyclerarticle.setVisibility(View.VISIBLE);
                    binder.recyclerarticle.setAdapter(viewmodel.getAdapter());
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
                    notifyUser();
                }
                //else onErreur();
            }

            @Override
            public void onFailure(Call<List<Beanarticledetail>> call, Throwable t) {
                Toast.makeText(SousproduitActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Display :
    private void displayArticle(){

        //
        List<Beanarticledetail> liste = viewmodel.getAllArticleByIddet(iddet);
        Toast.makeText(this, "Taille : " +
                String.valueOf(liste.size()), Toast.LENGTH_SHORT).show();

        //
        viewmodel.getAllArticleByIddet(iddet).forEach(
            d -> {
                // Map :
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
    }

    // Whenever an article is added, notify :
    public void notifyUser(){

        viewmodel.getAllLiveCommande().observe(this, d->{

            // Display SIZE :
            if( d.size() > 0){
                textPanierCount.setText(String.valueOf(d.size()));
                if(textPanierCount.getVisibility() != View.VISIBLE){
                    textPanierCount.setVisibility(View.VISIBLE);
                }
            }
            else textPanierCount.setVisibility(View.INVISIBLE);

            if(d.isEmpty() && viewmodel.isValideCommande()){
                // Close the DOORS, meaning PANIER is 'empty' :
                finish();
            }

            //
            cptTimer = 0;

            if(temoin){

                // Define the HANDLER :
                Handler handlerAsynchLoad = new Handler();
                Runnable runAsynchLoad = new Runnable() {
                    @Override
                    public void run() {
                        cptTimer++;

                        if(cptTimer == 1){
                            binder.constraintnotify.getBackground().setAlpha(200); // int value between 0 and 255
                            handlerAsynchLoad.postDelayed(this, 1000);
                        }
                        else if(cptTimer == 2){
                            binder.constraintnotify.getBackground().setAlpha(180); // int value between 0 and 255
                            handlerAsynchLoad.postDelayed(this, 500);
                        }
                        else if(cptTimer == 3){
                            //
                            cptTimer = 0;
                            binder.constraintnotify.setVisibility(View.GONE);
                            binder.constraintnotify.getBackground().setAlpha(255);
                            handlerAsynchLoad.removeCallbacks(this);
                        }
                    }
                };

                // Display :
                binder.constraintnotify.setVisibility(View.VISIBLE);

                // Display :
                /*if((!d.isEmpty()) && (textPanierCount.getVisibility() == View.GONE)) {
                    textPanierCount.setVisibility(View.VISIBLE);
                }
                else{
                    textPanierCount.setVisibility(View.GONE);
                }*/

                //
                handlerAsynchLoad.postDelayed(runAsynchLoad, 1000);
            }

            //
            if(!temoin) temoin = true;
        });
    }

}