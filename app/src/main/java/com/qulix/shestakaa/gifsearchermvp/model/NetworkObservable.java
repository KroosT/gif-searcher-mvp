package com.qulix.shestakaa.gifsearchermvp.model;

import java.util.Observable;

public class NetworkObservable extends Observable {

    private static NetworkObservable instance = null;
    private boolean mConnected = true;

    private NetworkObservable() {

    }

    public void connectionDown() {
        mConnected = false;
        setChanged();
        notifyObservers();
    }

    public void connectionUp() {
        mConnected = true;
        setChanged();
        notifyObservers();
    }

    public static NetworkObservable getInstance() {
        if(instance == null){
            instance = new NetworkObservable();
        }
        return instance;
    }

    public boolean isConnected() {
        return mConnected;
    }
}
