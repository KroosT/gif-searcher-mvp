package com.qulix.shestakaa.gifsearchermvp.presenter.gifdetails;

import com.qulix.shestakaa.gifsearchermvp.model.NetworkStateManager;
import com.qulix.shestakaa.gifsearchermvp.model.gifdetails.DetailsModel;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Downloadable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.view.gifdetails.DetailsView;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

@ParametersAreNonnullByDefault
public class DetailsPresenter {

    private final DetailsModel mModel;
    private final DetailsRouter mRouter;
    private DetailsView mView;
    private Cancelable mRequestHandler;
    @Inject
    NetworkStateManager mNetworkStateManager;
    private final CompositeDisposable mDisposables;

    @Inject
    public DetailsPresenter(final DetailsModel model, final DetailsRouter router) {
        Validator.isArgNotNull(model, "model");
        Validator.isArgNotNull(router, "router");
        mModel = model;
        mRouter = router;
        mDisposables = new CompositeDisposable();
    }

    public void bindView(final DetailsView view, final String url) {
        Validator.isArgNotNull(view, "view");
        Validator.isArgNotNull(url, "url");

        mView = view;
        mDisposables.add(mNetworkStateManager.getObservable().subscribe(this::processStatusChange));
        mView.showGif(url);
    }

    private void processStatusChange(final Boolean status) {
        if (status) {
            mView.dismissOfflineModeSuggestion();
        } else {
            mView.showOfflineModeSuggestion();
        }
    }

    public void unbindView() {
        mView = null;
        if (mDisposables != null && !mDisposables.isDisposed()) {
            mDisposables.clear();
        }
    }

    private void saveGif(final String url) {
        Validator.isArgNotNull(url, "url");

        mView.showDownloading();
        final Downloadable downloadable = result -> {
            if (mView == null) return;

            if (result) {
                mView.showSuccess();
            } else {
                mView.showError();
            }
        };

        mRequestHandler = mModel.saveGifByUrl(url, downloadable);
    }

    public void cancelSaving() {
        if (mRequestHandler != null) {
            mRequestHandler.cancelRequest();
        }
    }

    private void switchToOffline() {
        mRouter.goToOfflineScreen();
    }

    public void switchToMainScreen() { mRouter.goToMainScreen(); }

    public void onSaveButtonClicked(final String url) {
        Validator.isArgNotNull(url, "url");
        saveGif(url);
    }

    public void onSnackBarClicked() {
        switchToOffline();
    }
}
