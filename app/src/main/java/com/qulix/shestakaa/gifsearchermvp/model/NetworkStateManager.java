package com.qulix.shestakaa.gifsearchermvp.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.qulix.shestakaa.gifsearchermvp.utils.ConnectivityObserver;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class NetworkStateManager extends BroadcastReceiver {

    private boolean mConnected = true;
    private final List<ConnectivityObserver> mObservers;

    public NetworkStateManager() {
        mObservers = new ArrayList<>();
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        final ConnectivityManager cm = (ConnectivityManager) context
                                            .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        mConnected = !(netInfo == null || !netInfo.isConnected());
        notifyObservers();
    }

    public void addObserver(final ConnectivityObserver observer) {
        Validator.isArgNotNull(observer, "observer");
        mObservers.add(observer);
    }

    public void removeObserver(final ConnectivityObserver observer) {
        Validator.isArgNotNull(observer, "observer");
        mObservers.remove(observer);
    }

    public boolean isConnected() {
        return mConnected;
    }

    private void notifyObservers() {
        for (final ConnectivityObserver observer : mObservers) {
            observer.update(this);
        }
    }

}
