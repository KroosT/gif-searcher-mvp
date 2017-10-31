package com.qulix.shestakaa.gifsearchermvp.model.gifdetails;

import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Downloadable;

import java.util.Observer;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface DetailsModel {

    Cancelable saveGifByUrl(final String url, final Downloadable downloadable);
    void setObserver(final Observer observer);
    void removeObserver(final Observer observer);

}
