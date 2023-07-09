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
import com.ankk.market.adapters.AdapterOffre;
import com.ankk.market.adapters.AdapterProduit;
import com.ankk.market.beans.Beansousproduit;
import com.ankk.market.databinding.FragmentproduitBinding;
import com.ankk.market.mesenums.Modes;
import com.ankk.market.mesobjets.RetrofitTool;
import com.ankk.market.models.Produit;
import com.ankk.market.proxies.ApiProxy;
import com.ankk.market.repositories.ProduitRepository;
import com.ankk.market.viewmodels.FragmentproduitViewmodel;
import com.ankk.market.viewmodels.VMFactory;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragmentproduit extends Fragment {

    // A t t r i b u t e s :
    AdapterProduit adapter;
    AdapterOffre adapterOff;
    FragmentproduitBinding binder;
    Modes m;
    ApiProxy apiProxy;
    FragmentproduitViewmodel viewmodel;
    ProduitRepository produitRepository;
    OpenApplication app;


    // M e t h o d s :
    public Fragmentproduit() {
    }

    public Fragmentproduit(Modes m) {
        this.m = m;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (OpenApplication) getActivity().getApplication();
        produitRepository = new ProduitRepository(getActivity().getApplication());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binder = DataBindingUtil.inflate(inflater,
                R.layout.fragmentproduit,
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
        binder.shimmerproduit.startShimmer();

        // Carousel :
        binder.carousel.registerLifecycle(getLifecycle());
        List<CarouselItem> list = new ArrayList<>();
        // Image URL with caption
        list.add(
                new CarouselItem(
                        "https://firebasestorage.googleapis.com/v0/b/gestionpanneaux.appspot.com/o/aac9f071-40b1-4ff1-942b-890458699b07.jpg?alt=media",
                        "Mangue"
                )
        );
        list.add(
                new CarouselItem(
                        "https://firebasestorage.googleapis.com/v0/b/gestionpanneaux.appspot.com/o/9ff56413-5f05-4c65-aacf-56f68ea3e689.jpg?alt=media",
                        "Ananas"
                )
        );


        binder.carousel.setData(list);
        // See kotlin code for details.
        binder.carousel.setAutoPlay(true);
        binder.carousel.setAutoPlayDelay(5000);

        //
        adapter = new AdapterProduit(getContext(), m);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        binder.recyclerproduit.setLayoutManager(layoutManager);
        binder.recyclerproduit.setAdapter(adapter);

        // Read DATA :
        Integer[] donnees = {1, 2, 3, 4, 5};
        getmobileAllProduits();

        // Meilleures offres :
        adapterOff = new AdapterOffre(getContext(), m);
        LinearLayoutManager layoutManagerOffre = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        binder.recycleroffre.setLayoutManager(layoutManagerOffre);
        binder.recycleroffre.setAdapter(adapterOff);

        // Read DATA :
        Arrays.asList(donnees).forEach(
                d -> {
                    adapterOff.addItems(d);
                }
        );

    }

    // Set API :
    private void initProxy() {
        apiProxy = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BCK_URL)
                .client(new RetrofitTool().getClient(false, ""))
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiProxy.class);
    }

    public void getmobileAllProduits(){
        initProxy();
        apiProxy.getmobileAllProduits().enqueue(new Callback<List<Produit>>() {
            @Override
            public void onResponse(Call<List<Produit>> call, Response<List<Produit>> response) {
                binder.shimmerproduit.stopShimmer();
                if (response.code() == 200) {
                    // Now save it :
                    response.body().forEach(p -> {
                        Produit pt = new Produit();
                        pt.setChoix(0);
                        pt.setIdprd(p.getIdprd());
                        pt.setLibelle(p.getLibelle());
                        pt.setLienweb(p.getLienweb());
                        //
                        produitRepository.insert(pt);
                        adapter.addItems(pt);
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
    }

    // On error
    public void onErreur(){
        if(produitRepository.getAll().size() > 0){
            produitRepository.getAll().forEach(
                    d -> {
                        adapter.addItems(d);
                    }
            );
        }
        // Hide :
        binder.shimmerproduit.setVisibility(View.GONE);
        binder.recyclerproduit.setVisibility(View.VISIBLE);
    }
}