package com.qulix.shestakaa.gifsearchermvp.model.gifdetails;

import android.content.Context;

import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Downloadable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DetailsModelImpl implements DetailsModelInterface {

    @Override
    public Cancelable saveGifByUrl(final Context context,
                                   final String url,
                                   final Downloadable downloadable) {
        Validator.isArgNotNull(url, "url");

        final GifDownloader gifDownloader = new GifDownloader(context, downloadable);
        gifDownloader.execute(url);

        return new Cancelable() {
            @Override
            public void onCancel() {
                gifDownloader.cancel(true);
            }
        };
    }
}
