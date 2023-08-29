package com.ankk.market;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ankk.market.databinding.ActivityCompteBinding;
import com.ankk.market.mesobjets.RetrofitTool;
import com.ankk.market.models.Client;
import com.ankk.market.models.Commune;
import com.ankk.market.proxies.ApiProxy;
import com.ankk.market.viewmodels.AccueilViewmodel;
import com.ankk.market.viewmodels.ClientViewmodel;
import com.ankk.market.viewmodels.VMFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompteActivity extends AppCompatActivity {

    // A T T R I B U T E S :
    ActivityCompteBinding binder;
    ClientViewmodel viewmodel;
    String[] libCommunes;
    int i_commune = 0;
    AlertDialog alertDialogLoadPicture;
    boolean flagCompte = false, flagFcmToken = false, flagSouscription = false;
    ApiProxy apiProxy;
    String fcmtoken;
    boolean stopProcess = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hook :
        binder = DataBindingUtil.setContentView(this, R.layout.activity_compte);

        // Hide ACTION BAR
        if(getSupportActionBar()!=null)
            getSupportActionBar().hide();

        // Set COLOR BAR
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));

        // Set the VIEWMODEL :
        viewmodel = new ViewModelProvider(this,
                new VMFactory(getApplication()))
                .get(ClientViewmodel.class);

        // Les COMMUNES
        libCommunes = new String[viewmodel.getAllCommune().size()];
        viewmodel.getAllCommune().stream().map(
                d -> d.getLibelle()).collect(Collectors.toList()).toArray(libCommunes);
        // Init dropdownlist :
        ArrayAdapter adapterCommune = new ArrayAdapter(this,android.R.layout.simple_list_item_1, libCommunes);
        binder.villecompteautocomp.setAdapter(adapterCommune);
        binder.villecompteautocomp.setText(adapterCommune.getItem(0).toString(), false);
        binder.villecompteautocomp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                i_commune = position;
            }
        });

        // Set action on 'ENREGISTRER'
        binder.butenregistrer.setOnClickListener(d -> checkFields());
        // Set action on 'RETOUR'
        binder.butretour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // In case we HAVE already DATA :
        if(!viewmodel.getCompte().isEmpty()){
            // Display :
            Client ct = viewmodel.getCompte().get(0);
            binder.nomcompteinput.setText(ct.getNom());
            binder.prenomcompteinput.setText(ct.getPrenom());
            binder.emailcompteinput.setText(ct.getEmail());
            binder.numerocompteinput.setText(ct.getNumero());
            binder.adressecompteinput.setText(ct.getAdresse());
            if(ct.getGenre() == 0) binder.radiof.setChecked(true);
            else binder.radiom.setChecked(true);
            //
            List<Commune> lteC = viewmodel.getAllCommune();
            for (int j=0; j < lteC.size(); j++){
                if(ct.getCommune() == lteC.get(j).getIdcom()){
                    binder.villecompteautocomp.setText(adapterCommune.getItem(j).toString(), false);
                    i_commune = j;
                    break;
                }
            }
        }
    }

    protected void checkFields(){
        boolean ret = false;
        if(binder.nomcompteinput.getText().toString().isEmpty()){
            displayCustomMessage("Veuillez renseigner le NOM");
            ret = true;
        }
        else if(binder.prenomcompteinput.getText().toString().isEmpty()){
            displayCustomMessage("Veuillez renseigner le PRENOM");
            ret = true;
        }
        else if(binder.emailcompteinput.getText().toString().isEmpty()){
            displayCustomMessage("Veuillez renseigner le MAIL");
            ret = true;
        }
        else if(binder.numerocompteinput.getText().toString().isEmpty()){
            displayCustomMessage("Veuillez renseigner le NUMERO");
            ret = true;
        }
        //
        if(!ret){
            enregistrer();
        }
    }

    protected void displayCustomMessage(String message) {
        Snackbar snackbar= Snackbar.make (binder.constraintcompte,
                message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    // save :
    protected void enregistrer(){
        Client ct = new Client();
        ct.setIdcli(viewmodel.getCompte().isEmpty() ? 0 : ct.getIdcli());
        ct.setNom(binder.nomcompteinput.getText().toString());
        ct.setPrenom(binder.prenomcompteinput.getText().toString());
        ct.setEmail(binder.emailcompteinput.getText().toString());
        ct.setNumero(binder.numerocompteinput.getText().toString());
        ct.setCommune(viewmodel.getAllCommune().get(i_commune).getIdcom());
        ct.setAdresse(binder.adressecompteinput.getText().toString());
        ct.setGenre(binder.radiof.isChecked() ? 0 : 1);
        if(viewmodel.getCompte().isEmpty()) ct.setFcmtoken("");
        if(viewmodel.getCompte().isEmpty()) ct.setPwd("");

        // Now process :
        processDialog(ct);
    }


    //
    public void processDialog(Client ct){
        // We can launch the appropriate METHOD to send the DATA :
        LayoutInflater inflater = LayoutInflater.from(this);
        View vRapport = inflater.inflate(R.layout.layoutpatienterdialog, null);

        // Get OBects :
        TextView textpatienter = vRapport.findViewById(R.id.textpatienter);
        textpatienter.setText(viewmodel.getCompte().isEmpty() ? "Création du compte" : "Mis à jour du compte");
        ProgressBar progresspatienter = vRapport.findViewById(R.id.progresspatienter);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Synchronisation");

        builder.setIcon(R.mipmap.ic_launcher);
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

                    // MOVE ON :
                    finish();
                }
                else if(stopProcess){
                    // Display message :
                    Snackbar snackbar1 = Snackbar.make(binder.constraintcompte, "Erreur survenue !", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                    handlerAsynchLoad.removeCallbacks(this);
                }
                else {
                    handlerAsynchLoad.postDelayed(this, 1000);
                }
            }
        };
        // Call
        getFcmToken(ct);

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

    public void managecustomer(Client ct){
        if(apiProxy == null) initProxy();
        apiProxy.managecustomer(ct).enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                //Toast.makeText(getApplicationContext(),"response : "+String.valueOf(response.code()),Toast.LENGTH_SHORT).show();
                if (response.code() == 200) {
                    // Now save it :
                    viewmodel.insert(response.body());
                    flagCompte = true;
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                stopProcess = true;
            }
        });
    }

    public void getFcmToken(Client ct){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            stopProcess = true;
                            return;
                        }

                        Toast.makeText(getApplicationContext(),"FCM obtenu",Toast.LENGTH_SHORT).show();

                        // Get new FCM registration token
                        ct.setFcmtoken(task.getResult().toString());
                        flagFcmToken = true;

                        // Call subscription :
                        subscribeTo("ecommerce", ct);
                    }
                });
    }

    public void subscribeTo(String topic, Client ct){
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
                        Toast.makeText(getApplicationContext(), "subscribeTo", Toast.LENGTH_LONG).show();

                        if(msg.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Création l'utilisateur !", Toast.LENGTH_SHORT).show();
                            flagSouscription = true;
                            // Call
                            managecustomer(ct);
                        }
                    }
                });
    }
}