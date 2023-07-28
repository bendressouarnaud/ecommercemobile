package com.ankk.market;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ankk.market.beans.Beanarticlelive;
import com.ankk.market.fragments.Fragmentcategorie;
import com.ankk.market.fragments.Fragmentcompte;
import com.ankk.market.fragments.Fragmentoffre;
import com.ankk.market.fragments.Fragmentproduit;
import com.ankk.market.mesenums.Modes;
import com.ankk.market.models.Achat;
import com.ankk.market.viewmodels.AccueilViewmodel;
import com.ankk.market.viewmodels.DetailViewmodel;
import com.ankk.market.viewmodels.VMFactory;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.ankk.market.databinding.ActivityMainBinding;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    TextView textAlerteCount, textShopCount;
    int mCartItemCount = 11;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragmentproduit ft;
    Fragmentcategorie fc;
    Fragmentcompte fct;
    int [] images = {R.drawable.ganoderma, R.drawable.lipidcare, R.drawable.pinepollen};
    AccueilViewmodel viewmodel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        //binding.appBarMain.appbarlayout.setElevation(0);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));

        // Set the VIEWMODEL :
        viewmodel = new ViewModelProvider(this,
                new VMFactory(getApplication()))
                .get(AccueilViewmodel.class);

        // Set actions on NAVIGATIOn /
        binding.homenavigationview.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.menuaccueil:
                        //Fragmentproduit ft = new Fragmentproduit(Modes.PRODUITS);
                        /*fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameproduit, ft);
                        fragmentTransaction.commit();*/
                        openFragment(ft);
                        break;

                    case R.id.menucategorie:
                        /*Intent it = new Intent(MainActivity.this, SousproduitActivity.class);
                        startActivity(it);*/
                        if(fc==null) fc = new Fragmentcategorie();
                        /*fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameproduit, fc);
                        fragmentTransaction.commit();

                        Fragmentproduit ft = new Fragmentproduit(Modes.PRODUITS);*/
                        openFragment(fc);
                        break;

                    case R.id.menucompte:
                        if(fct==null) fct = new Fragmentcompte();
                        openFragment(fct);
                        break;
                }
                return true;
            }
        });

        // Register lifecycle. For activity this will be lifecycle/getLifecycle() and for fragments it will be viewLifecycleOwner/getViewLifecycleOwner().
        /*binding.maincontent.carousel.registerLifecycle(getLifecycle());
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


        binding.maincontent.carousel.setData(list);
        // See kotlin code for details.
        binding.maincontent.carousel.setAutoPlay(true);
        binding.maincontent.carousel.setAutoPlayDelay(5000); // Milliseconds
        */

        /*binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        displayData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        // Alerte :
        final MenuItem menuAlerte = menu.findItem(R.id.actionalerte);
        View actionViewAlerte = menuAlerte.getActionView();
        textAlerteCount = (TextView) actionViewAlerte.findViewById(R.id.cart_badge);

        // Panier :
        final MenuItem menuPanier = menu.findItem(R.id.actionbook);
        View actionViewPanier = menuPanier.getActionView();
        textShopCount = (TextView) actionViewPanier.findViewById(R.id.cart_badge_shop);

        //setupBadge();
        textAlerteCount.setVisibility(View.GONE);

        // Listener :
        notifyArticle();

        /*actionViewAlerte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuAlerte);
            }
        });*/

        actionViewAlerte.setOnClickListener( d -> onOptionsItemSelected(menuAlerte));
        actionViewPanier.setOnClickListener( d -> onOptionsItemSelected(menuPanier));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionbook:
                Intent it = new Intent(this, PanierActivity.class);
                startActivity(it);
                break;

            case R.id.actionalerte:
                Toast.makeText(getApplicationContext(),
                        "ACTION ALERTE",
                        Toast.LENGTH_SHORT).show();
                break;
        }
        //return super.onOptionsItemSelected(item);
        return true;
    }

    private void setupBadge() {

        if (textAlerteCount != null) {
            if (mCartItemCount == 0) {
                if (textAlerteCount.getVisibility() != View.GONE) {
                    textAlerteCount.setVisibility(View.GONE);
                }
            } else {
                textAlerteCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textAlerteCount.getVisibility() != View.VISIBLE) {
                    textAlerteCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
        return true;
    }*/


    // Display data :
    private void displayData(){
        // Default Fragment


        /*
        // Display products
        Fragmentproduit ft = new Fragmentproduit(Modes.PRODUITS);
        fragmentTransaction.replace(R.id.frameproduit, ft);
        fragmentTransaction.commit();*/
        ft = new Fragmentproduit(Modes.PRODUITS);
        openFragment(ft);
    }

    private void openFragment(Fragment fragment) {
        String fragmentTag = fragment.getClass().getName();

        fragmentManager = getSupportFragmentManager();

        boolean fragmentPopped = fragmentManager
                .popBackStackImmediate(fragmentTag , 0);

        if (!fragmentPopped && fragmentManager.findFragmentByTag(fragmentTag) == null) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack(fragment.getClass().getName()); //getSimpleName());
            fragmentTransaction.add(R.id.frameproduit, fragment);
            fragmentTransaction.commit();
        }
    }


    private void notifyArticle(){
        viewmodel.getAllAchatLive().observe(this, new Observer<List<Achat>>() {
                    @Override
                    public void onChanged(List<Achat> article) {
                        if(MainActivity.this.getLifecycle().getCurrentState()
                                == Lifecycle.State.RESUMED){
                            // Update the cached copy of the words in the adapter.
                            if(article.size() > 0){
                                textShopCount.setText(String.valueOf(article.size()));
                                textShopCount.setVisibility(View.VISIBLE);
                            }
                            else{
                                textShopCount.setVisibility(View.GONE);
                            }
                        }
                    }
                }
        );
    }
}