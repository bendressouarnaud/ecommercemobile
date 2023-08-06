package com.ankk.market.proxies;

import com.ankk.market.beans.Beanarticledetail;
import com.ankk.market.beans.Beanarticlerequest;
import com.ankk.market.beans.Beancategorie;
import com.ankk.market.beans.Beansousproduit;
import com.ankk.market.beans.Beansousproduitarticle;
import com.ankk.market.beans.RequeteBean;
import com.ankk.market.beans.ResponseBooking;
import com.ankk.market.models.Client;
import com.ankk.market.models.Commune;
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
    Call<List<Beancategorie>> getmobileallsousproduits(@Body RequeteBean rn);

    @POST("backendcommerce/getmobileallsousproduitsbyidprd")
    Call<List<Beansousproduit>> getmobileallsousproduitsbyidprd(@Body RequeteBean rn);

    @POST("backendcommerce/getarticlesbasedoniddet")
    Call<List<Beanarticledetail>> getarticlesbasedoniddet(@Body RequeteBean rn);

    //
    @POST("backendcommerce/getmobileallsousproduitsarticles")
    Call<List<Beansousproduitarticle>> getmobileallsousproduitsarticles(@Body RequeteBean rn);

    // Get COMMUNES :
    @GET("backendcommerce/getmobileAllCommunes")
    Call<List<Commune>> getmobileAllCommunes();

    // Send user for CREATION :
    @POST("backendcommerce/managecustomer")
    Call<Client> managecustomer(@Body Client data);

    @POST("backendcommerce/sendbooking")
    Call<ResponseBooking> sendbooking(@Body Beanarticlerequest data);
}
