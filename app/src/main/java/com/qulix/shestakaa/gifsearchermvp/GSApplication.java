package com.qulix.shestakaa.gifsearchermvp;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.qulix.shestakaa.gifsearchermvp.model.NetworkStateManager;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;


public class GSApplication extends Application {

    private static AppComponent sComponent;
    @Inject
    NetworkStateManager mManager;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        initAppComponent();
        sComponent.inject(this);
        registerNetworkReceiver();
    }

    private void registerNetworkReceiver() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mManager, intentFilter);
    }

    public static AppComponent getComponent() {
        return sComponent;
    }

    private void initAppComponent() {
        sComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }
}