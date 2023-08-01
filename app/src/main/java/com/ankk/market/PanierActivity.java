package com.ankk.market;

import android.os.Bundle;

import com.ankk.market.adapters.AdapterListPanier;
import com.ankk.market.beans.BeanActif;
import com.ankk.market.beans.Beanarticledetail;
import com.ankk.market.beans.Beanarticlelive;
import com.ankk.market.models.Achat;
import com.ankk.market.repositories.AchatRepository;
import com.ankk.market.repositories.BeanarticledetailRepository;
import com.ankk.market.viewmodels.ClientViewmodel;
import com.ankk.market.viewmodels.PanierViewmodel;
import com.ankk.market.viewmodels.VMFactory;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.ankk.market.databinding.ActivityPanierBinding;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PanierActivity extends AppCompatActivity {

    // A T T R I B U T E S :
    private AppBarConfiguration appBarConfiguration;
    private ActivityPanierBinding binder;
    int nbreArticleGobal=0, nbreArticleIdentique=0;
    long prixTotal = 0;
    PanierViewmodel viewmodel;



    // M e t h o d s :
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binder = ActivityPanierBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        setSupportActionBar(binder.toolbarpanier);

        getWindow().setStatusBarColor(getResources().getColor(R.color.black));

        // Set the VIEWMODEL :
        viewmodel = new ViewModelProvider(this,
                new VMFactory(getApplication()))
                .get(PanierViewmodel.class);

        //
        //adapter = new AdapterListPanier(getApplicationContext());
        binder.layoutpanier.recyclerviewpanier.setAdapter(viewmodel.getAdapter());

        // Get ACHAT DATA :
        viewmodel.getAllLiveActif().observe(this, new Observer<List<BeanActif>>() {
                    @Override
                    public void onChanged(List<BeanActif> achat) {
                        if(PanierActivity.this.getLifecycle().getCurrentState()
                                == Lifecycle.State.RESUMED){
                            if(viewmodel.getAdapter().getItemCount() > 0){
                                // Clean :
                                viewmodel.getAdapter().clearEverything();
                            }
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

        binder.layoutpanier.textpanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Get DATA :
        /*beanarticledetailRepository.getAll().forEach(
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
        );*/
    }

}