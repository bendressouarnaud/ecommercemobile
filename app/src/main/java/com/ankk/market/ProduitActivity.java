package com.ankk.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ankk.market.beans.Beanarticlelive;
import com.ankk.market.beans.Beancategorie;
import com.ankk.market.beans.Beansousproduit;
import com.ankk.market.beans.Beansousproduitarticle;
import com.ankk.market.beans.RequeteBean;
import com.ankk.market.databinding.ActivityProduitBinding;
import com.ankk.market.databinding.ActivitySousproduitBinding;
import com.ankk.market.fragments.Fragmentcategorie;
import com.ankk.market.mesobjets.RetrofitTool;
import com.ankk.market.models.Achat;
import com.ankk.market.models.Produit;
import com.ankk.market.proxies.ApiProxy;
import com.ankk.market.viewmodels.DetailViewmodel;
import com.ankk.market.viewmodels.ProduitViewmodel;
import com.ankk.market.viewmodels.VMFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProduitActivity extends AppCompatActivity {

    // A t t r i b u t e s :
    ApiProxy apiProxy;
    ActivityProduitBinding binder;
    int idprd = 0;
    ProduitViewmodel viewmodel;
    TextView textSearch;



    // M e t h o d s  :
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binder = ActivityProduitBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        setSupportActionBar(binder.toolbardisplayproduit);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));

        // Set the VIEWMODEL :
        viewmodel = new ViewModelProvider(this,
                new VMFactory(getApplication()))
                .get(ProduitViewmodel.class);

        // Get "EXTRAS" :
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Get EveryTHING :
            idprd = extras.getInt("idprd",0);
        }

        binder.toolbardisplayproduit.setTitle(
                viewmodel.getProduitRepository().getItem(idprd).getLibelle());

        binder.toolbardisplayproduit.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kill
                finish();
            }
        });

        //
        binder.shimmerdisplayproduit.startShimmer();
        getmobileallsousproduitsbyidprd();
        getmobileallsousproduits();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.produitmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rechproduit:
                Intent its = new Intent(getApplicationContext(), RechercheActivity.class);
                startActivity(its);
                break;
        }
        return true;
    }


    // Set API :
    private void initProxy() {
        apiProxy = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BCK_URL)
                .client(new RetrofitTool().getClient(false, ""))
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiProxy.class);
    }

    public void getmobileallsousproduitsbyidprd(){
        if(apiProxy ==null) initProxy();
        // Set Object :
        RequeteBean rn = new RequeteBean();
        rn.setIdprd(idprd);

        apiProxy.getmobileallsousproduitsbyidprd(rn).enqueue(new Callback<List<Beansousproduit>>() {
            @Override
            public void onResponse(Call<List<Beansousproduit>> call, Response<List<Beansousproduit>> response) {
                // STOP SIMMMER :
                //Toast.makeText(context, "AZERTY : "+String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                if (response.code() == 200) {
                    // Now save it :
                    binder.shimmerdisplayproduit.stopShimmer();
                    binder.shimmerdisplayproduit.setVisibility(View.GONE);
                    LinearLayoutManager layoutManagerOffre = new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.HORIZONTAL, false);
                    binder.recyclerdisplayproduit.setLayoutManager(layoutManagerOffre);
                    binder.recyclerdisplayproduit.setAdapter(viewmodel.getAdapter());
                    binder.recyclerdisplayproduit.setVisibility(View.VISIBLE);

                    // Feed
                    response.body().forEach(
                            d -> {
                                //
                                viewmodel.getAdapter().addItems(d);
                            }
                    );
                }
                //else onErreur();
            }

            @Override
            public void onFailure(Call<List<Beansousproduit>> call, Throwable t) {
                //Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getmobileallsousproduits(){
        if(apiProxy ==null) initProxy();
        // Set Object :
        RequeteBean rn = new RequeteBean();
        rn.setIdprd(idprd);

        apiProxy.getmobileallsousproduitsarticles(rn).enqueue(new Callback<List<Beansousproduitarticle>>() {
            @Override
            public void onResponse(Call<List<Beansousproduitarticle>> call, Response<List<Beansousproduitarticle>> response) {
                // STOP SIMMMER :
                //Toast.makeText(context, "AZERTY : "+String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                if (response.code() == 200) {
                    // Now save it :
                    binder.recyclerdisplaysousproduit.setAdapter(viewmodel.getAdapterDisplayProduit());
                    binder.recyclerdisplaysousproduit.setVisibility(View.VISIBLE);
                    // Feed
                    response.body().forEach(
                        d -> {
                            //
                            viewmodel.getAdapterDisplayProduit().addItems(d);
                        }
                    );
                }
            }

            @Override
            public void onFailure(Call<List<Beansousproduitarticle>> call, Throwable t) {
                //Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}