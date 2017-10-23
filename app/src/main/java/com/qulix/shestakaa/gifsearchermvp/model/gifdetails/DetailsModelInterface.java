package com.qulix.shestakaa.gifsearchermvp.model.gifdetails;

import android.content.Context;

import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Downloadable;

import javax.annotation.Nonnull;

public interface DetailsModelInterface {

    Cancelable saveGifByUrl(@Nonnull final Context context,
                            @Nonnull final String url,
                            @Nonnull final Downloadable downloadable);

}
