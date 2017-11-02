package com.qulix.shestakaa.gifsearchermvp;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.qulix.shestakaa.gifsearchermvp.model.NetworkStateReceiver;


public class GSApplication extends Application {

    private NetworkStateReceiver mReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        registerNetworkReceiver();
    }

    private void registerNetworkReceiver() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mReceiver = new NetworkStateReceiver();
        registerReceiver(mReceiver, intentFilter);
    }
}