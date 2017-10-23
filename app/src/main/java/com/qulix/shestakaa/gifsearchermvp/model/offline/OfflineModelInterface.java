package com.qulix.shestakaa.gifsearchermvp.model.offline;

import android.content.Context;

import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Loadable;

public interface OfflineModelInterface {
    Cancelable loadAvailableGifs(final Context context, final Loadable loadable);
}
