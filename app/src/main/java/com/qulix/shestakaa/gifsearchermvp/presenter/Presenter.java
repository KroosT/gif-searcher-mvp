package com.qulix.shestakaa.gifsearchermvp.presenter;

import javax.annotation.Nonnull;

import com.qulix.shestakaa.gifsearchermvp.model.API.Data;
import com.qulix.shestakaa.gifsearchermvp.model.API.Feed;
import com.qulix.shestakaa.gifsearchermvp.model.ModelInterface;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.view.ViewInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Presenter {

    private final ModelInterface mModelInterface;
    private final ViewInterface mView;
    private final Callback<Feed> mCallback;
    private Cancelable mCancelable;

    public Presenter(final ModelInterface modelInterface,
                     final ViewInterface view){
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

                final Feed body = response.body();
                List<Data> data = new ArrayList<>();
                if (body != null) {
                     data = body.getData();
                }
                if (data.isEmpty()) {
                    mView.showNoGifsError();
                }
                mView.updateData(data);
            }

            @Override
            public void onFailure(@Nonnull final Call<Feed> call, @Nonnull final Throwable t) {
                if (!call.isCanceled()) {
                    mView.showError();
                }
            }
        };
    }

    public void onTitleClicked() {
        if (mCancelable != null) {
            mCancelable.onCancel();
        }
        mCancelable = mModelInterface.getTrending(mCallback);
    }

    public void onCloseIconClicked(final String request) {
        if (mCancelable != null) {
            mCancelable.onCancel();
        }
        mCancelable = mModelInterface.getByRequest(mCallback, request);
    }

    public void onStop() {
        if (mCancelable != null) {
            mCancelable.onCancel();
        }
    }
}
