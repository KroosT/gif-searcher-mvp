package com.qulix.shestakaa.gifsearchermvp.presenter;

import com.qulix.shestakaa.gifsearchermvp.model.API.Data;
import com.qulix.shestakaa.gifsearchermvp.model.API.Feed;
import com.qulix.shestakaa.gifsearchermvp.model.Model;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@ParametersAreNonnullByDefault
public class Presenter {

    private final Model mModel;
    private final Callback<Feed> mCallback;
    private final Router mRouter;
    private Cancelable mRequest;
    private View mView;

    public Presenter(final Model model, final Router router){
        Validator.isArgNotNull(model, "model");
        Validator.isArgNotNull(router, "router");

        mModel = model;
        mRouter = router;
        mCallback = createCallback();

        onMainScreenSet();
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

    public void onViewBind(final View view) {
        Validator.isArgNotNull(view, "view");
        mView = view;
    }

    public void onViewUnbind() {
        mView = null;
    }

    public void onMainScreenSet() {
        onStopRequest();
        mRequest = mModel.getTrending(mCallback);
    }

    public void onTextInputChanged(final String request) {
        Validator.isArgNotNull(request, "request");
        onStopRequest();
        mRequest = mModel.getByRequest(mCallback, request);
    }

    public void onGifClicked(final String url) {
        Validator.isArgNotNull(url, "url");
        mRouter.goToDetailsScreen(url);
    }

    public void onStopRequest() {
        if (mRequest != null) {
            mRequest.onCancel();
        }
    }

    public void onSwitchToOffline() {
        mRouter.goToOfflineScreen();
    }

}
