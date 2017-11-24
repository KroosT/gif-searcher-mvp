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

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

@ParametersAreNonnullByDefault
@Singleton
public class NetworkStateManager extends BroadcastReceiver {

    private boolean mConnected = true;
    private final PublishSubject<Boolean> mConnectedw = PublishSubject.create();

    @Inject
    public NetworkStateManager() {

    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        final ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = cm != null ? cm.getActiveNetworkInfo() : null;

        mConnected = !(netInfo == null || !netInfo.isConnected());
        mConnectedw.onNext(!(netInfo == null || !netInfo.isConnected()));
    }

    public PublishSubject<Boolean> getObservable() {
        return mConnectedw;
    }

    public boolean isConnected() {
        return mConnected;
    }

}
