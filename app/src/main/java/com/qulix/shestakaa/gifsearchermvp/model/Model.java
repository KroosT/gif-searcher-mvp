package com.qulix.shestakaa.gifsearchermvp.model;

import android.content.Context;

import com.qulix.shestakaa.gifsearchermvp.model.API.Feed;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.ConnectivityObserver;

import javax.annotation.ParametersAreNonnullByDefault;

import retrofit2.Callback;

@ParametersAreNonnullByDefault
public interface Model {

    Cancelable getTrending(final Callback<Feed> callback);
    Cancelable getByRequest(final Callback<Feed> callback, final String req);
    Cancelable loadMoreTrending(final Callback<Feed> callback, final int offset);
    Cancelable loadMoreSearch(final Callback<Feed> callback, final String req, final int offset);
    void addConnectivityObserver(final ConnectivityObserver observer);
    void removeConnectivityObserver(final ConnectivityObserver observer);
    LoadMoreType getLoadMoreType(final Context context);

}
