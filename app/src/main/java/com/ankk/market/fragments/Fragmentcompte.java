package com.ankk.market.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ankk.market.BuildConfig;
import com.ankk.market.CompteActivity;
import com.ankk.market.OpenApplication;
import com.ankk.market.R;
import com.ankk.market.adapters.AdapterDetailProduit;
import com.ankk.market.adapters.AdapterListProduit;
import com.ankk.market.databinding.FragmentcompteBinding;
import com.ankk.market.mesobjets.BoiteOutil;
import com.ankk.market.mesobjets.RetrofitTool;
import com.ankk.market.models.Commune;
import com.ankk.market.models.Produit;
import com.ankk.market.proxies.ApiProxy;
import com.ankk.market.repositories.CommuneRepository;
import com.ankk.market.viewmodels.ClientViewmodel;
import com.ankk.market.viewmodels.FragmentproduitViewmodel;
import com.ankk.market.viewmodels.VMFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragmentcompte extends Fragment {

    // A t t r i b u t e s :
    OpenApplication app;
    FragmentcompteBinding binder;
    AlertDialog alertDialogLoadPicture;
    ApiProxy apiProxy;
    boolean getCommune = false;
    CommuneRepository communeRepository;
    List<Commune> data ;
    ClientViewmodel viewmodel;


    // M e t h o d s :
    public Fragmentcompte() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (OpenApplication) getActivity().getApplication();
        communeRepository = new CommuneRepository(app);
        data = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binder = DataBindingUtil.inflate(inflater,
                R.layout.fragmentcompte,
                container,
                false);
        return binder.getRoot();
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewmodel  = new ViewModelProvider(this,
                new VMFactory(getActivity().getApplication()))
                .get(ClientViewmodel.class);

        // In case User has already logged in, display CHANGES :
        if(!viewmodel.getCompte().isEmpty()){
            binder.butcompte.setText("Compte");
            binder.textmerci.setText("Gérer vos données");
        }

        // Set action :
        binder.butcompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                if(!communeRepository.getAll().isEmpty()){
                    if(viewmodel.getCompte().isEmpty() && !viewmodel.isFlagClientLive()){
                        viewmodel.setFlagClientLive(true);
                        // set listener :
                        viewmodel.getCompteAllLive().observe(getActivity(), d-> {
                            // User has successfully created his ACCOUNT, change BUTTON and TEXT
                            binder.butcompte.setText("Compte");
                            binder.textmerci.setText("Gérer vos données");
                            // Set New Call if needed :
                            /*Intent itn = new Intent(getContext(), CompteActivity.class);
                            itn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(itn);*/
                        });
                    }
                    // Open COMPTE :
                    Intent it = new Intent(getContext(), CompteActivity.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(it);
                }
                else {
                    // First Check INTERNET
                    if (BoiteOutil.checkInternet(getContext())) {
                        // Get DATA from
                        processDialog();
                    } else {
                        Toast.makeText(getContext(),
                                "Connexion Internet requise !", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    //
    public void processDialog(){
        // We can launch the appropriate METHOD to send the DATA :
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View vRapport = inflater.inflate(R.layout.layoutpatienterdialog, null);

        // Get OBects :
        TextView textpatienter = vRapport.findViewById(R.id.textpatienter);
        textpatienter.setText("Initialisation Système");
        ProgressBar progresspatienter = vRapport.findViewById(R.id.progresspatienter);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                        // Hide Items :
                        //binder.butcompte.setVisibility(View.INVISIBLE);
                        //binder.textmerci.setVisibility(View.INVISIBLE);

                        // Open COMPTE :
                        Intent it = new Intent(getContext(), CompteActivity.class);
                        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
                        communeRepository.insert(p);
                        data.add(p);
                    });
                    // Notify :
                    getCommune = true;
                }
                else onErreur();
            }

            @Override
            public void onFailure(Call<List<Commune>> call, Throwable t) {
                onErreur();
            }
        });
    }

    // On error
    public void onErreur(){
        // Hide :
        getCommune = true;
    }

}
