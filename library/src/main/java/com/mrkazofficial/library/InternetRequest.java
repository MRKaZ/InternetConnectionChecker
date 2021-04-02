package com.mrkazofficial.library;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;


/**
 * @author MRKaZ
 * @since 11:26 PM, 3/23/2021, 2021
 */

class InternetRequest extends BroadcastReceiver {

    private Context mContext;
    private String ConnectionType = null;

    private boolean isConnected;

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            // connected to the internet
            if (isConnected) {
                if (mNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    ConnectionType = "TYPE_WIFI";
                    sendConnectionUpdate(true);
                } else if (mNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // connected to mobile data
                    ConnectionType = "TYPE_MOBILE";
                    sendConnectionUpdate(true);
                }
            }
        } else {
            // not connected to the internet
            isConnected = true;
            sendConnectionUpdate(false);
        }
    }

    public void sendConnectionUpdate(boolean Connection) {
        Intent intent = new Intent(InternetChecker.CONNECTION_UPDATE);
        intent.putExtra("connectionStatus", Connection);
        intent.putExtra("connectionType", ConnectionType);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

}
