package com.trust.id.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.orhanobut.hawk.Hawk;
import com.trust.id.Oauth2Helper;
import com.trust.id.R;
import com.trust.id.network.RestClient;
import com.trust.id.ui.home.HomeActivity;

import java.util.Map;

import retrofit2.Call;

public class IdMessagginService extends FirebaseMessagingService {
    private static final String TAG = IdMessagginService.class.getSimpleName();
    private LocalBroadcastManager mBM;
    private NotificationManager mManager;
    private Oauth2Helper mHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Messaging Service Created");
        mBM = LocalBroadcastManager.getInstance(this);
        mHelper = Oauth2Helper.getInstance().with();
    }

    @Override
    public void onNewToken(final String token) {
        super.onNewToken(token);
        Log.d(TAG, "New Token");
        //TODO: Update firebase token
        mHelper.requestFreshToken(new Oauth2Helper.AuthListener.StateListener() {
            @Override
            public void onSuccess(String accessToken, String idToken) {
             /*   Call<Void> updateToken = RestClient.get().notification("Bearer " + accessToken, new FirebaseBody(token, ""));
                updateToken.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Hawk.put("FIREBASE", true);
                        } else {
                            Hawk.delete("FIREBASE");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Hawk.delete("FIREBASE");
                    }
                });*/

            }

            @Override
            public void onError(String error) {
                Hawk.delete("FIREBASE");
                Log.e(TAG, "Firebase token cannot be updated");
            }
        });
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e(TAG, "New message");
        showNotification(remoteMessage);
    }

    private void showNotification(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        Map<String, String> data = remoteMessage.getData();
        String authStrategy = data.get("auth_strategy");
        String strategy = (authStrategy.equals("fingerprint")) ? "Huella" : (authStrategy.equals("face")) ? "Face ID" : (authStrategy.equals("password")) ? "Contraseña" : "UNKNOWN";
        String contextTitle = "Se ha realizado una transacción " + ((data.get("result").equals("success")) ? "exitosa" : "fallida");
        String contextText = "Validación mediante " + strategy + " en " + data.get("institution");

        String mName = "my_package_channel";
        String mId = "Autentia"; // The user-visible name of the channel.
        String mDescription = "Nueva transacción"; // The user-visible description of the channel.

        Intent mIntent;
        PendingIntent mPending;
        NotificationCompat.Builder builder;

        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        String id = data.get("id");
        if (Hawk.contains("NOTIFICATION_TEST") && id.equals("1")) {
            id = Hawk.get("NOTIFICATION_TEST");
        }
        mIntent = new Intent(this, HomeActivity.class);
        mIntent.putExtra("id", id);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mPending = PendingIntent.getActivity(this, 0, mIntent,
                PendingIntent.FLAG_ONE_SHOT);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = mManager.getNotificationChannel(mId);
            if (mChannel == null) {
                mChannel = new NotificationChannel(mId, mName, importance);
                mChannel.setDescription(mDescription);
                mChannel.enableVibration(true);
                mChannel.setShowBadge(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                mManager.createNotificationChannel(mChannel);
            }
        }

        builder = new NotificationCompat.Builder(this, mId);

        builder.setContentTitle(contextTitle)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_person))
                .setSmallIcon(R.drawable.ic_lock)
                .setContentText(contextText)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(mPending)
                .setTicker(contextTitle)
                .setVibrate(new long[]{300, 500, 300, 500});


        mManager.notify(0, builder.build());


        Intent message = new Intent("NewMessage");
        message.putExtra("id", id);
        //mBM.sendBroadcast(message);

    }
}
