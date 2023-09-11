package com.ankk.market;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ankk.market.beans.BeanAuthentification;
import com.ankk.market.beans.BeanCustomerAuth;
import com.ankk.market.beans.BeanCustomerCreation;
import com.ankk.market.databinding.ActivityAuthentificationBinding;
import com.ankk.market.mesobjets.RetrofitTool;
import com.ankk.market.models.Commune;
import com.ankk.market.proxies.ApiProxy;
import com.ankk.market.viewmodels.AuthentificationViewmodel;
import com.ankk.market.viewmodels.VMFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Authentification extends AppCompatActivity {

    // A T T R I B U T E S :
    ActivityAuthentificationBinding binder;
    AlertDialog alertDialogLoadPicture;
    boolean flagCompte = false, flagFcmToken = false, flagSouscription = false, stopProcess = false, compteExiste = false;
    ApiProxy apiProxy;
    AuthentificationViewmodel viewmodel;



    // M E T H O D
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hook :
        binder = DataBindingUtil.setContentView(this, R.layout.activity_authentification);

        // Hide ACTION BAR
        if(getSupportActionBar()!=null)
            getSupportActionBar().hide();

        // Set COLOR BAR
        getWindow().setStatusBarColor(getResources().getColor(R.color.black, null));

        // Set the VIEWMODEL :
        viewmodel = new ViewModelProvider(this,
                new VMFactory(getApplication()))
                .get(AuthentificationViewmodel.class);

        // Set action on 'ENREGISTRER'
        binder.butenregistrerauth.setOnClickListener(d -> checkFields());
        // Set action on 'RETOUR'
        binder.butretourauth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void checkFields(){
        boolean ret = false;
        if(binder.emailcompteinputauth.getText().toString().isEmpty()){
            displayCustomMessage("Veuillez renseigner le MAIL");
            ret = true;
        }
        else if(binder.pwdinput.getText().toString().isEmpty()){
            displayCustomMessage("Veuillez renseigner le Mot de passe");
            ret = true;
        }
        //
        if(!ret){
            enregistrer();
        }
    }

    protected void displayCustomMessage(String message) {
        Snackbar snackbar= Snackbar.make (binder.constraintcompteauth,
                message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    protected void enregistrer(){
        BeanAuthentification bn = new BeanAuthentification();
        bn.setMail(binder.emailcompteinputauth.getText().toString());
        bn.setPwd(binder.pwdinput.getText().toString());
        // Now process :
        processDialog(bn);
    }


    //
    public void processDialog(BeanAuthentification data){
        // We can launch the appropriate METHOD to send the DATA :
        LayoutInflater inflater = LayoutInflater.from(this);
        View vRapport = inflater.inflate(R.layout.layoutpatienterdialog, null);

        // Get OBects :
        TextView textpatienter = vRapport.findViewById(R.id.textpatienter);
        textpatienter.setText( "Authentification");
        ProgressBar progresspatienter = vRapport.findViewById(R.id.progresspatienter);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Synchronisation");

        builder.setIcon(R.mipmap.ic_launcher_final);
        builder.setView(vRapport);
        builder.setCancelable(false);
        alertDialogLoadPicture = builder.create();
        alertDialogLoadPicture.show();

        // Define the HANDLER :
        Handler handlerAsynchLoad = new Handler();
        Runnable runAsynchLoad = new Runnable() {
            @Override
            public void run() {

                if (flagCompte && flagSouscription && flagFcmToken) {
                    // Job done :
                    alertDialogLoadPicture.cancel();
                    handlerAsynchLoad.removeCallbacks(this);

                    if(!compteExiste) {
                        // MOVE ON :
                        finish();
                    }
                    else compteExiste = false;
                }
                else if(stopProcess){
                    // Display message :
                    Snackbar snackbar1 = Snackbar.make(binder.constraintcompteauth, "Erreur survenue !", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                    handlerAsynchLoad.removeCallbacks(this);
                }
                else {
                    handlerAsynchLoad.postDelayed(this, 1000);
                }
            }
        };
        // Call
        getFcmToken(data);

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

    public void managecustomer(BeanAuthentification ct){
        if(apiProxy == null) initProxy();
        apiProxy.authenicatemobilecustomer(ct).enqueue(new Callback<BeanCustomerAuth>() {
            @Override
            public void onResponse(Call<BeanCustomerAuth> call, Response<BeanCustomerAuth> response) {
                //Toast.makeText(getApplicationContext(),"response : "+String.valueOf(response.code()),Toast.LENGTH_SHORT).show();
                if (response.code() == 200) {
                    if(response.body().getFlag() == 0){
                        Snackbar.make(binder.constraintcompteauth,
                                "Impossible de vous authentifier !", Snackbar.LENGTH_LONG).show();
                        compteExiste = true;
                        flagCompte = true;
                    }
                    else {
                        // Now save it :
                        viewmodel.getClientRepository().insert(response.body().getClt());
                        viewmodel.getCommuneRepository().insert(response.body().getCommune().toArray(new Commune[0]));
                        flagCompte = true;
                    }
                }
            }

            @Override
            public void onFailure(Call<BeanCustomerAuth> call, Throwable t) {
                stopProcess = true;
            }
        });
    }

    public void getFcmToken(BeanAuthentification ct){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            stopProcess = true;
                            return;
                        }

                        //Toast.makeText(getApplicationContext(),"FCM obtenu",Toast.LENGTH_SHORT).show();

                        // Get new FCM registration token
                        ct.setFcmtoken(task.getResult().toString());
                        flagFcmToken = true;

                        // Call subscription :
                        subscribeTo("ecommerce", ct);
                    }
                });
    }

    public void subscribeTo(String topic, BeanAuthentification ct){
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "";
                        if (!task.isSuccessful()) {
                            msg = task.getException().toString();
                            stopProcess = true;
                            return;
                        }

                        //
                        //Toast.makeText(getApplicationContext(), "subscribeTo", Toast.LENGTH_LONG).show();

                        if(msg.isEmpty()){
                            //Toast.makeText(getApplicationContext(), "Création l'utilisateur !", Toast.LENGTH_SHORT).show();
                            flagSouscription = true;
                            // Call
                            managecustomer(ct);
                        }
                    }
                });
    }
}