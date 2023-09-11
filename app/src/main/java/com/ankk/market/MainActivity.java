package com.ankk.market;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ankk.market.beans.Beanarticlelive;
import com.ankk.market.fragments.FragmentCommande;
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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.core.content.ContextCompat;
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
    FragmentCommande fcm;
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
                        ft = new Fragmentproduit(Modes.PRODUITS);
                        openFragment(ft);
                        break;

                    case R.id.menucategorie:
                        fc = new Fragmentcategorie();
                        openFragment(fc);
                        break;

                    case R.id.menucommande:
                        if(viewmodel.getClientRepository().getAll().isEmpty()){
                            Toast.makeText(getApplicationContext(),
                                    "Veuillez créer un compte !",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            fcm = new FragmentCommande();
                            openFragment(fcm);
                        }
                        /*if(!viewmodel.getCommandeRepository().getAll().isEmpty()) {
                            if (fcm == null) fcm = new FragmentCommande();
                            openFragment(fcm);
                        }
                        else Toast.makeText(getApplicationContext(),
                                "Aucune commande effectuée !",
                                Toast.LENGTH_SHORT).show();*/
                        break;

                    case R.id.menucompte:
                        //if(fct==null) fct = new Fragmentcompte();
                        fct = new Fragmentcompte();
                        openFragment(fct);
                        break;
                }
                return true;
            }
        });

        //
        displayData();

        // Get "EXTRAS" :
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Get EveryTHING :
            int cmd = extras.getInt("commande",0);
            if(cmd > 0){
                fcm = new FragmentCommande();
                openFragment(fcm);
            }
        }
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

        actionViewAlerte.setOnClickListener( d -> onOptionsItemSelected(menuAlerte));
        actionViewPanier.setOnClickListener( d -> onOptionsItemSelected(menuPanier));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionbook:
                if(!viewmodel.getAchatRepository().getAllByActif(1).isEmpty()) {
                    Intent it = new Intent(this, PanierActivity.class);
                    startActivity(it);
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Aucune commande en cours ...",
                            Toast.LENGTH_SHORT).show();
                }
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
        ft = new Fragmentproduit(Modes.PRODUITS);
        openFragment(ft);
        askNotificationPermission();
    }

    // Declare the launcher at the top of your Activity/Fragment:
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                    Toast.makeText(getApplicationContext(),
                            "post notifications !",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                    Toast.makeText(getApplicationContext(),
                            "NO post notifications !",
                            Toast.LENGTH_SHORT).show();
                }
            });

    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }


    private void openFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameproduit, fragment);
        fragmentTransaction.commit();

        /*String fragmentTag = fragment.getClass().getName();
        fragmentManager = getSupportFragmentManager();
        boolean fragmentPopped = fragmentManager
                .popBackStackImmediate(fragmentTag , 0);
        if (!fragmentPopped && fragmentManager.findFragmentByTag(fragmentTag) == null) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack(fragment.getClass().getName()); //getSimpleName());
            fragmentTransaction.add(R.id.frameproduit, fragment);
            fragmentTransaction.commit();
        }*/
    }


    private void notifyArticle(){
        viewmodel.getAllLiveCommande().observe(this, new Observer<List<Achat>>() {
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}