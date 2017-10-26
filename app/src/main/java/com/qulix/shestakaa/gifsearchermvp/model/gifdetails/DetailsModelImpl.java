package com.qulix.shestakaa.gifsearchermvp.model.gifdetails;

import android.content.Context;

import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Downloadable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DetailsModelImpl implements DetailsModel {

    private final Context mContext;

    public DetailsModelImpl(final Context context) {
        Validator.isArgNotNull(context, "context");
        mContext = context;
    }

    @Override
    public Cancelable saveGifByUrl(final String url,
                                   final Downloadable requestController) {
        Validator.isArgNotNull(url, "url");
        Validator.isArgNotNull(requestController, "downloadable");

        final GifDownloader gifDownloader = new GifDownloader(mContext, requestController);
        gifDownloader.execute(url);

        return new Cancelable() {
            @Override
            public void onCancelRequest() {
                gifDownloader.cancel(true);
            }
        };
    }
}
