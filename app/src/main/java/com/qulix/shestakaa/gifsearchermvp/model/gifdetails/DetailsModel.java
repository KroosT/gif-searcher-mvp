package com.qulix.shestakaa.gifsearchermvp.model.gifdetails;

import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Downloadable;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface DetailsModel {

    Cancelable saveGifByUrl(final String url, final Downloadable downloadable);

}
