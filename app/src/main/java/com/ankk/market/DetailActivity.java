package com.ankk.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ankk.market.beans.Beansousproduit;
import com.ankk.market.beans.Beansousproduitarticle;
import com.ankk.market.beans.Detail;
import com.ankk.market.beans.RequeteBean;
import com.ankk.market.databinding.ActivityDetailBinding;
import com.ankk.market.databinding.ActivityProduitBinding;
import com.ankk.market.mesobjets.RetrofitTool;
import com.ankk.market.proxies.ApiProxy;
import com.ankk.market.viewmodels.DetailActivityViewModel;
import com.ankk.market.viewmodels.ProduitViewmodel;
import com.ankk.market.viewmodels.VMFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    // A t t r i b u t e s :
    ActivityDetailBinding binder;
    ApiProxy apiProxy;
    DetailActivityViewModel viewmodel;



    // Methodes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binder = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        setSupportActionBar(binder.toolbardetailactivity);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black, null));

        // Set the VIEWMODEL :
        viewmodel = new ViewModelProvider(this,
                new VMFactory(getApplication()))
                .get(DetailActivityViewModel.class);

        // Get "EXTRAS" :
        Bundle extras = getIntent().getExtras();
        int idspr = 0;
        if (extras != null) {
            // Get EveryTHING :
            idspr = extras.getInt("idspr",0);
            //
            binder.toolbardetailactivity.setTitle(extras.getString("libelle", "---"));
        }

        // Display 'Sous-Produit'
        /**/

        //
        binder.shimmerdetailactivity.startShimmer();
        getmobilealldetailsbyidspr(idspr);
        getmobilealldetailsarticles(idspr);
    }


    private void initProxy() {
        apiProxy = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BCK_URL)
                .client(new RetrofitTool().getClient(false, ""))
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiProxy.class);
    }

    public void getmobilealldetailsbyidspr(int idspr){
        if(apiProxy ==null) initProxy();
        // Set Object :
        RequeteBean rn = new RequeteBean();
        rn.setIdprd(idspr);

        apiProxy.getmobilealldetailsbyidspr(rn).enqueue(new Callback<List<Detail>>() {
            @Override
            public void onResponse(Call<List<Detail>> call, Response<List<Detail>> response) {
                // STOP SIMMMER :
                //Toast.makeText(getApplicationContext(), "AZERTY : "+String.valueOf(response.body().size()), Toast.LENGTH_SHORT).show();
                if (response.code() == 200) {
                    // Now save it :
                    binder.shimmerdetailactivity.stopShimmer();
                    binder.shimmerdetailactivity.setVisibility(View.GONE);
                    LinearLayoutManager layoutManagerOffre = new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.HORIZONTAL, false);
                    binder.recyclerdetailactivity.setLayoutManager(layoutManagerOffre);
                    binder.recyclerdetailactivity.setAdapter(viewmodel.getAdapter());
                    binder.recyclerdetailactivity.setVisibility(View.VISIBLE);

                    // Feed
                    try {
                        response.body().forEach(
                                d -> {
                                    //
                                    viewmodel.getAdapter().addItems(d);
                                }
                        );
                    }
                    catch (Exception exc){
                        Toast.makeText(getApplicationContext(), "exc : "+
                                exc.toString(), Toast.LENGTH_LONG).show();
                    }
                }
                //else onErreur();
            }

            @Override
            public void onFailure(Call<List<Detail>> call, Throwable t) {
                //Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getmobilealldetailsarticles(int idspr){
        if(apiProxy ==null) initProxy();
        // Set Object :
        RequeteBean rn = new RequeteBean();
        rn.setIdprd(idspr);

        apiProxy.getmobilealldetailsarticles(rn).enqueue(new Callback<List<Beansousproduitarticle>>() {
            @Override
            public void onResponse(Call<List<Beansousproduitarticle>> call, Response<List<Beansousproduitarticle>> response) {
                // STOP SIMMMER :
                if (response.code() == 200) {
                    // Now save it :
                    binder.recyclerdisplayarticles.setAdapter(viewmodel.getAdapterDisplayDetail());
                    binder.recyclerdisplayarticles.setVisibility(View.VISIBLE);
                    // Feed
                    response.body().forEach(
                            d -> {
                                //
                                viewmodel.getAdapterDisplayDetail().addItems(d);
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