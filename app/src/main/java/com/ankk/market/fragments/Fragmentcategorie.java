package com.ankk.market.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ankk.market.OpenApplication;
import com.ankk.market.R;
import com.ankk.market.adapters.AdapterDetailProduit;
import com.ankk.market.adapters.AdapterListProduit;
import com.ankk.market.databinding.FragmentcategorieBinding;
import com.ankk.market.repositories.ProduitRepository;

import java.util.Arrays;

public class Fragmentcategorie extends Fragment {

    // A t t r i b u t e s :
    OpenApplication app;
    FragmentcategorieBinding binder;



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

        //
        binder.shimmerlibprod.startShimmer();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        binder.recyclerfraglibprod.setLayoutManager(layoutManager);
        AdapterListProduit adapter = new AdapterListProduit(getContext());
        binder.recyclerfraglibprod.setAdapter(adapter);

        // Read DATA :
        String[] donnees = {"Mangue", "Ananas", "Abricot", "Pomme", "Raisin"};
        Arrays.asList(donnees).forEach(
                d -> {
                    adapter.addItems(d);
                }
        );


        // Detail :
        LinearLayoutManager layoutManagerDetail = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        binder.recyclerfragdetail.setLayoutManager(layoutManagerDetail);
        AdapterDetailProduit adapterDetailProduit = new AdapterDetailProduit(getContext());
        binder.recyclerfragdetail.setAdapter(adapterDetailProduit);

        // Read DATA :
        String[] donneesD = {"Mangue", "Ananas", "Abricot", "Pomme", "Raisin"};
        Arrays.asList(donneesD).forEach(
                d -> {
                    adapterDetailProduit.addItems(d);
                }
        );
    }
}
