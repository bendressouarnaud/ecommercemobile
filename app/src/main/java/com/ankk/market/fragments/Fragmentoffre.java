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
import androidx.recyclerview.widget.RecyclerView;

import com.ankk.market.R;
import com.ankk.market.adapters.AdapterProduit;
import com.ankk.market.databinding.FragmentoffreBinding;
import com.ankk.market.mesenums.Modes;

import java.util.Arrays;

public class Fragmentoffre extends Fragment {

    // A t t r i b u t e s :
    AdapterProduit adapter;
    FragmentoffreBinding binder;
    Modes m;


    // M e t h o d s :
    public Fragmentoffre() {
    }

    public Fragmentoffre(Modes m) {
        this.m = m;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binder = DataBindingUtil.inflate(inflater,
                m == Modes.OFFRES ? R.layout.fragmentoffre : R.layout.fragmentproduit,
                container,
                false);
        return binder.getRoot();
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = getView().findViewById(m == Modes.OFFRES ? R.id.recycleroffre : R.id.recyclerproduit);
        recyclerView.setLayoutManager(layoutManager);

        //
        adapter = new AdapterProduit(getContext(), m);
        //binder.recycleroffre.setAdapter(adapter);
        recyclerView.setAdapter(adapter);

        // Read DATA :
        Integer[] donnees = {1, 2, 3, 4, 5};
        /*Arrays.asList(donnees).forEach(
                d -> {
                    adapter.addItems(d);
                }
        );*/
    }
}