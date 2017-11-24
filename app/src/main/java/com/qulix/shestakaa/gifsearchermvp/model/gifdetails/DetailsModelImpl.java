package com.qulix.shestakaa.gifsearchermvp.model.gifdetails;

import com.qulix.shestakaa.gifsearchermvp.model.LoaderFactory;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Downloadable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

@ParametersAreNonnullByDefault
public class DetailsModelImpl implements DetailsModel {

    private final LoaderFactory mLoaderFactory;

    @Inject
    public DetailsModelImpl(final LoaderFactory loaderFactory) {
        Validator.isArgNotNull(loaderFactory, "loaderFactory");

        mLoaderFactory = loaderFactory;
    }

    @Override
    public Cancelable saveGifByUrl(final String url,
                                   final Downloadable requestHandler) {
        Validator.isArgNotNull(url, "url");
        Validator.isArgNotNull(requestHandler, "downloadable");

        final GifDownloader gifDownloader = mLoaderFactory.buildGifDownloader(requestHandler);
        gifDownloader.execute(url);

        return () -> gifDownloader.cancel(true);
    }
}
