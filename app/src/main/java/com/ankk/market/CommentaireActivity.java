package com.ankk.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.ankk.market.beans.BeanCommentRequest;
import com.ankk.market.beans.RequeteHistoCommande;
import com.ankk.market.databinding.ActivityCommentaireBinding;
import com.ankk.market.databinding.ActivityHistoriqueCommandeBinding;
import com.ankk.market.viewmodels.CommentaireViewmodel;
import com.ankk.market.viewmodels.HistoriqueViewmodel;
import com.ankk.market.viewmodels.VMFactory;

public class CommentaireActivity extends AppCompatActivity {


    // A t t r i b u t e s  :
    ActivityCommentaireBinding binder;
    CommentaireViewmodel viewmodel;



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
                BeanCommentRequest bt = new BeanCommentRequest();
                bt.setIdart(viewmodel.getIdart());
                bt.setCommentaire(binder.commentedit.getText().toString());
                bt.setIdcli(viewmodel.getClientRepository().getAll().get(0).getIdcli());
                bt.setNote(viewmodel.getNote());
                // Process :

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
                break;

            case 2:
                binder.cmtunnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                binder.cmtdeuxnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                break;

            case 3:
                binder.cmtunnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                binder.cmtdeuxnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                binder.cmttroixnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                break;

            case 4:
                binder.cmtunnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                binder.cmtdeuxnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                binder.cmttroixnote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
                binder.cmtquatrenote.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_full_gray, null));
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
}