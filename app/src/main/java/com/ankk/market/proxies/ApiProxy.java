package com.ankk.market.proxies;

import com.ankk.market.beans.Beansousproduit;
import com.ankk.market.beans.RequeteBean;
import com.ankk.market.models.Produit;
import com.ankk.market.models.Sousproduit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiProxy {

    @GET("backendcommerce/getmobileAllProduits")
    Call<List<Produit>> getmobileAllProduits();

    //
    @POST("backendcommerce/getmobileallsousproduits")
    Call<List<Sousproduit>> getmobileallsousproduits(@Body RequeteBean rn);
}
