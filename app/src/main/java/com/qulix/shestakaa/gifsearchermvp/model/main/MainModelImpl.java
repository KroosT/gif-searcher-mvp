package com.qulix.shestakaa.gifsearchermvp.model.main;

import com.qulix.shestakaa.gifsearchermvp.model.API.ApiInterface;
import com.qulix.shestakaa.gifsearchermvp.model.API.ApiService;
import com.qulix.shestakaa.gifsearchermvp.model.API.Feed;
import com.qulix.shestakaa.gifsearchermvp.model.ApplicationSettings;
import com.qulix.shestakaa.gifsearchermvp.model.LoadMoreType;
import com.qulix.shestakaa.gifsearchermvp.model.NetworkStateManager;
import com.qulix.shestakaa.gifsearchermvp.utils.ConnectivityObserver;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@ParametersAreNonnullByDefault
public class MainModelImpl implements MainModel {

    private final ApiInterface mApiInterface;
    private static final String API_KEY = "fWieUtS84ZkjIWupFAQvqpUapoYj1k29";
    private static final int DEFAULT_LIMIT = 25;
    private final NetworkStateManager mNetworkManager;
    private final ApplicationSettings mApplicationSettings;

    @Inject
    public MainModelImpl(final NetworkStateManager networkStateManager, final ApplicationSettings helper) {
        Validator.isArgNotNull(networkStateManager, "networkStateManager");
        Validator.isArgNotNull(helper, "helper");

        mApiInterface = ApiService.getClient().create(ApiInterface.class);
        mNetworkManager = networkStateManager;
        mApplicationSettings = helper;
    }

    @Override
    public Observable<Feed> getTrending() {
        return processObservable(mApiInterface.getTrendingNow(API_KEY, DEFAULT_LIMIT));
    }

    @Override
    public Observable<Feed> getByRequest(final String req) {
        return processObservable(mApiInterface.getSearch(req, API_KEY, DEFAULT_LIMIT));

    }

    @Override
    public Observable<Feed> loadMoreTrending(final int offset) {
        return processObservable(mApiInterface.getTrendingNow(API_KEY, DEFAULT_LIMIT + offset));
    }

    @Override
    public Observable<Feed> loadMoreSearch(final String req,
                                     final int offset) {
        return processObservable(mApiInterface.getSearch(req, API_KEY, DEFAULT_LIMIT + offset));
    }

    @Override
    public void addConnectivityObserver(final ConnectivityObserver observer) {
        Validator.isArgNotNull(observer, "observer");
        mNetworkManager.addObserver(observer);
    }

    @Override
    public void removeConnectivityObserver(final ConnectivityObserver observer) {
        Validator.isArgNotNull(observer, "observer");
        mNetworkManager.removeObserver(observer);
    }

    @Override
    public LoadMoreType getLoadMoreType() {
        return mApplicationSettings.getLoadMoreType();
    }

    private Observable<Feed> processObservable(final Observable<Feed> observable) {
        Validator.isArgNotNull(observable, "observable");

        return observable.subscribeOn(Schedulers.io())
                         .observeOn(AndroidSchedulers.mainThread());
    }
}