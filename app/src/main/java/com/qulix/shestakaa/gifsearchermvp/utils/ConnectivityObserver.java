package com.qulix.shestakaa.gifsearchermvp.utils;

import com.qulix.shestakaa.gifsearchermvp.model.NetworkStateManager;

public interface ConnectivityObserver {
    void update(final NetworkStateManager manager);
}
