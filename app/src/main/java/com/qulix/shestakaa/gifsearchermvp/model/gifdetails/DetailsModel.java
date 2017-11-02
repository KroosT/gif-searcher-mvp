package com.qulix.shestakaa.gifsearchermvp.model.gifdetails;

import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.ConnectivityObserver;
import com.qulix.shestakaa.gifsearchermvp.utils.Downloadable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface DetailsModel {

    Cancelable saveGifByUrl(final String url, final Downloadable downloadable);
    void addConnectivityObserver(final ConnectivityObserver observer);
    void removeConnectivityObserver(final ConnectivityObserver observer);

}
