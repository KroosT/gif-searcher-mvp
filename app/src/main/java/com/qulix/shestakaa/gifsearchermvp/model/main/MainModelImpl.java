package com.qulix.shestakaa.gifsearchermvp.model.main;

import com.qulix.shestakaa.gifsearchermvp.model.API.ApiInterface;
import com.qulix.shestakaa.gifsearchermvp.model.API.ApiService;
import com.qulix.shestakaa.gifsearchermvp.model.API.Feed;
import com.qulix.shestakaa.gifsearchermvp.model.ApplicationSettings;
import com.qulix.shestakaa.gifsearchermvp.model.LoadMoreType;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@ParametersAreNonnullByDefault
public class MainModelImpl implements MainModel {

    private final ApiInterface mApiInterface;
    private static final String API_KEY = "fWieUtS84ZkjIWupFAQvqpUapoYj1k29";
    private static final int DEFAULT_LIMIT = 25;
    private final ApplicationSettings mApplicationSettings;

    @Inject
    public MainModelImpl(final ApplicationSettings helper) {
        Validator.isArgNotNull(helper, "helper");

        mApiInterface = ApiService.getClient().create(ApiInterface.class);
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
    public LoadMoreType getLoadMoreType() {
        return mApplicationSettings.getLoadMoreType();
    }

    private Observable<Feed> processObservable(final Observable<Feed> observable) {
        Validator.isArgNotNull(observable, "observable");

        return observable.subscribeOn(Schedulers.io())
                         .observeOn(AndroidSchedulers.mainThread())
                         .retry();
    }
}