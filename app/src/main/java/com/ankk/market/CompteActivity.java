package com.ankk.market;

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

import com.ankk.market.databinding.ActivityCompteBinding;
import com.ankk.market.models.Client;
import com.ankk.market.viewmodels.AccueilViewmodel;
import com.ankk.market.viewmodels.ClientViewmodel;
import com.ankk.market.viewmodels.VMFactory;
import com.google.android.material.snackbar.Snackbar;

import java.util.stream.Collectors;

public class CompteActivity extends AppCompatActivity {

    // A T T R I B U T E S :
    ActivityCompteBinding binder;
    ClientViewmodel viewmodel;
    String[] libCommunes;
    int i_commune = 0;
    AlertDialog alertDialogLoadPicture;


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


    }

    protected boolean checkFields(){
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
        return ret;
    }

    protected void displayCustomMessage(String message) {
        Snackbar snackbar= Snackbar.make (binder.constraintcompte,
                message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    // save :
    protected void enregistrer(){
        Client ct = new Client();
        ct.setNom(binder.nomcompteinput.getText().toString());
        ct.setPrenom(binder.prenomcompteinput.getText().toString());
        ct.setEmail(binder.emailcompteinput.getText().toString());
        ct.setNumero(binder.numerocompteinput.getText().toString());
        ct.setCommune(viewmodel.getAllCommune().get(i_commune).getIdcom());
        ct.setAdresse(binder.adressecompteinput.getText().toString());
        ct.setGenre(binder.radiof.isChecked() ? 0 : 1);
        if(viewmodel.getCompte().isEmpty()) ct.setFcmtoken("");

        // Now process :

    }


    //
    /*public void processDialog(){
        // We can launch the appropriate METHOD to send the DATA :
        LayoutInflater inflater = LayoutInflater.from(this);
        View vRapport = inflater.inflate(R.layout.layoutpatienterdialog, null);

        // Get OBects :
        TextView textpatienter = vRapport.findViewById(R.id.textpatienter);
        textpatienter.setText("Initialisation Système");
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
                if (getCommune) {
                    // Job done :
                    alertDialogLoadPicture.cancel();
                    handlerAsynchLoad.removeCallbacks(this);

                    // MOVE ON :
                    if(!data.isEmpty()){

                        // Hide Items :
                        //binder.butcompte.setVisibility(View.INVISIBLE);
                        //binder.textmerci.setVisibility(View.INVISIBLE);

                        // Open COMPTE :
                        Intent it = new Intent(getContext(), CompteActivity.class);
                        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(it);
                    }
                } else {
                    handlerAsynchLoad.postDelayed(this, 1000);
                }
            }
        };
        // Call
        getmobileAllCommunes();

        //
        handlerAsynchLoad.postDelayed(runAsynchLoad, 1000);
    }*/
}