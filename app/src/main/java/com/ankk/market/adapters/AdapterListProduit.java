package com.ankk.market.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ankk.market.BuildConfig;
import com.ankk.market.OpenApplication;
import com.ankk.market.R;
import com.ankk.market.beans.Beancategorie;
import com.ankk.market.beans.RequeteBean;
import com.ankk.market.databinding.CardviewlisteproduitBinding;
import com.ankk.market.databinding.CardviewoffreBinding;
import com.ankk.market.fragments.Fragmentcategorie;
import com.ankk.market.mesenums.Modes;
import com.ankk.market.mesobjets.RetrofitTool;
import com.ankk.market.models.Produit;
import com.ankk.market.models.Sousproduit;
import com.ankk.market.proxies.ApiProxy;
import com.ankk.market.repositories.SousproduitRepository;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterListProduit extends RecyclerView.Adapter<AdapterListProduit.ListeViewHolder> {


    // Attributes :
    private final Context context;
    private List<Produit> donnee;
    ApiProxy apiProxy;
    SousproduitRepository repository;
    OpenApplication app;



    public AdapterListProduit(Context context) {
        this.context = context;
        donnee = new ArrayList<>();
        app = (OpenApplication) context.getApplicationContext();
        repository = new SousproduitRepository(app);
    }


    @Override
    public void onBindViewHolder(@NonNull ListeViewHolder holder, int position) {
        // Get DATA :
        holder.binder.libproduit.setText(donnee.get(position).getLibelle());
        if(position == 0) holder.binder.viewproduitname.setVisibility(View.VISIBLE);
        // Set ACTION :
        holder.binder.libproduit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getmobileallsousproduits(donnee.get(position).getIdprd());
            }
        });
    }


    @NonNull
    @Override
    public ListeViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                           int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new ListeViewHolder(DataBindingUtil.inflate(inflater,
                R.layout.cardviewlisteproduit,
                parent,
                false));
    }


    static class ListeViewHolder extends RecyclerView.ViewHolder {
        CardviewlisteproduitBinding binder;

        public ListeViewHolder(CardviewlisteproduitBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }
    }

    @Override
    public int getItemCount() {
        return donnee.size();
    }

    public void addItems(Produit data) {
        donnee.add(data);
        notifyItemInserted(donnee.size() - 1);
    }


    private void initProxy() {
        apiProxy = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BCK_URL)
                .client(new RetrofitTool().getClient(false, ""))
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiProxy.class);
    }


    public void getmobileallsousproduits(int idprd){
        if(apiProxy ==null) initProxy();
        // Set Object :
        RequeteBean rn = new RequeteBean();
        rn.setIdprd(idprd);

        apiProxy.getmobileallsousproduits(rn).enqueue(new Callback<List<Beancategorie>>() {
            @Override
            public void onResponse(Call<List<Beancategorie>> call, Response<List<Beancategorie>> response) {
                // STOP SIMMMER :
                Toast.makeText(context, "AZERTY : "+String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                if (response.code() == 200) {
                    // Now save it :

                    // Notify FRAGMENT :
                    Fragmentcategorie.notifyNewSousProduit(response.body());
                }
                //else onErreur();
            }

            @Override
            public void onFailure(Call<List<Beancategorie>> call, Throwable t) {
                Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
