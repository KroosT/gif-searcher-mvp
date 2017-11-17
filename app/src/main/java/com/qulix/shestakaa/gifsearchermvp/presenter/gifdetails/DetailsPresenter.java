package com.qulix.shestakaa.gifsearchermvp.presenter.gifdetails;

import android.support.annotation.NonNull;

import com.qulix.shestakaa.gifsearchermvp.model.NetworkStateManager;
import com.qulix.shestakaa.gifsearchermvp.model.gifdetails.DetailsModel;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.ConnectivityObserver;
import com.qulix.shestakaa.gifsearchermvp.utils.Downloadable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.view.gifdetails.DetailsView;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DetailsPresenter {

    private final DetailsModel mModel;
    private final DetailsRouter mRouter;
    @NonNull
    private final ConnectivityObserver mObserver;
    private DetailsView mView;
    private Cancelable mRequestHandler;

    public DetailsPresenter(final DetailsModel model, final DetailsRouter router) {
        Validator.isArgNotNull(model, "model");
        Validator.isArgNotNull(router, "router");
        mModel = model;
        mObserver = createConnectivityObserver();
        mRouter = router;
    }

    @NonNull
    private ConnectivityObserver createConnectivityObserver() {
        return new ConnectivityObserver() {
            @Override
            public void update(final NetworkStateManager manager) {
                Validator.isArgNotNull(manager, "manager");

                if (mView == null) return;


                if (manager.isConnected()) {
                    mView.dismissOfflineModeSuggestion();
                } else {
                    mView.showOfflineModeSuggestion();
                }
            }
        };
    }

    public void bindView(final DetailsView view, final String url) {
        Validator.isArgNotNull(view, "view");
        Validator.isArgNotNull(url, "url");

        mView = view;
        mView.showGif(url);
    }

    public void unbindView() {
        mView = null;
    }

    private void saveGif(final String url) {
        Validator.isArgNotNull(url, "url");

        mView.showDownloading();
        final Downloadable downloadable = new Downloadable() {
            @Override
            public void onDataDownloaded(final boolean result) {
                if (mView == null) return;

                if (result) {
                    mView.showSuccess();
                } else {
                    mView.showError();
                }
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

    public void addObserver() {
        mModel.addConnectivityObserver(mObserver);
    }

    public void removeObserver() {
        mModel.removeConnectivityObserver(mObserver);
    }

    public void onSaveButtonClicked(final String url) {
        Validator.isArgNotNull(url, "url");
        saveGif(url);
    }

    public void onSnackBarClicked() {
        switchToOffline();
    }
}
