package com.qulix.shestakaa.gifsearchermvp.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.subjects.PublishSubject;

@ParametersAreNonnullByDefault
@Singleton
public class NetworkStateManager extends BroadcastReceiver {

    private final PublishSubject<Boolean> mConnected = PublishSubject.create();

    @Inject
    public NetworkStateManager() {

    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        final ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = cm != null ? cm.getActiveNetworkInfo() : null;

        mConnected.onNext(!(netInfo == null || !netInfo.isConnected()));
    }

    public PublishSubject<Boolean> getObservable() {
        return mConnected;
    }

}
