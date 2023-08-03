package com.ankk.market;

import android.content.Intent;
import android.os.Bundle;

import com.ankk.market.adapters.AdapterListPanier;
import com.ankk.market.beans.BeanActif;
import com.ankk.market.beans.Beanarticledetail;
import com.ankk.market.beans.Beanarticlelive;
import com.ankk.market.beans.Beanarticlerequest;
import com.ankk.market.beans.RequeteBean;
import com.ankk.market.mesobjets.BoiteOutil;
import com.ankk.market.mesobjets.RetrofitTool;
import com.ankk.market.models.Achat;
import com.ankk.market.models.Commune;
import com.ankk.market.proxies.ApiProxy;
import com.ankk.market.repositories.AchatRepository;
import com.ankk.market.repositories.BeanarticledetailRepository;
import com.ankk.market.viewmodels.ClientViewmodel;
import com.ankk.market.viewmodels.PanierViewmodel;
import com.ankk.market.viewmodels.VMFactory;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.ankk.market.databinding.ActivityPanierBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PanierActivity extends AppCompatActivity {

    // A T T R I B U T E S :
    private AppBarConfiguration appBarConfiguration;
    private ActivityPanierBinding binder;
    int nbreArticleGobal=0, nbreArticleIdentique=0;
    long prixTotal = 0;
    PanierViewmodel viewmodel;
    ApiProxy apiProxy;
    AlertDialog alertDialogLoadPicture;
    List<Commune> data ;
    boolean getCommune = false;
    static PanierActivity instance;
    List<BeanActif> keepArticle;
    boolean envoiValidation = false;



    // M e t h o d s :
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get it :
        instance = this;

        binder = ActivityPanierBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        setSupportActionBar(binder.toolbarpanier);

        getWindow().setStatusBarColor(getResources().getColor(R.color.black));

        // Set the VIEWMODEL :
        viewmodel = new ViewModelProvider(this,
                new VMFactory(getApplication()))
                .get(PanierViewmodel.class);

        // Pass Activity :
        viewmodel.getAdapter().setActivity(this);
        data = new ArrayList<>();
        keepArticle = new ArrayList<>();

        //
        //adapter = new AdapterListPanier(getApplicationContext());
        binder.layoutpanier.recyclerviewpanier.setAdapter(viewmodel.getAdapter());

        // Get ACHAT DATA :
        viewmodel.getAllLiveActif().observe(this, new Observer<List<BeanActif>>() {
                    @Override
                    public void onChanged(List<BeanActif> achat) {
                        if(PanierActivity.this.getLifecycle().getCurrentState()
                                == Lifecycle.State.RESUMED){

                            // Kill ACTIVITY :
                            if(achat.isEmpty()){
                                finish();
                            }

                            if(viewmodel.getAdapter().getItemCount() > 0){
                                // Clean :
                                viewmodel.getAdapter().clearEverything();
                            }
                            // Reset :
                            prixTotal= 0;
                            nbreArticleGobal= 0;

                            // Track ARTICLE :
                            if(!keepArticle.isEmpty()) keepArticle = new ArrayList<>();
                            keepArticle = achat;

                            // Update the cached copy of the words in the adapter.
                            //article.forEach(viewmodel.getAdapter()::addItems);
                            achat.forEach(
                                    d -> {
                                        //
                                        Beanarticledetail bl = viewmodel.getBeanarticledetailRepository().getItem(d.getIdart());
                                        // Map :
                                        Beanarticlelive be = new Beanarticlelive();
                                        be.setIdart(d.getIdart());
                                        be.setPrix(bl.getPrix());
                                        be.setReduction(bl.getReduction());
                                        be.setNote(bl.getNote());
                                        be.setArticlerestant(bl.getArticlerestant());
                                        be.setLibelle(bl.getLibelle());
                                        be.setLienweb(bl.getLienweb());
                                        // Article reserve :
                                        List<Achat> getAchat =
                                                viewmodel.getAchatRepository().getAllByIdartAndChoix(d.getIdart(), 1);
                                        be.setArticlereserve(getAchat.size());
                                        viewmodel.getAdapter().addItems(be);
                                        //
                                        nbreArticleGobal++;

                                        // Compute PRICE :
                                        prixTotal = prixTotal + (bl.getPrix() * getAchat.size());
                                    }
                            );

                            // Update the field :
                            binder.layoutpanier.textpanier.setText("PANIER ("+String.valueOf(nbreArticleGobal)+")");
                            binder.layoutpanier.textmontanttotal.setText( NumberFormat.getInstance(Locale.FRENCH).format(prixTotal) + " FCFA");
                        }
                    }
                }
        );

        // Set ACTION on
        binder.toolbarpanier.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kill
                finish();
            }
        });

        binder.layoutpanier.textpayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!viewmodel.getClientRepository().getAll().isEmpty()) {
                    Intent it = new Intent(PanierActivity.this, CinetPay.class);
                    it.putExtra("montant", prixTotal);
                    //it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(it);
                }
                else {
                    if(viewmodel.getCommuneRepository().getAll().isEmpty()){
                        if (BoiteOutil.checkInternet(getApplicationContext())) {
                            // Get DATA from
                            processDialog();
                        } else {
                            Toast.makeText(PanierActivity.this,
                                    "Connexion Internet requise !", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        // Call to register USER's account :
                        Intent it = new Intent(PanierActivity.this, CompteActivity.class);
                        startActivity(it);
                    }
                }
            }
        });
    }


    public void processDialog(){
        // We can launch the appropriate METHOD to send the DATA :
        LayoutInflater inflater = LayoutInflater.from(this);
        View vRapport = inflater.inflate(R.layout.layoutpatienterdialog, null);

        // Get OBects :
        TextView textpatienter = vRapport.findViewById(R.id.textpatienter);
        textpatienter.setText("Initialisation Système");
        ProgressBar progresspatienter = vRapport.findViewById(R.id.progresspatienter);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Synchronisation");

        builder.setIcon(R.mipmap.ic_launcher);
        builder.setView(vRapport);
        builder.setCancelable(false);
        alertDialogLoadPicture = builder.create();
        alertDialogLoadPicture.show();

        // Define the HANDLER :
        Handler handlerAsynchLoad = new Handler();
        Runnable runAsynchLoad = new Runnable() {
            @Override
            public void run() {
                if (getCommune) {
                    // Job done :
                    alertDialogLoadPicture.cancel();
                    handlerAsynchLoad.removeCallbacks(this);

                    // MOVE ON :
                    if(!data.isEmpty()){
                        // Open COMPTE :
                        Intent it = new Intent(PanierActivity.this, CompteActivity.class);
                        //it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(it);
                    }
                } else {
                    handlerAsynchLoad.postDelayed(this, 1000);
                }
            }
        };
        // Call
        getmobileAllCommunes();

        //
        handlerAsynchLoad.postDelayed(runAsynchLoad, 1000);
    }

    // Set API :
    private void initProxy() {
        apiProxy = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BCK_URL)
                .client(new RetrofitTool().getClient(false, ""))
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiProxy.class);
    }

    public void getmobileAllCommunes(){
        initProxy();
        apiProxy.getmobileAllCommunes().enqueue(new Callback<List<Commune>>() {
            @Override
            public void onResponse(Call<List<Commune>> call, Response<List<Commune>> response) {
                if (response.code() == 200) {
                    // Now save it :
                    response.body().forEach(p -> {
                        viewmodel.getCommuneRepository().insert(p);
                        data.add(p);
                    });
                    // Notify :
                    getCommune = true;
                }
                //else onErreur();
            }

            @Override
            public void onFailure(Call<List<Commune>> call, Throwable t) {
                //onErreur();
            }
        });
    }

    public static PanierActivity getInstance() {
        return instance;
    }

    public void setPaiementeffectue(){
        if(apiProxy == null) initProxy();

        // Get ALL articles :
        Beanarticlerequest bt = new Beanarticlerequest();
        bt.setListe(keepArticle);
        bt.setIdcli(viewmodel.getClientRepository().getAll().get(0).getIdcli());

        // Set Flag :
        envoiValidation = true;

        // We can send this :
        apiProxy.sendbooking(bt).enqueue(new Callback<RequeteBean>() {
            @Override
            public void onResponse(Call<RequeteBean> call, Response<RequeteBean> response) {
                if (response.code() == 200) {
                    // Close the DOORS :
                    if(response.body().getIdprd() == 1){
                        // Set ARTICLE 'actif' to '0'
                        List<Achat> articles = viewmodel.getAchatRepository().getAllByActif(1);
                        articles.forEach(
                                d -> {
                                    d.setActif(0);
                                }
                        );
                        //
                        envoiValidation = false;
                        // Update and EXIT :
                        viewmodel.getAchatRepository().updateAll(articles.toArray(new Achat[0]));
                    }
                }
            }

            @Override
            public void onFailure(Call<RequeteBean> call, Throwable t) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(envoiValidation){
            // Display
            binder.layoutpanier.progresspanier.setVisibility(View.VISIBLE);
            binder.layoutpanier.textpanierpatienter.setVisibility(View.VISIBLE);
        }
    }
}