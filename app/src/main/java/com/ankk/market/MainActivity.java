package com.ankk.market;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.ankk.market.fragments.Fragmentcategorie;
import com.ankk.market.fragments.Fragmentoffre;
import com.ankk.market.fragments.Fragmentproduit;
import com.ankk.market.mesenums.Modes;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
    TextView textCartItemCount;
    int mCartItemCount = 10;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragmentproduit ft;
    Fragmentcategorie fc;
    int [] images = {R.drawable.ganoderma, R.drawable.lipidcare, R.drawable.pinepollen};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        //binding.appBarMain.appbarlayout.setElevation(0);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));

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

        final MenuItem menuItem = menu.findItem(R.id.action_settings);

        View actionView = menuItem.getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case 3: {
                // Do something
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
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
}