package com.ankk.market.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ankk.market.BuildConfig;
import com.ankk.market.OpenApplication;
import com.ankk.market.R;
import com.ankk.market.adapters.AdapterDetailProduit;
import com.ankk.market.adapters.AdapterListCommande;
import com.ankk.market.adapters.AdapterListProduit;
import com.ankk.market.beans.BeanCommande;
import com.ankk.market.beans.BeanCommandeProjection;
import com.ankk.market.beans.RequeteBean;
import com.ankk.market.databinding.FragmentcommandeBinding;
import com.ankk.market.mesobjets.RetrofitTool;
import com.ankk.market.models.Produit;
import com.ankk.market.proxies.ApiProxy;
import com.ankk.market.repositories.ClientRepository;
import com.ankk.market.repositories.CommandeRepository;
import com.ankk.market.viewmodels.FragmentproduitViewmodel;
import com.ankk.market.viewmodels.VMFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentCommande extends Fragment {

    // A t t r i b u t e s :
    OpenApplication app;
    FragmentcommandeBinding binder;
    CommandeRepository commandeRepository;
    ClientRepository clientRepository;
    ApiProxy apiProxy;


    // M e t h o d s  :
    public FragmentCommande() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //
        app = (OpenApplication) getActivity().getApplication();
        commandeRepository = new CommandeRepository(app);
        clientRepository = new ClientRepository(app);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binder = DataBindingUtil.inflate(inflater,
                R.layout.fragmentcommande,
                container,
                false);
        return binder.getRoot();
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Call :
        getmobilehistoricalcommande();
    }


    private void initProxy() {
        apiProxy = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BCK_URL)
                .client(new RetrofitTool().getClient(false, ""))
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiProxy.class);
    }

    public void getmobilehistoricalcommande(){
        initProxy();

        //
        RequeteBean rn = new RequeteBean();
        rn.setIdprd(clientRepository.getAll().get(0).getIdcli());

        apiProxy.getmobilehistoricalcommande(rn).enqueue(new Callback<List<BeanCommandeProjection>>() {
            @Override
            public void onResponse(Call<List<BeanCommandeProjection>> call, Response<List<BeanCommandeProjection>> response) {
                binder.shimcommande.stopShimmer();
                if (response.code() == 200) {

                    // Get DATA :
                    //List<BeanCommande> liste = commandeRepository.getAllGroupByDatesHeure();
                    AdapterListCommande adapter = new AdapterListCommande(getContext());
                    binder.recyclerfragcommande.setAdapter(adapter);

                    // Attach DATA :
                    response.body().forEach(adapter::addItems);

                    // Hide :
                    binder.shimcommande.setVisibility(View.GONE);
                    binder.recyclerfragcommande.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<BeanCommandeProjection>> call, Throwable t) {
                binder.shimcommande.stopShimmer();
            }
        });
    }

}
