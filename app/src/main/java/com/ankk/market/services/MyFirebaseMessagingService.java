package com.ankk.market.services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.ankk.market.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {



    // M e t h o d s :
    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        // Process :
        handleMessage(message);
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }


    //
    private void handleMessage(RemoteMessage remoteMessage){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

                if(remoteMessage.getData().size() > 0){
                    Map<String, String> donnee = remoteMessage.getData();

                    //
                    String dte = "";
                    String heu = "";
                    String resume ="";
                    String contenu ="";

                    // based on the objet, process :
                    switch (Integer.parseInt(donnee.get("objet"))){
                        case 1:
                            // COMMANDE en cours de traiement : Integer.parseInt(donnee.get("activite"))
                            dte = donnee.get("dates");
                            heu = donnee.get("heure");
                            resume = "Validation de votre commande";
                            contenu = "Votre commande du "+dte+" ("+heu+") a été validée. \n " +
                                    "Elle vous sera sous peu expédiée !";
                            displayNotification("Notification Commande",
                                    resume, contenu, Integer.parseInt(heu.replaceAll(":", "")));
                            break;


                        case 2:
                            // COMMANDE en cours de LIVRAISON :
                            dte = donnee.get("dates");
                            heu = donnee.get("heure");
                            resume = "Livraison de votre commande encours";
                            contenu = "Votre commande du "+dte+" ("+heu+") est en cours de livraison. \n " +
                                    "Vous la recevrez dans peu de temps !";
                            displayNotification("Notification Commande",
                                    resume, contenu, Integer.parseInt(heu.replaceAll(":", "")));
                            break;

                        case 3:
                            // COMMANDE LIVREE :
                            dte = donnee.get("dates");
                            heu = donnee.get("heure");
                            resume = "Commande livrée";
                            contenu = "Votre commande du "+dte+" ("+heu+") vous a été livrée. \n " +
                                    "Nous vous remercions pour votre confiance !";
                            displayNotification("Notification Commande",
                                    resume, contenu, Integer.parseInt(heu.replaceAll(":", "")));
                            break;

                        case 4:
                            // Commercant :
                            break;

                        case 5:
                            // Louer :
                            break;

                        case 6:
                            // Positiongps :
                            break;
                    }
                }
            }
        });
    }


    // Display NOTIFICATION :
    private void displayNotification(String titre, String resume, String contenu, int idNotif){
        //
        NotificationManager notificationManager = (NotificationManager)
                getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel("NOTIF",
                    "Information", NotificationManager.IMPORTANCE_MAX);
            // Configure the notification channel.
            notificationChannel.setDescription("Statut de la commande");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "NOTIF")
                .setSmallIcon(R.mipmap.ic_launcher_cust)
                .setContentTitle(titre)
                .setContentText(resume)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(contenu))
                .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS|Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //
        notificationManager.notify(idNotif, builder.build());
    }

}
