package com.ankk.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ankk.market.adapters.AdapterListArticle;
import com.ankk.market.beans.Beanarticledatahistory;
import com.ankk.market.beans.Beanarticledetail;
import com.ankk.market.beans.Beanarticlelive;
import com.ankk.market.beans.RequeteBean;
import com.ankk.market.beans.RequeteBeanArticle;
import com.ankk.market.databinding.ActivityArticleBinding;
import com.ankk.market.databinding.ActivitySousproduitBinding;
import com.ankk.market.mesobjets.RetrofitTool;
import com.ankk.market.models.Achat;
import com.ankk.market.models.Client;
import com.ankk.market.proxies.ApiProxy;
import com.ankk.market.viewmodels.AccueilViewmodel;
import com.ankk.market.viewmodels.ArticleViewmodel;
import com.ankk.market.viewmodels.VMFactory;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleActivity extends AppCompatActivity {

    // A t t r i b u t e s :
    ActivityArticleBinding binder;
    ApiProxy apiProxy;
    ArticleViewmodel viewmodel;
    int nbreCommande = 0;
    TextView textPanierCount;



    // M e t h o d s :
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_article);

        binder = ActivityArticleBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

        setSupportActionBar(binder.toolbararticle);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black, null));

        // Set the VIEWMODEL :
        viewmodel = new ViewModelProvider(this,
                new VMFactory(getApplication()))
                .get(ArticleViewmodel.class);

        binder.toolbararticle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kill
                finish();
            }
        });

        // Get "EXTRAS" :
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int idart = extras.getInt("idart",0);
            viewmodel.setIdart(idart);

            // In case the ARTICLE has been already BOOKED, take it into account :
            Achat act = viewmodel.getAchatRepository().getAllByIdartAndActif(idart, 1).stream().findFirst().orElse(null);
            if(act != null){
                // Hide articlebutart :
                binder.articlebutart.setVisibility(View.GONE);
                // Update the number of ARTICLES :
                binder.quantitearticleart.setText(String.valueOf(viewmodel.getAchatRepository().getAllByIdartAndActif(idart, 1).size()));
            }
            else{
                // Hide BUTTONS - & + and TEXT
                binder.articlebutplusart.setVisibility(View.INVISIBLE);
                binder.articlebutmoinsart.setVisibility(View.INVISIBLE);
                binder.quantitearticleart.setVisibility(View.INVISIBLE);
            }

            // Add actions :
            binder.articlebutart.setOnClickListener( d -> addarticle(idart, 1));
            binder.articlebutplusart.setOnClickListener( d -> addarticle(idart, 1));
            binder.articlebutmoinsart.setOnClickListener( d -> addarticle(idart, -1));

            //
            binder.butaddcomment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(ArticleActivity.this, CommentaireActivity.class);
                    it.putExtra("idart", idart);
                    startActivity(it);
                }
            });

            // idart :
            getmobilearticleinformationbyidart(idart);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.article, menu);
        final MenuItem menuItemPanier = menu.findItem(R.id.articlebookicon);
        View actionViewPanier = menuItemPanier.getActionView();
        textPanierCount = (TextView) actionViewPanier.findViewById(R.id.cart_badge_shop);

        // Hide :
        textPanierCount.setVisibility(View.GONE);

        actionViewPanier.setOnClickListener( d -> onOptionsItemSelected(menuItemPanier));

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.articlebookicon:
                if(!viewmodel.getAchatRepository().getAllByActif(1).isEmpty()) {
                    // Set FLAG
                    viewmodel.setValideCommande(true);
                    Intent it = new Intent(this, PanierActivity.class);
                    startActivity(it);
                }
                else
                    Toast.makeText(getApplicationContext(), "Aucune commande en cours ...", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


    private void initProxy() {
        apiProxy = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BCK_URL)
                .client(new RetrofitTool().getClient(false, ""))
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiProxy.class);
    }


    public void getmobilearticleinformationbyidart(int idprd){
        if(apiProxy ==null) initProxy();
        // Set Object :
        RequeteBeanArticle rn = new RequeteBeanArticle();
        rn.setIdart(idprd);
        Client ct = viewmodel.getClientRepository().getAll().stream().findFirst().orElse(null);
        rn.setIduser(ct != null ? ct.getIdcli() : 0);

        apiProxy.getmobilearticleinformationbyidart(rn).enqueue(new Callback<Beanarticledatahistory>() {
            @Override
            public void onResponse(Call<Beanarticledatahistory> call, Response<Beanarticledatahistory> response) {
                // STOP SIMMMER :

                if (response.code() == 200) {
                    // Now save it if NOT EXIST:
                    Beanarticledetail bl =
                            viewmodel.getBeanarticledetailRepository().getItem(viewmodel.getIdart());
                    if(bl == null){
                        bl = new Beanarticledetail();
                        bl.setIdart(idprd);
                        bl.setIddet(response.body().getIddet());
                        bl.setPrix(response.body().getPrix());
                        bl.setReduction(response.body().getReduction());
                        bl.setNote(response.body().getNote());
                        bl.setArticlerestant(response.body().getNombrearticle());
                        bl.setLibelle(response.body().getArticle());
                        bl.setLienweb(response.body().getImages().get(
                                response.body().getImages().size() - 1
                        ).getLienweb());
                        // Save IT :
                        viewmodel.getBeanarticledetailRepository().insert(bl);
                    }

                    // Call ADAPTER
                    binder.shimarticleloading.stopShimmer();
                    binder.shimarticleloading.setVisibility(View.GONE);
                    binder.constraintcontenuarticle.setVisibility(View.VISIBLE);

                    // Set DATA :
                    binder.carouselarticle.registerLifecycle(getLifecycle());
                    List<CarouselItem> list = new ArrayList<>();
                    // Image URL with caption
                    response.body().getImages().forEach(
                        d -> {
                            list.add(
                                    new CarouselItem(
                                            "https://firebasestorage.googleapis.com/v0/b/gestionpanneaux.appspot.com/o/"+d.getLienweb()+"?alt=media",
                                            ""
                                    )
                            );
                        }
                    );

                    binder.carouselarticle.setData(list);

                    binder.textarticle.setText(response.body().getArticle());
                    binder.nomentreprisearticle.setText(response.body().getEntreprise());

                    // Get PRICE :
                    if(response.body().getReduction() > 0){
                        binder.prixreductionaticle.setText(
                                NumberFormat.getInstance(Locale.FRENCH).format(response.body().getPrix())+" FCFA"
                        );
                        binder.prixreductionaticle.setPaintFlags(
                                binder.prixreductionaticle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
                        );

                        binder.textarticlepourcentage.setText("-"+String.valueOf(response.body().getReduction())+"%");
                        // Compute PRICE :
                        float tpPrice = ((response.body().getPrix() * response.body().getReduction()) / 100);
                        binder.prixarticle.setText(
                                NumberFormat.getInstance(Locale.FRENCH).format(response.body().getPrix() - tpPrice)+" FCFA");
                    }
                    else {
                        binder.prixreductionaticle.setVisibility(View.GONE);
                        binder.textarticlepourcentage.setVisibility(View.GONE);
                        binder.prixarticle.setText(NumberFormat.getInstance(Locale.FRENCH).format(response.body().getPrix()) + " FCFA");
                    }

                    // Article restant , get the ONE that are being booked :
                    Achat act = viewmodel.getAchatRepository().getAllByIdartAndActif(viewmodel.getIdart(),
                            1).stream().findFirst().orElse(null);
                    int articleAdeduire = 0;
                    if(act != null){
                        //
                        articleAdeduire = viewmodel.getAchatRepository().getAllByIdartAndActif(
                                viewmodel.getIdart(), 1).size();
                    }

                    viewmodel.setArticleRestant(response.body().getNombrearticle() - articleAdeduire);
                    if((response.body().getNombrearticle() - articleAdeduire) > 5){
                        // Hide :
                        binder.imgalertearticle.setImageDrawable(
                                getResources().getDrawable(R.drawable.ic_article_restant, null));
                        // Change COLOR :
                        binder.textarticlerestant.setTextColor(getResources().getColor(R.color.color_green, null));
                    }
                    binder.textarticlerestant.setText(String.valueOf(response.body().getNombrearticle() - articleAdeduire) + " articles restants");


                    // Contenu du TEXT :
                    binder.textcontenudetail.setText(response.body().getDescriptionproduit());

                    // Modalité :
                    binder.contenumodaliteretour.setText(response.body().getModaliteretour());

                    // Define ACTION on TELEPHONE Icon :
                    binder.articleimgcall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Uri number = Uri.parse("tel:"+response.body().getContact());
                            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                            startActivity(callIntent);
                        }
                    });

                    // Display BUTTON if 'needed' :
                    if((response.body().getAutorisecommentaire() == 1) &&
                            (response.body().getCommentaireexiste() == 0))
                        binder.butaddcomment.setVisibility(View.VISIBLE);
                    else binder.butaddcomment.setVisibility(View.GONE);

                    // Now process the LIST of COMMENTS :
                    if(!response.body().getComments().isEmpty()){

                        // Comments number :
                        int totalComment = response.body().getComments().size();
                        // Average :
                        double noteMoy = response.body().getComments().stream().mapToInt(d -> d.getNote()).average().getAsDouble();
                        // Update :
                        binder.noteevaluation.setText( String.valueOf(noteMoy)+"/"+String.valueOf(totalComment));
                        binder.avisclients.setText( "("+ String.valueOf(totalComment)+ " Avis client(s))");

                        binder.recyclerviewarticle.setAdapter(viewmodel.getAdapterCommentaireArticle());
                        response.body().getComments().forEach(
                                d -> {
                                    viewmodel.getAdapterCommentaireArticle().addItems(d);
                                }
                        );

                        // Now hide :
                        binder.constraintnocomment.setVisibility(View.GONE);
                        // Display :
                        binder.recyclerviewarticle.setVisibility(View.VISIBLE);

                        // Set STAR :
                        // Set NOTE
                        if(noteMoy == 1) {
                            binder.artstarun.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                            //
                            binder.artstardeux.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                            binder.artstartroix.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                            binder.artstarquatre.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                            binder.artstarcinq.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                        }
                        else if(noteMoy == 2) {
                            binder.artstarun.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                            binder.artstardeux.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                            //
                            binder.artstartroix.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                            binder.artstarquatre.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                            binder.artstarcinq.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                        }
                        else if(noteMoy == 3) {
                            binder.artstarun.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                            binder.artstardeux.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                            binder.artstartroix.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                            //
                            binder.artstarquatre.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                            binder.artstarcinq.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                        }
                        else if(noteMoy == 4) {
                            binder.artstarun.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                            binder.artstardeux.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                            binder.artstartroix.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                            binder.artstarquatre.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                            //
                            binder.artstarcinq.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                        }
                        else if(noteMoy == 5) {
                            binder.artstarun.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                            binder.artstardeux.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                            binder.artstartroix.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                            binder.artstarquatre.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                            binder.artstarcinq.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                        }
                        else{
                            if((noteMoy > 1) && (noteMoy < 2)){
                                binder.artstarun.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                                binder.artstardeux.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_half_full, null));
                                //
                                binder.artstartroix.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                                binder.artstarquatre.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                                binder.artstarcinq.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                            }
                            else if((noteMoy > 2) && (noteMoy < 3)){
                                binder.artstarun.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                                binder.artstardeux.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                                binder.artstartroix.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_half_full, null));
                                //
                                binder.artstarquatre.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                                binder.artstarcinq.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                            }
                            else if((noteMoy > 3) && (noteMoy < 4)){
                                binder.artstarun.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                                binder.artstardeux.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                                binder.artstartroix.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                                binder.artstarquatre.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_half_full, null));
                                //
                                binder.artstarcinq.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                            }
                            else if(noteMoy > 4){
                                binder.artstarun.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                                binder.artstardeux.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                                binder.artstartroix.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                                binder.artstarquatre.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                                binder.artstarcinq.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_half_full, null));
                            }
                        }
                    }
                    else {
                        // Update :
                        binder.noteevaluation.setText( "--/--");
                        binder.avisclients.setText( "(0 Avis client)");
                    }

                    // Notify :
                    notifyUser();
                }
                //else onErreur();
            }

            @Override
            public void onFailure(Call<Beanarticledatahistory> call, Throwable t) {
                //Toast.makeText(ArticleActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addarticle(int idart, int nbreElement){
        // Update field :
        Beanarticledetail bl = viewmodel.getBeanarticledetailRepository().getItem(viewmodel.getIdart());
        if(bl != null) {
            int articleRestant = viewmodel.getArticleRestant(); //  bl.getArticlerestant();
            int deduction = articleRestant - nbreElement;
            bl.setArticlerestant(deduction);
            viewmodel.getBeanarticledetailRepository().update(bl);
            viewmodel.setArticleRestant(deduction);

            // First HIDE texteview :
            //binder.quantitearticleart.setVisibility(View.GONE);
            binder.progressarticlesingle.setVisibility(View.VISIBLE);

            // Pick ARTICLE BOOKED :
            List<Achat> lCommande = viewmodel.getAchatRepository().getAllByIdartAndActif(idart, 1);
            nbreCommande = 0;
            if (lCommande != null) {
                nbreCommande = lCommande.size();
            }

            // Set :
            if (nbreElement == 1) nbreCommande++;
            else nbreCommande--;

            // Hit ACHAT :
            if (nbreElement == 1) {

                // Disable but +
                //if((articleRestant - nbreElement) == nbreCommande){
                if(deduction == 0){
                    binder.articlebutplusart.setEnabled(false);
                    binder.articlebutmoinsart.setEnabled(true);
                }

                Achat at = new Achat();
                at.setActif(1);
                at.setIdart(idart);
                viewmodel.getAchatRepository().insert(at);

                // Check in case '-' button was disabled :
                if(!binder.articlebutmoinsart.isEnabled()) binder.articlebutmoinsart.setEnabled(true);
            } else {

                // Check in case '-' button was disabled :
                if(!binder.articlebutplusart.isEnabled()) binder.articlebutplusart.setEnabled(true);

                // Delete the last ACHATS :
                viewmodel.getAchatRepository().delete(lCommande.get(0));
                /*if (nbreCommande == 0) {
                    binder.articlebutmoinsart.setEnabled(false);
                    binder.articlebutplusart.setEnabled(true);
                }*/
            }

            // Update :
            //beanarticledetailRepository.insert(bl);

            // Define the HANDLER :
            Handler handlerAsynchLoad = new Handler();
            Runnable runAsynchLoad = new Runnable() {
                @Override
                public void run() {
                    handlerAsynchLoad.removeCallbacks(this);

                    // Now, display ARTICLE requested :
                    binder.textarticlerestant.setText(
                            String.valueOf(viewmodel.getArticleRestant()) +
                                    " article(s) restant(s)");
                    // Update
                    binder.quantitearticleart.setText(String.valueOf(nbreCommande));
                    binder.progressarticlesingle.setVisibility(View.GONE);
                    if(binder.quantitearticleart.getVisibility() != View.VISIBLE)
                        binder.quantitearticleart.setVisibility(View.VISIBLE);

                    if(nbreCommande == 0){
                        binder.articlebutart.setVisibility(View.VISIBLE);

                        binder.articlebutmoinsart.setVisibility(View.GONE);
                        binder.articlebutplusart.setVisibility(View.GONE);
                        binder.quantitearticleart.setVisibility(View.GONE);
                    }
                    else {
                        binder.articlebutmoinsart.setVisibility(View.VISIBLE);
                        binder.articlebutplusart.setVisibility(View.VISIBLE);
                        binder.articlebutart.setVisibility(View.GONE);
                    }

                    // Hide :
                    binder.constraintnotifyarticlenew.setVisibility(View.INVISIBLE);
                }
            };

            // Display :
            binder.constraintnotifyarticlenew.setVisibility(View.VISIBLE);

            //
            handlerAsynchLoad.postDelayed(runAsynchLoad, 2500);
        }
    }


    public void notifyUser(){
        viewmodel.getAchatRepository().getAllLiveCommande().observe(this, d->{
            // Display SIZE :
            if( d.size() > 0){
                textPanierCount.setText(String.valueOf(d.size()));
                if(textPanierCount.getVisibility() != View.VISIBLE){
                    textPanierCount.setVisibility(View.VISIBLE);
                }
            }
            else textPanierCount.setVisibility(View.INVISIBLE);

            if(d.isEmpty() && viewmodel.isValideCommande()){
                // Close the DOORS, meaning PANIER is 'empty' :
                finish();
            }
        });
    }
}