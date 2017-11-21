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

    private final NetworkStateManager mNetworkManager;
    private final LoaderFactory mLoaderFactory;

    @Inject
    public OfflineModelImpl(final LoaderFactory loaderFactory,
                            final NetworkStateManager networkStateManager) {

        Validator.isArgNotNull(loaderFactory, "mLoaderFactory");
        Validator.isArgNotNull(networkStateManager, "networkStateManager");

        mLoaderFactory = loaderFactory;
        mNetworkManager = networkStateManager;
    }

    @Override
    public Cancelable loadAvailableGifs(final Loadable requestHandler) {
        Validator.isArgNotNull(requestHandler, "loadable");

        final GifLoader gifLoader = mLoaderFactory.buildGifLoader(requestHandler);
        gifLoader.execute();

        return new Cancelable() {
            @Override
            public void cancelRequest() {
                gifLoader.cancel(true);
            }
        };
    }

    @Override
    public void addConnectivityObserver(final ConnectivityObserver observer) {
        Validator.isArgNotNull(observer, "observer");
        mNetworkManager.addObserver(observer);
    }

    @Override
    public void removeConnectivityObserver(final ConnectivityObserver observer) {
        Validator.isArgNotNull(observer, "observer");
        mNetworkManager.removeObserver(observer);
    }


}
