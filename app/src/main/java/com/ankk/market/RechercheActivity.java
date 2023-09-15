package com.ankk.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
        /*mylist.add("C");
        mylist.add("C++");
        mylist.add("C#");
        mylist.add("Java");
        mylist.add("Advanced java");
        mylist.add("Interview prep with c++");
        mylist.add("Interview prep with java");
        mylist.add("data structures with c");
        mylist.add("data structures with java");*/

        // Set adapter to ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        binder.listView.setAdapter(adapter);
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
                    Toast.makeText(RechercheActivity.this, "Not found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            // This method is overridden to filter the adapter according
            // to a search query when the user is typing search
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
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

    public void getmobileAllCommunes(){
        initProxy();
        apiProxy.getmobileAllCommunes().enqueue(new Callback<List<Commune>>() {
            @Override
            public void onResponse(Call<List<Commune>> call, Response<List<Commune>> response) {
                if (response.code() == 200) {
                    // Now save it :
                    response.body().forEach(p -> {
                        viewmodel.getCommuneRepository().insert(p);
                        data.add(p);
                    });
                    // Notify :
                    getCommune = true;
                }
                //else onErreur();
            }

            @Override
            public void onFailure(Call<List<Commune>> call, Throwable t) {
                //onErreur();
            }
        });
    }
}