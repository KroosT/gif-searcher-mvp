package com.qulix.shestakaa.gifsearchermvp.model.main;

import com.qulix.shestakaa.gifsearchermvp.model.API.Feed;
import com.qulix.shestakaa.gifsearchermvp.model.LoadMoreType;
import com.qulix.shestakaa.gifsearchermvp.utils.ConnectivityObserver;

import javax.annotation.ParametersAreNonnullByDefault;

import io.reactivex.Observable;

@ParametersAreNonnullByDefault
public interface MainModel {

    Observable<Feed> getTrending();
    Observable<Feed> getByRequest(final String req);
    Observable<Feed> loadMoreTrending(final int offset);
    Observable<Feed> loadMoreSearch(final String req, final int offset);
    void addConnectivityObserver(final ConnectivityObserver observer);
    void removeConnectivityObserver(final ConnectivityObserver observer);
    LoadMoreType getLoadMoreType();

}
