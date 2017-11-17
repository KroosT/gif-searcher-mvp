package com.qulix.shestakaa.gifsearchermvp;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.qulix.shestakaa.gifsearchermvp.model.NetworkStateManager;
import com.squareup.leakcanary.LeakCanary;


public class GSApplication extends Application {

    private static GSApplication sApplication = null;
    private final NetworkStateManager mManager = new NetworkStateManager();

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
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