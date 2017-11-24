package com.qulix.shestakaa.gifsearchermvp.model.offline;

import com.qulix.shestakaa.gifsearchermvp.model.LoaderFactory;
import com.qulix.shestakaa.gifsearchermvp.model.NetworkStateManager;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.ConnectivityObserver;
import com.qulix.shestakaa.gifsearchermvp.utils.Loadable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

@ParametersAreNonnullByDefault
public class OfflineModelImpl implements OfflineModel {

    private final LoaderFactory mLoaderFactory;

    @Inject
    public OfflineModelImpl(final LoaderFactory loaderFactory) {

        Validator.isArgNotNull(loaderFactory, "mLoaderFactory");

        mLoaderFactory = loaderFactory;
    }

    @Override
    public Cancelable loadAvailableGifs(final Loadable requestHandler) {
        Validator.isArgNotNull(requestHandler, "loadable");

        final GifLoader gifLoader = mLoaderFactory.buildGifLoader(requestHandler);
        gifLoader.execute();

        return () -> gifLoader.cancel(true);
    }

}
