package com.qulix.shestakaa.gifsearchermvp.presenter;

import com.qulix.shestakaa.gifsearchermvp.model.API.Data;
import com.qulix.shestakaa.gifsearchermvp.model.API.Feed;
import com.qulix.shestakaa.gifsearchermvp.model.ModelInterface;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.view.Router;
import com.qulix.shestakaa.gifsearchermvp.view.ViewInterface;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@ParametersAreNonnullByDefault
public class Presenter {

    private final ModelInterface mModel;
    private final ViewInterface mView;
    private final Callback<Feed> mCallback;
    private final Router mRouter;
    private Cancelable mCancelable;

    public Presenter(final ModelInterface modelInterface,
                     final ViewInterface view,
                     final Router router){
        Validator.isArgNotNull(modelInterface, "modelInterface");
        Validator.isArgNotNull(view, "view");
        Validator.isArgNotNull(router, "router");

        mModel = modelInterface;
        mView = view;
        mRouter = router;
        mCallback = createCallback();

        onTitleClicked();
    }

    private Callback<Feed> createCallback() {
        return new Callback<Feed>() {
            @Override
            public void onResponse(final Call<Feed> call,
                                   final Response<Feed> response) {
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
            public void onFailure(final Call<Feed> call, final Throwable t) {
                if (!call.isCanceled()) {
                    mView.showError();
                }
            }
        };
    }

    public void onTitleClicked() {
        onStop();
        mCancelable = mModel.getTrending(mCallback);
    }

    public void onCloseIconClicked(final String request) {
        Validator.isArgNotNull(request, "request");
        onStop();
        mCancelable = mModel.getByRequest(mCallback, request);
    }

    public void onGifClick(final String url) {
        Validator.isArgNotNull(url, "url");
        mRouter.goToDetailsScreen(url);
    }

    public void onStop() {
        if (mCancelable != null) {
            mCancelable.onCancel();
        }
    }

    public void onSwitchToOffline() {
        mRouter.goToOfflineScreen();
    }

}
