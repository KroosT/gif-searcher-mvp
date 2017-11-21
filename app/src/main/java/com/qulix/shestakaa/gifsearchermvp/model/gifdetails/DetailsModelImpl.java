package com.qulix.shestakaa.gifsearchermvp.model.gifdetails;

import com.qulix.shestakaa.gifsearchermvp.model.LoaderFactory;
import com.qulix.shestakaa.gifsearchermvp.model.NetworkStateManager;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.ConnectivityObserver;
import com.qulix.shestakaa.gifsearchermvp.utils.Downloadable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

@ParametersAreNonnullByDefault
public class DetailsModelImpl implements DetailsModel {

    private final NetworkStateManager mNetworkManager;
    private final LoaderFactory mLoaderFactory;

    @Inject
    public DetailsModelImpl(final LoaderFactory loaderFactory,
                            final NetworkStateManager networkStateManager) {
        Validator.isArgNotNull(loaderFactory, "loaderFactory");
        Validator.isArgNotNull(networkStateManager, "networkStateManager");

        mLoaderFactory = loaderFactory;
        mNetworkManager = networkStateManager;
    }

    @Override
    public Cancelable saveGifByUrl(final String url,
                                   final Downloadable requestHandler) {
        Validator.isArgNotNull(url, "url");
        Validator.isArgNotNull(requestHandler, "downloadable");

        final GifDownloader gifDownloader = mLoaderFactory.buildGifDownloader(requestHandler);
        gifDownloader.execute(url);

        return new Cancelable() {
            @Override
            public void cancelRequest() {
                gifDownloader.cancel(true);
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
