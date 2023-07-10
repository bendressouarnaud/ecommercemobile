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

        getWindow().setStatusBarColor(getResources().getColor(R.color.black));

        // Get "EXTRAS" :
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Montant  -- IDMAG :
            /*montant = extras.getInt("montant",0);
            // Display the amount
            binder.editmontantcinet.setText(String.valueOf(montant));
            idmag = extras.getInt("idmag",0);
            mois = extras.getInt("mois",0);
            taxeodp = extras.getInt("taxeodp",0);
            idclj = extras.getInt("idclj",0);*/
        }
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