package com.qulix.shestakaa.gifsearchermvp.model.offline;

import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Loadable;

public interface OfflineModelInterface {
    Cancelable loadAvailableGifs(final Loadable loadable);
}
