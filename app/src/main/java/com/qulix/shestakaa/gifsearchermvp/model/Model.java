package com.qulix.shestakaa.gifsearchermvp.model;

import com.qulix.shestakaa.gifsearchermvp.model.API.ApiInterface;
import com.qulix.shestakaa.gifsearchermvp.model.API.ApiService;
import com.qulix.shestakaa.gifsearchermvp.model.API.Feed;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;


import javax.annotation.Nonnull;

import retrofit2.Call;
import retrofit2.Callback;

public class Model implements ModelInterface {

    private final ApiInterface mApiInterface;
    private static final String API_KEY = "fWieUtS84ZkjIWupFAQvqpUapoYj1k29";

    public Model() {
        mApiInterface = ApiService.getClient().create(ApiInterface.class);
    }

    @Override
    public void getTrending(@Nonnull final Callback<Feed> callback) {
        Validator.isArgNotNull(callback, "callback");
        final Call<Feed> call = mApiInterface.getTrendingNow(API_KEY);
        call.enqueue(callback);
    }

    @Override
    public void getByRequest(@Nonnull final Callback<Feed> callback, final String req) {
        Validator.isArgNotNull(callback, "callback");
        final Call<Feed> call = mApiInterface.getSearch(req, API_KEY);
        call.enqueue(callback);
    }

}
