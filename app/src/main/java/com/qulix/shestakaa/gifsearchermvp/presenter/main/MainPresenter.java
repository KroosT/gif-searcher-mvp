package com.qulix.shestakaa.gifsearchermvp.presenter.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;

import com.qulix.shestakaa.gifsearchermvp.model.API.Data;
import com.qulix.shestakaa.gifsearchermvp.model.API.Feed;
import com.qulix.shestakaa.gifsearchermvp.model.NetworkStateManager;
import com.qulix.shestakaa.gifsearchermvp.model.main.MainModel;
import com.qulix.shestakaa.gifsearchermvp.presenter.RequestType;
import com.qulix.shestakaa.gifsearchermvp.utils.AdapterData;
import com.qulix.shestakaa.gifsearchermvp.utils.StringUtils;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.view.main.MainView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import static com.qulix.shestakaa.gifsearchermvp.model.LoadMoreType.SCROLL;
import static com.qulix.shestakaa.gifsearchermvp.presenter.RequestType.SEARCH;
import static com.qulix.shestakaa.gifsearchermvp.presenter.RequestType.TRENDING;

@ParametersAreNonnullByDefault
public class MainPresenter {

    private static final int DEFAULT_GIF_COUNT_LIMIT = 25;

    private final MainModel mModel;
    private final Router mRouter;
    private final CompositeDisposable mDisposables;
    private MainView mView;
    private RequestType mPreviousRequest = null;
    private String mPreviousSearchQuery = "";
    private int mPreviousOffset = 0;
    private boolean mIsDataEnded = false;
    @Inject
    NetworkStateManager mNetworkStateManager;

    @Inject
    public MainPresenter(final MainModel model, final Router router) {
        Validator.isArgNotNull(model, "model");
        Validator.isArgNotNull(router, "router");

        mModel = model;
        mRouter = router;
        mDisposables = new CompositeDisposable();
    }

    @NonNull
    public OnScrollListener createOnScrollListener() {
        return new OnScrollListener() {
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

    public void bindView(final MainView view) {
        Validator.isArgNotNull(view, "view");

        mView = view;
        mDisposables.add(mNetworkStateManager.getObservable().subscribe(this::processStatusChange));
        repeatPreviousRequest();
    }


    public void unbindView() {
        clearDisposables();
        mView = null;
    }

    public void repeatPreviousRequest() {
        mView.showMainProgressBar();
        if (mPreviousRequest == null) {
            setTrendingScreen();
        } else if (mPreviousRequest == TRENDING) {
            processObservable(mModel.getTrending());
        } else {
            processObservable(mModel.getByRequest(mPreviousSearchQuery));
        }
    }

    private void setTrendingScreen() {
        processObservable(mModel.getTrending());
        mPreviousRequest = TRENDING;
        mIsDataEnded = false;
    }

    public void onTextInputChanged(final String request) {
        Validator.isArgNotNull(request, "request");
        if (StringUtils.isNotNullOrBlank(request)) {
            processObservable(mModel.getByRequest(request));
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

    private void clearDisposables() {
        if (mDisposables != null && !mDisposables.isDisposed()) {
            mDisposables.clear();
        }
    }

    public void switchToOffline() {
        mRouter.goToOfflineScreen();
    }

    public void onLoadMoreClicked() {
        if (!mIsDataEnded) {
            mView.showProgressBar();
            switch (mPreviousRequest) {
                case SEARCH:
                    processObservable(mModel.loadMoreSearch(mPreviousSearchQuery,
                                                            mPreviousOffset));
                    break;
                case TRENDING:
                default:
                    processObservable(mModel.loadMoreTrending(mPreviousOffset));
            }
        } else {
            mView.showDataEnded();
        }
    }

    private void processObservable(final Observable<Feed> observable) {
        Validator.isArgNotNull(observable, "observable");
        mDisposables.add(observable.timeout(5000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                                   .subscribe(this::processResponse, throwable -> mView.showError()));
    }

    private void processResponse(final Feed response) {
        Validator.isArgNotNull(response, "response");

        final AdapterData adapterData = new AdapterData();
        final List<String> urls = new ArrayList<>();
        final int totalCount;
        for (final Data data : response.getData()) {
            urls.add(data.getImages().getOriginal().getUrl());
        }
        adapterData.setUrls(urls);
        totalCount = response.getPagination().getTotalCount();

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

    private void processStatusChange(final boolean status) {
        if (status) {
            mView.dismissOfflineModeSuggestion();
        } else {
            mView.showOfflineModeSuggestion();
        }
    }

}
