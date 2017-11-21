package com.qulix.shestakaa.gifsearchermvp.presenter.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qulix.shestakaa.gifsearchermvp.model.API.Data;
import com.qulix.shestakaa.gifsearchermvp.model.API.Feed;
import com.qulix.shestakaa.gifsearchermvp.model.main.Model;
import com.qulix.shestakaa.gifsearchermvp.model.NetworkStateManager;
import com.qulix.shestakaa.gifsearchermvp.presenter.RequestType;
import com.qulix.shestakaa.gifsearchermvp.utils.AdapterData;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.ConnectivityObserver;
import com.qulix.shestakaa.gifsearchermvp.utils.StringUtils;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.view.main.View;

import java.util.ArrayList;
import java.util.List;

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
    private Cancelable mRequestHandler;
    private View mView;
    private RequestType mPreviousRequest = null;
    private String mPreviousSearchQuery = "";
    private int mPreviousOffset = 0;
    @NonNull
    private final ConnectivityObserver mObserver;
    private boolean mIsDataEnded = false;

    public Presenter(final Model model, final Router router) {
        Validator.isArgNotNull(model, "model");
        Validator.isArgNotNull(router, "router");

        mModel = model;
        mObserver = createConnectivityObserver();
        mRouter = router;
        mCallback = createCallback();

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
                    mView.showBlankScreen();
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

    @NonNull
    private ConnectivityObserver createConnectivityObserver() {
        return new ConnectivityObserver() {
            @Override
            public void update(final NetworkStateManager manager) {
                if (mView != null) {
                    if (manager.isConnected()) {
                        mView.dismissOfflineModeSuggestion();
                    } else {
                        mView.showOfflineModeSuggestion();
                    }
                }
            }
        };
    }

    @NonNull
    public RecyclerView.OnScrollListener createOnScrollListener() {
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
                        if (mModel.getLoadMoreType() == SCROLL) {
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

    public void bindView(final View view) {
        Validator.isArgNotNull(view, "view");
        mView = view;
        repeatPreviousRequest();
    }


    public void unbindView() {
        stopRequest();
        mView = null;
    }

    public void repeatPreviousRequest() {
        stopRequest();
        mView.showMainProgressBar();
        if (mPreviousRequest == null) {
            setTrendingScreen();
        } else if (mPreviousRequest == TRENDING) {
            mRequestHandler = mModel.getTrending(mCallback);
        } else {
            mRequestHandler = mModel.getByRequest(mCallback, mPreviousSearchQuery);
        }
    }

    private void setTrendingScreen() {
        mRequestHandler = mModel.getTrending(mCallback);
        mPreviousRequest = TRENDING;
        mIsDataEnded = false;
    }

    public void onTextInputChanged(final String request) {
        Validator.isArgNotNull(request, "request");
        stopRequest();
        if (StringUtils.isNotNullOrBlank(request)) {
            mRequestHandler = mModel.getByRequest(mCallback, request);
            mPreviousRequest = SEARCH;
            mPreviousSearchQuery = request;
            mIsDataEnded = false;
        } else {
            setTrendingScreen();
        }
    }

    public void onGifClicked(final String url) {
        Validator.isArgNotNull(url, "url");
        mRouter.goToDetailsScreen(url);
    }

    public void stopRequest() {
        if (mRequestHandler != null) {
            mRequestHandler.cancelRequest();
        }
    }

    public void addObserver() {
        mModel.addConnectivityObserver(mObserver);
    }

    public void removeObserver() {
        mModel.removeConnectivityObserver(mObserver);
    }

    public void switchToOffline() {
        mRouter.goToOfflineScreen();
    }

    public void onLoadMoreClicked() {
        if (!mIsDataEnded) {
            mView.showProgressBar();
            switch (mPreviousRequest) {
                case SEARCH:
                    mRequestHandler = mModel.loadMoreSearch(mCallback,
                                                            mPreviousSearchQuery,
                                                            mPreviousOffset);
                    break;
                case TRENDING:
                default:
                    mRequestHandler = mModel.loadMoreTrending(mCallback, mPreviousOffset);
            }
        } else {
            mView.showDataEnded();
        }
    }

}
