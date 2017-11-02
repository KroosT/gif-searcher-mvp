package com.qulix.shestakaa.gifsearchermvp;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.qulix.shestakaa.gifsearchermvp.model.NetworkStateManager;


public class GSApplication extends Application {

    private static GSApplication sApplication = null;
    private final NetworkStateManager mManager = new NetworkStateManager();

    @Override
    public void onCreate() {
        super.onCreate();
        registerNetworkReceiver();
        sApplication = this;
    }

    private void registerNetworkReceiver() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mManager, intentFilter);
    }

    public NetworkStateManager getNetworkStateManager() {
        return mManager;
    }

    public static GSApplication getInstance() {
        return sApplication;
    }
}