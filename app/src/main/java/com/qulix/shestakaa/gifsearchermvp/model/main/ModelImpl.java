package com.qulix.shestakaa.gifsearchermvp.model.main;

import com.qulix.shestakaa.gifsearchermvp.model.API.ApiInterface;
import com.qulix.shestakaa.gifsearchermvp.model.API.ApiService;
import com.qulix.shestakaa.gifsearchermvp.model.API.Feed;
import com.qulix.shestakaa.gifsearchermvp.model.ApplicationSettings;
import com.qulix.shestakaa.gifsearchermvp.model.LoadMoreType;
import com.qulix.shestakaa.gifsearchermvp.model.NetworkStateManager;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.ConnectivityObserver;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;

@ParametersAreNonnullByDefault
public class ModelImpl implements Model {

    private final ApiInterface mApiInterface;
    private static final String API_KEY = "fWieUtS84ZkjIWupFAQvqpUapoYj1k29";
    private static final int DEFAULT_LIMIT = 25;
    private final NetworkStateManager mNetworkManager;
    private final ApplicationSettings mApplicationSettings;

    @Inject
    public ModelImpl(final NetworkStateManager networkStateManager, final ApplicationSettings helper) {
        Validator.isArgNotNull(networkStateManager, "networkStateManager");
        Validator.isArgNotNull(helper, "helper");

        mApiInterface = ApiService.getClient().create(ApiInterface.class);
        mNetworkManager = networkStateManager;
        mApplicationSettings = helper;
    }

    @Override
    public Cancelable getTrending(final Callback<Feed> callback) {
        Validator.isArgNotNull(callback, "callback");
        final Call<Feed> call = mApiInterface.getTrendingNow(API_KEY, DEFAULT_LIMIT);
        call.enqueue(callback);
        return createEventListener(call);
    }

    @Override
    public Cancelable getByRequest(final Callback<Feed> callback,
                                   final String req) {
        Validator.isArgNotNull(callback, "callback");
        final Call<Feed> call = mApiInterface.getSearch(req, API_KEY, DEFAULT_LIMIT);
        call.enqueue(callback);
        return createEventListener(call);
    }

    @Override
    public Cancelable loadMoreTrending(final Callback<Feed> callback, final int offset) {
        Validator.isArgNotNull(callback, "callback");

        final Call<Feed> call = mApiInterface.getTrendingNow(API_KEY, DEFAULT_LIMIT + offset);
        call.enqueue(callback);
        return createEventListener(call);
    }

    @Override
    public Cancelable loadMoreSearch(final Callback<Feed> callback,
                                     final String req,
                                     final int offset) {
        Validator.isArgNotNull(callback, "callback");

        final Call<Feed> call = mApiInterface.getSearch(req, API_KEY, DEFAULT_LIMIT + offset);
        call.enqueue(callback);
        return createEventListener(call);
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

    private <T> Cancelable createEventListener(final Call<T> call) {
        Validator.isArgNotNull(call, "call");
        return new Cancelable() {
            @Override
            public void cancelRequest() {
                call.cancel();
            }
        };
    }
}