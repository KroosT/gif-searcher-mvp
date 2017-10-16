package com.qulix.shestakaa.gifsearchermvp.model;

import com.qulix.shestakaa.gifsearchermvp.model.API.Feed;

import javax.annotation.Nonnull;

import retrofit2.Callback;

public interface ModelInterface {

    void getTrending(@Nonnull final Callback<Feed> callback);

    void getByRequest(@Nonnull final Callback<Feed> callback, final String req);

}
