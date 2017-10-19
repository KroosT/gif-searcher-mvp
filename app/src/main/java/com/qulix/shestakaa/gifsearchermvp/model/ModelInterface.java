package com.qulix.shestakaa.gifsearchermvp.model;

import com.qulix.shestakaa.gifsearchermvp.model.API.Feed;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;

import javax.annotation.Nonnull;

import retrofit2.Callback;

public interface ModelInterface {

    Cancelable getTrending(@Nonnull final Callback<Feed> callback);

    Cancelable getByRequest(@Nonnull final Callback<Feed> callback,
                            final String req);

}
