package com.qulix.shestakaa.gifsearchermvp.model;

import android.app.Application;

import com.qulix.shestakaa.gifsearchermvp.model.gifdetails.GifDownloader;
import com.qulix.shestakaa.gifsearchermvp.model.offline.GifLoader;
import com.qulix.shestakaa.gifsearchermvp.utils.Downloadable;
import com.qulix.shestakaa.gifsearchermvp.utils.Loadable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;
import javax.inject.Singleton;

@ParametersAreNonnullByDefault
@Singleton
public class LoaderFactory {

    private final Application mApplication;

    @Inject
    public LoaderFactory(final Application application) {
        Validator.isArgNotNull(application, "application");

        mApplication = application;
    }

    public GifDownloader buildGifDownloader(final Downloadable downloadable) {
        Validator.isArgNotNull(downloadable, "downloadable");

        return new GifDownloader(mApplication, downloadable);
    }

    public GifLoader buildGifLoader(final Loadable loadable) {
        Validator.isArgNotNull(loadable, "loadable");
        return new GifLoader(mApplication, loadable);
    }

}
