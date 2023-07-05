package com.ankk.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ankk.market.adapters.AdapterDetailProduit;
import com.ankk.market.adapters.AdapterListProduit;
import com.ankk.market.databinding.ActivityMainBinding;
import com.ankk.market.databinding.ActivitySousproduitBinding;

import java.util.Arrays;

public class SousproduitActivity extends AppCompatActivity {

    //
    ActivitySousproduitBinding binder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binder = ActivitySousproduitBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

        setSupportActionBar(binder.toolbarsousproduit);

        /*if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }*/

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        binder.recyclerlibprod.setLayoutManager(layoutManager);
        AdapterListProduit adapter = new AdapterListProduit(this);
        binder.recyclerlibprod.setAdapter(adapter);

        // Read DATA :
        String[] donnees = {"Mangue", "Ananas", "Abricot", "Pomme", "Raisin"};
        Arrays.asList(donnees).forEach(
                d -> {
                    adapter.addItems(d);
                }
        );


        // Detail :
        LinearLayoutManager layoutManagerDetail = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        binder.recyclerdetail.setLayoutManager(layoutManagerDetail);
        AdapterDetailProduit adapterDetailProduit = new AdapterDetailProduit(this);
        binder.recyclerdetail.setAdapter(adapterDetailProduit);

        // Read DATA :
        String[] donneesD = {"Mangue", "Ananas", "Abricot", "Pomme", "Raisin"};
        Arrays.asList(donneesD).forEach(
                d -> {
                    adapterDetailProduit.addItems(d);
                }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sousproduit, menu);
        final MenuItem menuItem = menu.findItem(R.id.actionbookssp);
        View actionView = menuItem.getActionView();

        /*actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });*/

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
}