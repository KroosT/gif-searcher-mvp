package com.qulix.shestakaa.gifsearchermvp.presenter;

import javax.annotation.Nonnull;

import com.qulix.shestakaa.gifsearchermvp.model.API.Data;
import com.qulix.shestakaa.gifsearchermvp.model.API.Feed;
import com.qulix.shestakaa.gifsearchermvp.model.ModelInterface;
import com.qulix.shestakaa.gifsearchermvp.view.ViewInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Presenter {

    private final ModelInterface mModelInterface;
    private final ViewInterface mView;
    private final Callback<Feed> mCallback;

    public Presenter(final ModelInterface modelInterface, final ViewInterface view){
        mModelInterface = modelInterface;
        mView = view;
        mCallback = createCallback();
        onTitleClicked();
    }

    private Callback<Feed> createCallback() {
        return new Callback<Feed>() {
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
    }

    public void onTitleClicked() {
        mModelInterface.getTrending(mCallback);
    }

    public void onCancelIconClicked(final String request) {
        mModelInterface.getByRequest(mCallback, request);
    }
}
