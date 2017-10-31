package com.qulix.shestakaa.gifsearchermvp.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.model.API.ApiInterface;
import com.qulix.shestakaa.gifsearchermvp.model.API.ApiService;
import com.qulix.shestakaa.gifsearchermvp.model.API.Feed;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;


import java.util.Observable;
import java.util.Observer;

import javax.annotation.ParametersAreNonnullByDefault;

import retrofit2.Call;
import retrofit2.Callback;

import static com.qulix.shestakaa.gifsearchermvp.model.LoadMoreType.BUTTON;

@ParametersAreNonnullByDefault
public class ModelImpl implements Model {

    private final ApiInterface mApiInterface;
    private static final String API_KEY = "fWieUtS84ZkjIWupFAQvqpUapoYj1k29";
    private static final int DEFAULT_LIMIT = 25;

    public ModelImpl() {
        mApiInterface = ApiService.getClient().create(ApiInterface.class);
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
    public void setObserver(final Observer observer) {
        Validator.isArgNotNull(observer, "observer");
        NetworkStateReceiver.getObservable().addObserver(observer);
    }

    @Override
    public void removeObserver(final Observer observer) {
        Validator.isArgNotNull(observer, "observer");
        NetworkStateReceiver.getObservable().deleteObserver(observer);
    }

    @Override
    public LoadMoreType getLoadMoreType(final Context context) {
        Validator.isArgNotNull(context, "context");

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final String type = preferences.getString(context.getResources()
                                                         .getString(R.string.pref_key), null);
        Validator.isArgNotNull(type, "type");

        for (final LoadMoreType loadMoreType : LoadMoreType.values()) {
            if (type.equalsIgnoreCase(loadMoreType.getValue())) {
                return loadMoreType;
            }
        }
        return BUTTON;
    }

    private <T> Cancelable createEventListener(final Call<T> call) {
        Validator.isArgNotNull(call, "call");
        return new Cancelable() {
            @Override
            public void onCancelRequest() {
                call.cancel();
            }
        };
    }
}