package com.qulix.shestakaa.gifsearchermvp.utils;

import com.qulix.shestakaa.gifsearchermvp.model.NetworkStateManager;

import javax.annotation.Nonnull;

public interface ConnectivityObserver {
    void update(@Nonnull final NetworkStateManager manager);
}
