package com.qulix.shestakaa.gifsearchermvp.model.gifdetails;

import android.content.Context;

import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Downloadable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DetailsModelImpl implements DetailsModelInterface {

    private final Context mContext;

    public DetailsModelImpl(final Context context) {
        mContext = context;
    }

    @Override
    public Cancelable saveGifByUrl(final String url,
                                   final Downloadable downloadable) {
        Validator.isArgNotNull(url, "url");

        final GifDownloader gifDownloader = new GifDownloader(mContext, downloadable);
        gifDownloader.execute(url);

        return new Cancelable() {
            @Override
            public void onCancel() {
                gifDownloader.cancel(true);
            }
        };
    }
}
