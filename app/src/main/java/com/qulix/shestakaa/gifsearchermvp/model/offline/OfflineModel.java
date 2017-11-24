package com.qulix.shestakaa.gifsearchermvp.model.offline;

import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.ConnectivityObserver;
import com.qulix.shestakaa.gifsearchermvp.utils.Loadable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface OfflineModel {
    Cancelable loadAvailableGifs(final Loadable loadable);
}
