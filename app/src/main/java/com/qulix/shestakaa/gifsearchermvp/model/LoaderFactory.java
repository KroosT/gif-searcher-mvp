package com.qulix.shestakaa.gifsearchermvp.model;

import android.content.Context;

import com.qulix.shestakaa.gifsearchermvp.model.gifdetails.GifDownloader;
import com.qulix.shestakaa.gifsearchermvp.model.offline.GifLoader;
import com.qulix.shestakaa.gifsearchermvp.utils.Downloadable;
import com.qulix.shestakaa.gifsearchermvp.utils.Loadable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class LoaderFactory {

    private final Context mContext;

    public LoaderFactory(final Context context) {
        Validator.isArgNotNull(context, "context");

        mContext = context;
    }

    public GifDownloader buildGifDownloader(final Downloadable downloadable) {
        Validator.isArgNotNull(downloadable, "downloadable");

        return new GifDownloader(mContext, downloadable);
    }

    public GifLoader buildGifLoader(final Loadable loadable) {
        Validator.isArgNotNull(loadable, "loadable");
        return new GifLoader(mContext, loadable);
    }

}
