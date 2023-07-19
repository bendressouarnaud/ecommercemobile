package com.ankk.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ankk.market.adapters.AdapterDetailProduit;
import com.ankk.market.adapters.AdapterListArticle;
import com.ankk.market.adapters.AdapterListProduit;
import com.ankk.market.beans.Beanarticledetail;
import com.ankk.market.beans.Beanarticlelive;
import com.ankk.market.beans.Beancategorie;
import com.ankk.market.beans.RequeteBean;
import com.ankk.market.databinding.ActivityMainBinding;
import com.ankk.market.databinding.ActivitySousproduitBinding;
import com.ankk.market.fragments.Fragmentcategorie;
import com.ankk.market.mesobjets.RetrofitTool;
import com.ankk.market.models.Achat;
import com.ankk.market.proxies.ApiProxy;
import com.ankk.market.repositories.BeanarticledetailRepository;
import com.ankk.market.viewmodels.DetailViewmodel;
import com.ankk.market.viewmodels.VMFactory;

import java.util.Arrays;
import java.util.List;

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
    DetailViewmodel viewmodel;
    boolean temoin = false;
    int cptTimer = 0;



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

        // Get "EXTRAS" :
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // iddet :
            iddet = extras.getInt("id",0);
            mode = extras.getInt("mode",0);
            // Call to get DATA :
            getarticlesbasedoniddet(iddet);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sousproduit, menu);
        final MenuItem menuItem = menu.findItem(R.id.actionbookssp);
        View actionView = menuItem.getActionView();

        /*actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });*/

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case 3: {
                // Do something
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
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
                //Toast.makeText(context, "AZERTY : "+String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
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
                            //viewmodel.getAdapter().addItems(d);
                        }
                    );

                    // Call
                    displayArticle();
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
        viewmodel.getAllArticle().forEach(
            d -> {
                // Map :
                Integer idart, prix, reduction, note, articlerestant, articlereserve;
                String libelle, lienweb;
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

        /*viewmodel.getAllArticleLive().observe(this, new Observer<List<Beanarticledetail>>() {
                    @Override
                    public void onChanged(List<Beanarticledetail> article) {
                        if(SousproduitActivity.this.getLifecycle().getCurrentState()
                                == Lifecycle.State.RESUMED){
                            if(viewmodel.getAdapter().getItemCount() > 0){
                                // Clean :
                                viewmodel.getAdapter().clearEverything();
                            }
                            // Update the cached copy of the words in the adapter.
                            //article.forEach(viewmodel.getAdapter()::addItems);
                            article.forEach(
                                d -> {
                                    // Map :
                                    Integer idart, prix, reduction, note, articlerestant, articlereserve;
                                    String libelle, lienweb;
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
                    }
                }
        );*/
    }

    // Whenever an article is added, notify :
    public void notifyUser(){
        viewmodel.getAllAchatLive().observe(this, d->{

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

                //
                handlerAsynchLoad.postDelayed(runAsynchLoad, 1000);
            }


            //
            if(!temoin) temoin = true;
            //else temoin = false;

            // Display the SIZE:
            //d.size()
        });
    }

}