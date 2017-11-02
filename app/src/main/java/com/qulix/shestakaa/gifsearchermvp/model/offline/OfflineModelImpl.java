package com.qulix.shestakaa.gifsearchermvp.model.offline;

import android.content.Context;

import com.qulix.shestakaa.gifsearchermvp.model.NetworkStateReceiver;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Loadable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import java.util.Observer;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class OfflineModelImpl implements OfflineModel {

    private final Context mContext;

    public OfflineModelImpl(final Context context) {
        Validator.isArgNotNull(context, "context");
        mContext = context;
    }

    @Override
    public Cancelable loadAvailableGifs(final Loadable requestController) {
        Validator.isArgNotNull(requestController, "loadable");

        final GifLoader gifLoader = new GifLoader(mContext, requestController);
        gifLoader.execute();

        return new Cancelable() {
            @Override
            public void cancelRequest() {
                gifLoader.cancel(true);
            }
        };
    }

    @Override
    public void setObserver(final Observer observer) {
        Validator.isArgNotNull(observer, "observer");
        NetworkStateReceiver.getObservable().addObserver(observer);
    }

    @Override
    public void removeObserver(final Observer observer) {
        Validator.isArgNotNull(observer, "observer");
        NetworkStateReceiver.getObservable().deleteObserver(observer);
    }


}
