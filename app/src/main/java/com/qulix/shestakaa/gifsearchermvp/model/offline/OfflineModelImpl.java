package com.qulix.shestakaa.gifsearchermvp.model.offline;

import android.content.Context;

import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Loadable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class OfflineModelImpl implements OfflineModel {

    private final Context mContext;

    public OfflineModelImpl(final Context context) {
        Validator.isArgNotNull(context, "context");
        mContext = context;
    }

    @Override
    public Cancelable loadAvailableGifs(final Loadable loadable) {
        Validator.isArgNotNull(loadable, "loadable");

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
