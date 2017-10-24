package com.qulix.shestakaa.gifsearchermvp.model.offline;

import android.content.Context;

import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Loadable;

public class OfflineModelImpl implements OfflineModelInterface {

    @Override
    public Cancelable loadAvailableGifs(final Context context, final Loadable loadable) {
        final GifLoader gifLoader = new GifLoader(context, loadable);
        gifLoader.execute();

        return new Cancelable() {
            @Override
            public void onCancel() {
                gifLoader.cancel(true);
            }
        };
    }


}
