package com.ankk.market;

import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ankk.market.cinetpay.MobileMoney;
import com.ankk.market.models.Achat;
import com.ankk.market.models.Client;
import com.ankk.market.viewmodels.CinetpayViewmodel;
import com.ankk.market.viewmodels.PanierViewmodel;
import com.ankk.market.viewmodels.VMFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CinetPay extends AppCompatActivity {

    // Attributes :
    protected WebView mWebView;
    OpenApplication app;
    String nom, prenom, email, contact;
    int montant = 0;
    CinetpayViewmodel viewmodel;



    // Methods :
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinet_pay);

        // Hook :
        mWebView = findViewById(R.id.webviewcinetpay);

        //
        app = (OpenApplication)getApplication();

        // Set the VIEWMODEL :
        viewmodel = new ViewModelProvider(this,
                new VMFactory(getApplication()))
                .get(CinetpayViewmodel.class);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        CookieManager cookieManager = CookieManager.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(mWebView,true);
        } else {
            cookieManager.setAcceptCookie(true);
        }

        // Get "EXTRAS" :
        /*Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Get EveryTHING :
            montant = Integer.valueOf(extras.getString("montant"));
            idmag = extras.getInt("idmag",0);
        }*/

        // Transaction Id :
        Client ct = viewmodel.getClientRepository().getAll().get(0);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String transaction_ID = timeStamp + "_" + String.valueOf(ct.getIdcli());

        // Get All ARTICLE MONEY :
        List<Achat> listeAchat = viewmodel.getAchatRepository().getAllByActif(1);

        //
        //         13013879545bdc3a5579f458.42836232    448173
        MobileMoney mm = new MobileMoney(getApplicationContext(),
                "13013879545bdc3a5579f458.42836232",
                "448173", transaction_ID, montant,
                "XOF", "Paiement de la commande");

        // Get DATA :
        String nom = "***", prenom = "***",email="***",numero="***";
        nom = ct.getNom();
        prenom = ct.getPrenom();
        email = ct.getEmail();
        numero = ct.getNumero();

        //
        mWebView.addJavascriptInterface(mm
                        .setCustomerName(nom)
                        .setCustomerSurname(prenom)
                        .setCustomerEmail(email)
                        .setCustomerPhoneNumber(numero)
                ,
                "Android");

        mWebView.loadUrl("file:///android_asset/cinetpay.html");
    }

}