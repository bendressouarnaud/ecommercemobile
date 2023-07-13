package com.ankk.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ankk.market.adapters.AdapterDetailProduit;
import com.ankk.market.adapters.AdapterListArticle;
import com.ankk.market.adapters.AdapterListProduit;
import com.ankk.market.beans.Beanarticledetail;
import com.ankk.market.beans.Beancategorie;
import com.ankk.market.beans.RequeteBean;
import com.ankk.market.databinding.ActivityMainBinding;
import com.ankk.market.databinding.ActivitySousproduitBinding;
import com.ankk.market.fragments.Fragmentcategorie;
import com.ankk.market.mesobjets.RetrofitTool;
import com.ankk.market.proxies.ApiProxy;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binder = ActivitySousproduitBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

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

            // Montant  -- IDMAG :
            /*montant = extras.getInt("montant",0);
            // Display the amount
            binder.editmontantcinet.setText(String.valueOf(montant));
            idmag = extras.getInt("idmag",0);
            mois = extras.getInt("mois",0);
            taxeodp = extras.getInt("taxeodp",0);
            idclj = extras.getInt("idclj",0);*/
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
                    AdapterListArticle adapter = new AdapterListArticle(getApplicationContext());
                    binder.shimarticledetail.stopShimmer();
                    binder.shimarticledetail.setVisibility(View.GONE);
                    binder.recyclerarticle.setVisibility(View.VISIBLE);
                    binder.recyclerarticle.setAdapter(adapter);
                    response.body().forEach(
                        d -> {
                            adapter.addItems(d);
                        }
                    );
                }
                //else onErreur();
            }

            @Override
            public void onFailure(Call<List<Beanarticledetail>> call, Throwable t) {
                Toast.makeText(SousproduitActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}