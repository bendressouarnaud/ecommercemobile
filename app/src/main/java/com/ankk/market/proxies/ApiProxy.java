package com.ankk.market.proxies;

import com.ankk.market.beans.BeanArticleHistoCommande;
import com.ankk.market.beans.BeanArticlestatusrequest;
import com.ankk.market.beans.BeanArticlestatusresponse;
import com.ankk.market.beans.BeanAuthentification;
import com.ankk.market.beans.BeanCommandeProjection;
import com.ankk.market.beans.BeanCommentRequest;
import com.ankk.market.beans.BeanCustomerAuth;
import com.ankk.market.beans.BeanCustomerCreation;
import com.ankk.market.beans.BeanResumeArticleDetail;
import com.ankk.market.beans.Beanarticledatahistory;
import com.ankk.market.beans.Beanarticledetail;
import com.ankk.market.beans.Beanarticlerequest;
import com.ankk.market.beans.Beancategorie;
import com.ankk.market.beans.Beansousproduit;
import com.ankk.market.beans.Beansousproduitarticle;
import com.ankk.market.beans.Detail;
import com.ankk.market.beans.RequestBean;
import com.ankk.market.beans.RequeteBean;
import com.ankk.market.beans.RequeteBeanArticle;
import com.ankk.market.beans.RequeteHistoCommande;
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
    @POST("backendcommerce/getmobilepromotedarticles")
    Call<List<BeanResumeArticleDetail>> getpromotedarticles(@Body RequeteBean rn);

    @GET("backendcommerce/getmobilerecentarticles")
    Call<List<Beanarticledetail>> getrecentarticles();

    //
    @POST("backendcommerce/getmobileallsousproduits")
    Call<List<Beancategorie>> getmobileallsousproduits(@Body RequeteBean rn);

    @POST("backendcommerce/getmobilealldetailsbyidspr")
    Call<List<Detail>> getmobilealldetailsbyidspr(@Body RequeteBean rn);

    @POST("backendcommerce/getmobileallsousproduitsbyidprd")
    Call<List<Beansousproduit>> getmobileallsousproduitsbyidprd(@Body RequeteBean rn);

    @POST("backendcommerce/getarticlesbasedoniddet")
    Call<List<BeanResumeArticleDetail>> getarticlesbasedoniddet(@Body RequeteBean rn);

    // Get DATA base on 'Sous-Produit' lib :
    @POST("backendcommerce/getmobilearticlesBasedonLib")
    Call<List<BeanResumeArticleDetail>> getmobilearticlesBasedonLib(@Body RequestBean rn);

    // Get DETAILS for ARTICLE:
    @POST("backendcommerce/getarticledetails")
    Call<List<BeanArticlestatusresponse>> getarticledetails(@Body BeanArticlestatusrequest data);

    //
    @POST("backendcommerce/getmobileallsousproduitsarticles")
    Call<List<Beansousproduitarticle>> getmobileallsousproduitsarticles(@Body RequeteBean rn);

    //
    @POST("backendcommerce/getmobilealldetailsarticles")
    Call<List<Beansousproduitarticle>> getmobilealldetailsarticles(@Body RequeteBean rn);

    // Get COMMUNES :
    @GET("backendcommerce/getmobileAllCommunes")
    Call<List<Commune>> getmobileAllCommunes();

    // Send user for CREATION :
    @POST("backendcommerce/managecustomer")
    Call<BeanCustomerCreation> managecustomer(@Body Client data);

    @POST("backendcommerce/authenicatemobilecustomer")
    Call<BeanCustomerAuth> authenicatemobilecustomer(@Body BeanAuthentification data);

    @POST("backendcommerce/sendbooking")
    Call<ResponseBooking> sendbooking(@Body Beanarticlerequest data);

    @POST("backendcommerce/getmobilearticleinformationbyidart")
    Call<Beanarticledatahistory> getmobilearticleinformationbyidart(@Body RequeteBeanArticle data);

    @POST("backendcommerce/getmobilehistoricalcommande")
    Call<List<BeanCommandeProjection>> getmobilehistoricalcommande(@Body RequeteBean data);

    @POST("backendcommerce/getcustomercommandearticle")
    Call<BeanArticleHistoCommande> getcustomercommandearticle(@Body RequeteHistoCommande data);

    @POST("backendcommerce/sendmobilecomment")
    Call<RequeteBean> sendmobilecomment(@Body BeanCommentRequest data);
}
