package com.qulix.shestakaa.gifsearchermvp.utils;

import java.util.Observable;

public class NetworkObservable extends Observable {

    private static NetworkObservable instance = null;

    private NetworkObservable() {

    }

    public void connectionDown() {
        setChanged();
        notifyObservers();
    }

    public static NetworkObservable getInstance() {
        if(instance == null){
            instance = new NetworkObservable();
        }
        return instance;
    }
}
