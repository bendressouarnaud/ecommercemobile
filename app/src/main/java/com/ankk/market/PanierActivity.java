package com.ankk.market;

import android.os.Bundle;

import com.ankk.market.adapters.AdapterListPanier;
import com.ankk.market.beans.Beanarticledetail;
import com.ankk.market.beans.Beanarticlelive;
import com.ankk.market.models.Achat;
import com.ankk.market.repositories.AchatRepository;
import com.ankk.market.repositories.BeanarticledetailRepository;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.ankk.market.databinding.ActivityPanierBinding;

import java.util.List;

public class PanierActivity extends AppCompatActivity {

    // A T T R I B U T E S :
    private AppBarConfiguration appBarConfiguration;
    private ActivityPanierBinding binder;
    AdapterListPanier adapter;
    BeanarticledetailRepository beanarticledetailRepository;
    AchatRepository achatRepository;



    // M e t h o d s :
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binder = ActivityPanierBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        setSupportActionBar(binder.toolbarpanier);

        getWindow().setStatusBarColor(getResources().getColor(R.color.black));

        //
        adapter = new AdapterListPanier(getApplicationContext());
        binder.layoutpanier.recyclerviewpanier.setAdapter(adapter);

        //
        beanarticledetailRepository = new BeanarticledetailRepository(getApplication());
        achatRepository = new AchatRepository(getApplication());
        // Get DATA :
        beanarticledetailRepository.getAll().forEach(
            d -> {
                // Map :
                Beanarticlelive be = new Beanarticlelive();
                be.setIdart(d.getIdart());
                be.setPrix(d.getPrix());
                be.setReduction(d.getReduction());
                be.setNote(d.getNote());
                be.setArticlerestant(d.getArticlerestant());
                be.setLibelle(d.getLibelle());
                be.setLienweb(d.getLienweb());
                // Article reserve :
                List<Achat> getAchat = achatRepository.getAllByIdart(d.getIdart());
                be.setArticlereserve(getAchat != null ? getAchat.size() : 0);
                adapter.addItems(be);
            }
        );
    }

}