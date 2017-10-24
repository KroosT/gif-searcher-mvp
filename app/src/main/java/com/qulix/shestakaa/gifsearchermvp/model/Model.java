package com.qulix.shestakaa.gifsearchermvp.model;

import com.qulix.shestakaa.gifsearchermvp.model.API.Feed;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import retrofit2.Callback;

@ParametersAreNonnullByDefault
public interface Model {

    Cancelable getTrending(final Callback<Feed> callback);

    Cancelable getByRequest(final Callback<Feed> callback, final String req);

}
