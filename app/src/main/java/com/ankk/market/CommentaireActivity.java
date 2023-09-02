package com.ankk.market;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ankk.market.beans.BeanCommentRequest;
import com.ankk.market.beans.RequeteBean;
import com.ankk.market.beans.RequeteHistoCommande;
import com.ankk.market.databinding.ActivityCommentaireBinding;
import com.ankk.market.databinding.ActivityHistoriqueCommandeBinding;
import com.ankk.market.mesobjets.RetrofitTool;
import com.ankk.market.models.Client;
import com.ankk.market.proxies.ApiProxy;
import com.ankk.market.viewmodels.CommentaireViewmodel;
import com.ankk.market.viewmodels.HistoriqueViewmodel;
import com.ankk.market.viewmodels.VMFactory;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentaireActivity extends AppCompatActivity {


    // A t t r i b u t e s  :
    ActivityCommentaireBinding binder;
    CommentaireViewmodel viewmodel;
    AlertDialog alertDialog;
    ApiProxy apiProxy;



    // M e t h o d s  :
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binder = ActivityCommentaireBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

        getWindow().setStatusBarColor(getResources().getColor(R.color.black, null));

        binder.cmtunnote.setOnClickListener(d -> updateNote(1));
        binder.cmtdeuxnote.setOnClickListener(d -> updateNote(2));
        binder.cmttroixnote.setOnClickListener(d -> updateNote(3));
        binder.cmtquatrenote.setOnClickListener(d -> updateNote(4));
        binder.cmtcinqnote.setOnClickListener(d -> updateNote(5));

        // Set the VIEWMODEL :
        viewmodel = new ViewModelProvider(this,
                new VMFactory(getApplication()))
                .get(CommentaireViewmodel.class);

        // Get "EXTRAS" :
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            viewmodel.setIdart( extras.getInt("idart"));
        }

        binder.butenregistrercmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //
                binder.butenregistrercmt.setEnabled(false);

                BeanCommentRequest bt = new BeanCommentRequest();
                bt.setIdart(viewmodel.getIdart());
                bt.setCommentaire(binder.commentedit.getText().toString());
                bt.setIdcli(viewmodel.getClientRepository().getAll().get(0).getIdcli());
                bt.setNote(viewmodel.getNote());
                // Process :
                processDialog(bt);
            }
        });
    }

    //
    private void updateNote(int position){

        // Set VALUE :
        viewmodel.setNote(position);

        switch (position){
            case 1:
                binder.cmtunnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                //
                binder.cmtdeuxnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                binder.cmttroixnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                binder.cmtquatrenote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                binder.cmtcinqnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                break;

            case 2:
                binder.cmtunnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                binder.cmtdeuxnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                //
                binder.cmttroixnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                binder.cmtquatrenote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                binder.cmtcinqnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                break;

            case 3:
                binder.cmtunnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                binder.cmtdeuxnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                binder.cmttroixnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                //
                binder.cmtquatrenote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                binder.cmtcinqnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                break;

            case 4:
                binder.cmtunnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                binder.cmtdeuxnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                binder.cmttroixnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                binder.cmtquatrenote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                //
                binder.cmtcinqnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_empty, null));
                break;

            case 5:
                binder.cmtunnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                binder.cmtdeuxnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                binder.cmttroixnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                binder.cmtquatrenote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                binder.cmtcinqnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                break;
        }
    }

    public void processDialog(BeanCommentRequest bt){
        // We can launch the appropriate METHOD to send the DATA :
        LayoutInflater inflater = LayoutInflater.from(this);
        View vRapport = inflater.inflate(R.layout.layoutpatienterdialog, null);

        // Get OBects :
        TextView textpatienter = vRapport.findViewById(R.id.textpatienter);
        textpatienter.setText("Enregistrement du commentaire");
        ProgressBar progresspatienter = vRapport.findViewById(R.id.progresspatienter);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Synchronisation");

        builder.setIcon(R.mipmap.ic_launcher);
        builder.setView(vRapport);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();

        // Define the HANDLER :
        Handler handlerAsynchLoad = new Handler();
        Runnable runAsynchLoad = new Runnable() {
            @Override
            public void run() {
                if (viewmodel.getFlag() > 0) {
                    if(viewmodel.getFlag() == 1) {
                        //
                        alertDialog.cancel();
                        handlerAsynchLoad.removeCallbacks(this);

                        // MOVE ON :
                        finish();
                    }
                    else {
                        //
                        alertDialog.cancel();
                        handlerAsynchLoad.removeCallbacks(this);

                        binder.butenregistrercmt.setEnabled(true);
                        try {
                            Snackbar snackbar= Snackbar.make (binder.constraintinterfacecmt,
                                    "Impossible de traiter la demande !", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                        catch (Exception e){}
                    }
                }
                else {
                    handlerAsynchLoad.postDelayed(this, 1000);
                }
            }
        };
        // Call
        managecomment(bt);

        //
        handlerAsynchLoad.postDelayed(runAsynchLoad, 1000);
    }


    private void initProxy() {
        apiProxy = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BCK_URL)
                .client(new RetrofitTool().getClient(false, ""))
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiProxy.class);
    }

    public void managecomment(BeanCommentRequest bt){
        if(apiProxy == null) initProxy();
        apiProxy.sendmobilecomment(bt).enqueue(new Callback<RequeteBean>() {
            @Override
            public void onResponse(Call<RequeteBean> call, Response<RequeteBean> response) {
                //Toast.makeText(getApplicationContext(),"response : "+String.valueOf(response.code()),Toast.LENGTH_SHORT).show();
                if (response.code() == 200) {
                    // Now save it :
                    viewmodel.setFlag(1);
                }
            }

            @Override
            public void onFailure(Call<RequeteBean> call, Throwable t) {
                viewmodel.setFlag(2);
            }
        });
    }
}