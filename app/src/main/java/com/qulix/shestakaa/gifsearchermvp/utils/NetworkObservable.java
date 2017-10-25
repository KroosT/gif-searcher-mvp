package com.qulix.shestakaa.gifsearchermvp.utils;

import java.util.Observable;

import javax.annotation.Nonnull;

public class NetworkObservable extends Observable {

    private static NetworkObservable instance = null;
    private ConnectionStatus mConnectionStatus = ConnectionStatus.CONNECTED;

    private NetworkObservable() {

    }

    public void connectionDown() {
        mConnectionStatus = ConnectionStatus.NO_CONNECTION;
        setChanged();
        notifyObservers();
    }

    public void connectionUp() {
        mConnectionStatus = ConnectionStatus.CONNECTED;
        setChanged();
        notifyObservers();
    }

    public static NetworkObservable getInstance() {
        if(instance == null){
            instance = new NetworkObservable();
        }
        return instance;
    }

    @Nonnull
    public ConnectionStatus getConnectionStatus() {
        return mConnectionStatus;
    }
}
