package com.ankk.market.cinetpay;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.ankk.market.PanierActivity;
import com.cinetpay.androidsdk.CinetPayWebAppInterface;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MobileMoney extends CinetPayWebAppInterface {

    // Attributes


    public MobileMoney(Context context,
                       String api_key,
                       String site_id,
                       String transaction_id,
                       int amount,
                       String currency,
                       String description) {
        super(context, api_key, site_id, transaction_id, amount, currency, description);
    }

    @Override
    @JavascriptInterface
    public void onResponse(String data) {
        try {
            CinetPayResponse ce = new ObjectMapper().readValue(data, CinetPayResponse.class);
            if(ce == null) {
                Toast.makeText(getContext(), "Obet NULL ",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                PanierActivity.getInstance().setPaiementeffectue();
                Toast.makeText(getContext(), "Succes ",
                        Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception exc){
            Toast.makeText(getContext(), "Imposible de convertir : "+exc.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    @JavascriptInterface
    public void onError(String data) {
        //Log.d("MyCinetPayWebApp", data);
        Toast.makeText(getContext(), "Error : "+data,
                Toast.LENGTH_LONG).show();
    }


}
