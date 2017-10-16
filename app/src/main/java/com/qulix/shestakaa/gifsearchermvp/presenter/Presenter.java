package com.qulix.shestakaa.gifsearchermvp.presenter;

import javax.annotation.Nonnull;

import com.qulix.shestakaa.gifsearchermvp.model.API.Data;
import com.qulix.shestakaa.gifsearchermvp.model.API.Feed;
import com.qulix.shestakaa.gifsearchermvp.model.ModelInterface;
import com.qulix.shestakaa.gifsearchermvp.view.MainView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Presenter {

    private final ModelInterface mModelInterface;
    private final MainView mView;
    private final Callback<Feed> mCallback;

    public Presenter(final ModelInterface modelInterface, final MainView view){
        mModelInterface = modelInterface;
        mView = view;

        mCallback = new Callback<Feed>() {
            @Override
            public void onResponse(@Nonnull final Call<Feed> call,
                                   @Nonnull final Response<Feed> response) {
                final List<Data> data = response.body().getData();
                if (data.isEmpty()) {
                    mView.showNoGifsError();
                }
                mView.updateData(data);
            }

            @Override
            public void onFailure(@Nonnull final Call<Feed> call, @Nonnull final Throwable t) {
                mView.showError();
            }
        };

        getTrending();
    }

    public void getTrending() {
        mModelInterface.getTrending(mCallback);
    }

    public void getByRequest(final String request) {
        mModelInterface.getByRequest(mCallback, request);
    }
}
