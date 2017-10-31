package com.qulix.shestakaa.gifsearchermvp.model.offline;

import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Loadable;

import java.util.Observer;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface OfflineModel {
    Cancelable loadAvailableGifs(final Loadable loadable);
    void setObserver(final Observer observer);
    void removeObserver(final Observer observer);
}
