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

import static com.qulix.shestakaa.gifsearchermvp.presenter.RequestType.*;

@ParametersAreNonnullByDefault
public class Presenter {

    private final Model mModel;
    private final Callback<Feed> mCallback;
    private final Router mRouter;
    private Cancelable mRequestController;
    private View mView;
    private RequestType mPreviousRequest = TRENDING;
    private String mPreviousSearchQuery = "";
    private int mPreviousOffset = 0;
    private boolean mIsDataEnded = false;

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
                int totalCount = 0;
                if (body != null) {
                    data = body.getData();
                    totalCount = body.getPagination().getTotalCount();
                }
                if (data.isEmpty()) {
                    mView.showNoGifsError();
                }
                if (totalCount == data.size()) {
                    mIsDataEnded = true;
                }
                mPreviousOffset = data.size();
                mView.updateData(data, totalCount);
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
        mRequestController = mModel.getTrending(mCallback);
        mPreviousRequest = TRENDING;
        mIsDataEnded = false;
    }

    public void onTextInputChanged(final String request) {
        Validator.isArgNotNull(request, "request");
        onStopRequest();
        mRequestController = mModel.getByRequest(mCallback, request);
        mPreviousRequest = SEARCH;
        mPreviousSearchQuery = request;
        mIsDataEnded = false;
    }

    public void onGifClicked(final String url) {
        Validator.isArgNotNull(url, "url");
        mRouter.goToDetailsScreen(url);
    }

    public void onStopRequest() {
        if (mRequestController != null) {
            mRequestController.onCancelRequest();
        }
    }

    public void onSwitchToOffline() {
        mRouter.goToOfflineScreen();
    }

    public void onLoadMoreClicked() {
        if (!mIsDataEnded) {
            switch (mPreviousRequest) {
                case SEARCH:
                    mRequestController = mModel.loadMoreSearch(mCallback, mPreviousSearchQuery,
                                                               mPreviousOffset);
                    break;
                case TRENDING:
                default:
                    mRequestController = mModel.loadMoreTrending(mCallback, mPreviousOffset);
            }
        } else {
            mView.showGifsEndedInfo();
        }
    }
}
