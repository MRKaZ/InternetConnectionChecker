package com.mrkazofficial.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * @author MRKaZ
 * @since 10:13 PM, 4/2/2021, 2021
 */

public class InternetChecker {

    private static final BroadcastReceiver mNetworkBroadcastReceiver = new InternetRequest();
    public static final String CONNECTION_UPDATE = "CONNECTION_UPDATE";
    private static InternetChecker.InternetListener mInternetListener;

    public InternetChecker() {
    }

    /**
     * Method to make initiate the InternetChecker
     *
     * @param Context The context
     */
    public static void initialize(Context Context) {
        Context.registerReceiver(mNetworkBroadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    /**
     * Method to unregister unRegisterConnectivity response
     *
     * @param Context The context
     */
    public static void unRegisterConnectivity(Context Context) {
        try {
            Context.unregisterReceiver(mNetworkBroadcastReceiver);
        } catch (IllegalArgumentException e) {
            Log.e("InternetChecker", "unRegister: Error " + e.getMessage());
        }
    }

    /**
     * Method to register registerConnectivity
     *
     * @param Context The context
     */
    public static void registerConnectivity(Context Context) {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Context);
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(CONNECTION_UPDATE);
        localBroadcastManager.registerReceiver(mBroadcastReceiver, iFilter);
    }

    /**
     * Method to Initiate the BroadCastReceiver listening to connection
     *
     * @param BroadcastReceiver      The BroadcastReceiver
     */
    private static final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(CONNECTION_UPDATE)) {
                boolean getStatus = intent.getBooleanExtra("connectionStatus", false);
                if (getStatus) {
                    // If Connection available
                    // Getting type of the Connection
                    String connectionType = intent.getStringExtra("connectionType");
                    // Using if - else for the switch connection type
                    if (connectionType.endsWith("TYPE_WIFI")) {
                        addResponse("TYPE_WIFI", true);
                    } else if (connectionType.endsWith("TYPE_MOBILE")) {
                        addResponse("TYPE_MOBILE", true);
                    }
                } else {
                    // If Don't have a connection
                    addResponse("TYPE_NULL", false);
                }
            }
        }
    };

    /**
     * Method to getResponse from the connectivity manager
     *
     * @param internetListener The InternetListener
     */
    public void getResponse(InternetChecker.InternetListener internetListener) {
        mInternetListener = internetListener;
    }


    public interface InternetListener {
        void onResponse(String connectionType, boolean isConnection);
    }

    /**
     * Method to Add response to the listener
     *
     * @param mResponse   The Adding response to the listener
     * @param mConnection The Set connection tru / false to listener
     */
    private static void addResponse(String mResponse, boolean mConnection) {
        mInternetListener.onResponse((String) mResponse, (Boolean) mConnection);
    }
}
