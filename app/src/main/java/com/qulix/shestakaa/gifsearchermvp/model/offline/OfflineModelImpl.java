package com.qulix.shestakaa.gifsearchermvp.model.offline;

import android.content.Context;

import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Loadable;

public class OfflineModelImpl implements OfflineModelInterface {


    private final Context mContext;

    public OfflineModelImpl(final Context context) {
        mContext = context;
    }

    @Override
    public Cancelable loadAvailableGifs(final Loadable loadable) {
        final GifLoader gifLoader = new GifLoader(mContext, loadable);
        gifLoader.execute();

        return new Cancelable() {
            @Override
            public void onCancel() {
                gifLoader.cancel(true);
            }
        };
    }


}
