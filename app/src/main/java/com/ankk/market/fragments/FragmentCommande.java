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

import com.ankk.market.OpenApplication;
import com.ankk.market.R;
import com.ankk.market.adapters.AdapterDetailProduit;
import com.ankk.market.adapters.AdapterListCommande;
import com.ankk.market.adapters.AdapterListProduit;
import com.ankk.market.beans.BeanCommande;
import com.ankk.market.databinding.FragmentcommandeBinding;
import com.ankk.market.models.Produit;
import com.ankk.market.repositories.CommandeRepository;
import com.ankk.market.viewmodels.FragmentproduitViewmodel;
import com.ankk.market.viewmodels.VMFactory;

import java.util.List;

public class FragmentCommande extends Fragment {

    // A t t r i b u t e s :
    OpenApplication app;
    FragmentcommandeBinding binder;
    CommandeRepository commandeRepository;


    // M e t h o d s  :
    public FragmentCommande() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //
        app = (OpenApplication) getActivity().getApplication();
        commandeRepository = new CommandeRepository(app);
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

        // Get DATA :
        List<BeanCommande> liste = commandeRepository.getAllGroupByDatesHeure();
        AdapterListCommande adapter = new AdapterListCommande(getContext());
        binder.recyclerfragcommande.setAdapter(adapter);

        // Attach DATA :
        liste.forEach(adapter::addItems);
    }

}
