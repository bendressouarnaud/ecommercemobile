package com.ankk.market.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ankk.market.BuildConfig;
import com.ankk.market.OpenApplication;
import com.ankk.market.R;
import com.ankk.market.adapters.AdapterDetailProduit;
import com.ankk.market.adapters.AdapterListProduit;
import com.ankk.market.beans.Beancategorie;
import com.ankk.market.beans.RequeteBean;
import com.ankk.market.databinding.FragmentcategorieBinding;
import com.ankk.market.mesobjets.RetrofitTool;
import com.ankk.market.models.Produit;
import com.ankk.market.proxies.ApiProxy;
import com.ankk.market.repositories.ProduitRepository;
import com.ankk.market.viewmodels.FragmentproduitViewmodel;
import com.ankk.market.viewmodels.VMFactory;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragmentcategorie extends Fragment {

    // A t t r i b u t e s :
    OpenApplication app;
    static FragmentcategorieBinding binder;
    ApiProxy apiProxy;
    static FragmentproduitViewmodel viewmodel;
    int keepFirstProductId = 0;
    static AdapterDetailProduit adapterDetailProduit;



    // M e t h o d s :
    public Fragmentcategorie() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (OpenApplication) getActivity().getApplication();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binder = DataBindingUtil.inflate(inflater,
                R.layout.fragmentcategorie,
                container,
                false);
        return binder.getRoot();
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewmodel  = new ViewModelProvider(this,
                new VMFactory(getActivity().getApplication()))
                .get(FragmentproduitViewmodel.class);

        //
        binder.shimmerlibprod.startShimmer();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        binder.recyclerfraglibprod.setLayoutManager(layoutManager);
        AdapterListProduit adapter = new AdapterListProduit(getContext());
        binder.recyclerfraglibprod.setAdapter(adapter);

        // Read DATA :

        viewmodel.getAll().forEach(
            d -> {
                if(keepFirstProductId == 0) keepFirstProductId = d.getIdprd();
                adapter.addItems(d);
            }
        );
        /*String[] donnees = {"Mangue", "Ananas", "Abricot", "Pomme", "Raisin"};
        Arrays.asList(donnees).forEach(
                d -> {
                    adapter.addItems(d);
                }
        );*/


        // Detail :
        LinearLayoutManager layoutManagerDetail = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        binder.recyclerfragdetail.setLayoutManager(layoutManagerDetail);
        adapterDetailProduit = new AdapterDetailProduit(getContext());
        binder.recyclerfragdetail.setAdapter(adapterDetailProduit);

        // Read DATA :
        /*String[] donneesD = {"Mangue", "Ananas", "Abricot", "Pomme", "Raisin"};
        Arrays.asList(donneesD).forEach(
                d -> {
                    adapterDetailProduit.addItems(d);
                }
        );*/
    }

    private void initProxy() {
        apiProxy = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BCK_URL)
                .client(new RetrofitTool().getClient(false, ""))
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiProxy.class);
    }


    /*public void getmobileallsousproduits(){
        if(apiProxy ==null) initProxy();
        // Set Object :
        RequeteBean rn = new RequeteBean();

        apiProxy.getmobileallsousproduits().enqueue(new Callback<List<Produit>>() {
            @Override
            public void onResponse(Call<List<Produit>> call, Response<List<Produit>> response) {
                binder.shimmerproduit.stopShimmer();
                if (response.code() == 200) {
                    // Now save it :
                    response.body().forEach(p -> {
                        produitRepository.insert(p);
                        adapter.addItems(p);
                    });
                    // Hide :
                    binder.shimmerproduit.setVisibility(View.GONE);
                    binder.recyclerproduit.setVisibility(View.VISIBLE);
                }
                else onErreur();
            }

            @Override
            public void onFailure(Call<List<Produit>> call, Throwable t) {
                binder.shimmerproduit.stopShimmer();
                onErreur();
            }
        });
    }*/

    public static void notifyNewSousProduit(List<Beancategorie> data){
        binder.shimmerlibprod.stopShimmer();
        binder.shimmerlibprod.setVisibility(View.GONE);
        binder.recyclerfragdetail.setVisibility(View.VISIBLE);

        data.forEach(
            d -> {
                adapterDetailProduit.addItems(d);
            }
        );
        /*viewmodel.getAllByIdprd(idprd).forEach(
            d -> {
                //adapterDetailProduit.addItems(d);
            }
        );*/
    }
}
