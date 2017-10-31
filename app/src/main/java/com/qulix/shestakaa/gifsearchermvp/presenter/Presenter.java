package com.qulix.shestakaa.gifsearchermvp.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qulix.shestakaa.gifsearchermvp.model.API.Data;
import com.qulix.shestakaa.gifsearchermvp.model.API.Feed;
import com.qulix.shestakaa.gifsearchermvp.model.Model;
import com.qulix.shestakaa.gifsearchermvp.model.NetworkObservable;
import com.qulix.shestakaa.gifsearchermvp.utils.AdapterData;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.annotation.ParametersAreNonnullByDefault;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.qulix.shestakaa.gifsearchermvp.model.LoadMoreType.SCROLL;
import static com.qulix.shestakaa.gifsearchermvp.presenter.RequestType.SEARCH;
import static com.qulix.shestakaa.gifsearchermvp.presenter.RequestType.TRENDING;

@ParametersAreNonnullByDefault
public class Presenter {

    private static final int DEFAULT_GIF_COUNT_LIMIT = 25;

    private final Model mModel;
    private final Callback<Feed> mCallback;
    private final Router mRouter;
    private Cancelable mRequestController;
    private View mView;
    private RequestType mPreviousRequest = TRENDING;
    private String mPreviousSearchQuery = "";
    private int mPreviousOffset = 0;
    private boolean mIsDataEnded = false;

    public Presenter(final Model model, final Router router) {
        Validator.isArgNotNull(model, "model");
        Validator.isArgNotNull(router, "router");

        mModel = model;
        mModel.setObserver(createConnectivityObserver());
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
                final AdapterData adapterData = new AdapterData();
                final List<String> urls = new ArrayList<>();
                int totalCount = 0;
                if (body != null) {
                    for (final Data data : body.getData()) {
                        urls.add(data.getImages().getOriginal().getUrl());
                    }
                    adapterData.setUrls(urls);
                    totalCount = body.getPagination().getTotalCount();
                }
                if (urls.isEmpty()) {
                    mView.showNoGifsError();
                }
                if (totalCount == urls.size()) {
                    mIsDataEnded = true;
                }
                mPreviousOffset = urls.size();
                mView.updateData(adapterData);
            }

            @Override
            public void onFailure(final Call<Feed> call, final Throwable t) {
                if (!call.isCanceled()) {
                    mView.showError();
                }
            }
        };
    }

    private Observer createConnectivityObserver() {
        return new Observer() {
            @Override
            public void update(final Observable o, final Object arg) {
                if (mView != null) {
                    if (((NetworkObservable) o).isConnected()) {
                        mView.dismissOfflineModeSuggestion();
                    } else {
                        mView.showOfflineModeSuggestion();
                    }
                }
            }
        };
    }

    public RecyclerView.OnScrollListener createOnScrollListener(final Context context) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
                final LinearLayoutManager layoutManager =
                        (LinearLayoutManager) recyclerView.getLayoutManager();
                if (dy > 0) {
                    final int totalItemCount = layoutManager.getItemCount();
                    final int firstVisible = layoutManager.findFirstVisibleItemPosition();
                    if (firstVisible == totalItemCount - DEFAULT_GIF_COUNT_LIMIT / 2
                            && !mIsDataEnded) {
                        if (mModel.getLoadMoreType(context) == SCROLL) {
                            onLoadMoreClicked();
                            mView.showProgressBar();
                        } else {
                            mView.showButton();
                        }
                    }
                }
            }
        };
    }

    public void onViewBind(final View view) {
        Validator.isArgNotNull(view, "view");
        mView = view;
    }

    public void onViewUnbind() {
        onStopRequest();
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
            mView.showProgressBar();
            switch (mPreviousRequest) {
                case SEARCH:
                    mRequestController = mModel.loadMoreSearch(mCallback,
                                                               mPreviousSearchQuery,
                                                               mPreviousOffset);
                    break;
                case TRENDING:
                default:
                    mRequestController = mModel.loadMoreTrending(mCallback, mPreviousOffset);
            }
        } else {
            mView.showDataEnded();
        }
    }

}
