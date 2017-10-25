package com.qulix.shestakaa.gifsearchermvp.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        final ConnectivityManager cm = (ConnectivityManager) context
                                            .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        if (netInfo == null || !netInfo.isConnected()) {
            getObservable().connectionDown();
        } else {
            getObservable().connectionUp();
        }
    }

    public static NetworkObservable getObservable() {
        return NetworkObservable.getInstance();
    }

}
