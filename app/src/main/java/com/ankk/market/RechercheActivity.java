package com.ankk.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.ankk.market.beans.RequestBean;
import com.ankk.market.databinding.ActivityArticleBinding;
import com.ankk.market.databinding.ActivityRechercheBinding;
import com.ankk.market.mesobjets.RetrofitTool;
import com.ankk.market.models.Commune;
import com.ankk.market.proxies.ApiProxy;
import com.ankk.market.viewmodels.AccueilViewmodel;
import com.ankk.market.viewmodels.RechercheViewmodel;
import com.ankk.market.viewmodels.VMFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RechercheActivity extends AppCompatActivity {

    // List View object
    ListView listView;

    // Define array adapter for ListView
    ArrayAdapter<String> adapter;

    // Define array List for List View data
    ArrayList<String> mylist;
    ActivityRechercheBinding binder;
    RechercheViewmodel viewmodel;
    ApiProxy apiProxy;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_recherche);

        binder = ActivityRechercheBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

        setSupportActionBar(binder.toolbarsearch);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black, null));

        // Set the VIEWMODEL :
        viewmodel = new ViewModelProvider(this,
                new VMFactory(getApplication()))
                .get(RechercheViewmodel.class);

        // Add items to Array List
        mylist = new ArrayList<>();
        mylist.addAll(viewmodel.getProduitRepository().getAll().stream().map(d -> d.getLibelle()).collect(Collectors.toList()));

        // Set adapter to ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        binder.listView.setAdapter(adapter);

        // Click on it :
        binder.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(getApplicationContext(), SousproduitActivity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                it.putExtra("mode",5);
                it.putExtra("lib", mylist.get(i));
                startActivity(it);
                // Kill ACTIVITY :
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu with items using MenuInflator
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recherche, menu);

        // Initialise menu item search bar
        // with id and take its object
        MenuItem searchViewItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) searchViewItem.getActionView();
        //searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        View closeButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        // Set on click listener
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Manage this event.
                searchView.setQuery("", false);
                searchView.clearFocus();
                mylist.clear();
                mylist.addAll(viewmodel.getProduitRepository().getAll().stream().map(
                        d -> d.getLibelle()).collect(Collectors.toList()));
                adapter.clear();
                adapter.addAll(mylist);
                binder.listView.setAdapter(adapter);
            }
        });

        // attach setOnQueryTextListener
        // to search view defined above
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // Override onQueryTextSubmit method which is call when submit query is searched
            @Override
            public boolean onQueryTextSubmit(String query) {
                // If the list contains the search query than filter the adapter
                // using the filter method with the query as its argument
                if (mylist.contains(query)) {
                    adapter.getFilter().filter(query);
                } else {
                    // Search query not found in List View
                    //Toast.makeText(RechercheActivity.this, "Not found", Toast.LENGTH_LONG).show();
                    if(!(mylist.contains(query)) && (query.length() > 1)) getDataRequested(query);
                }
                return false;
            }

            // This method is overridden to filter the adapter according
            // to a search query when the user is typing search
            @Override
            public boolean onQueryTextChange(String newText) {
                if(!mylist.contains(newText)){
                    if(newText.length() > 1){
                        getDataRequested(newText);
                    }
                }
                else adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void initProxy() {
        apiProxy = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BCK_URL)
                .client(new RetrofitTool().getClient(false, ""))
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiProxy.class);
    }

    public void getDataRequested(String text){
        if(apiProxy == null) initProxy();

        RequestBean rn = new RequestBean();
        rn.setLib(text);
        rn.setId(0);

        apiProxy.lookforuserrequest(rn).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.code() == 200) {
                    // clear adapter :
                    mylist.clear();
                    mylist.addAll(response.body());
                    adapter.clear();
                    adapter.addAll(response.body());
                    binder.listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                //onErreur();
            }
        });
    }
}