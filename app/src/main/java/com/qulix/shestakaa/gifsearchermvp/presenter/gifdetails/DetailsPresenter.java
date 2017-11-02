package com.qulix.shestakaa.gifsearchermvp.presenter.gifdetails;

import com.qulix.shestakaa.gifsearchermvp.model.NetworkObservable;
import com.qulix.shestakaa.gifsearchermvp.model.gifdetails.DetailsModel;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Downloadable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.view.gifdetails.DetailsView;

import java.util.Observable;
import java.util.Observer;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DetailsPresenter {

    private final DetailsModel mModel;
    private final DetailsRouter mRouter;
    private final Observer mObserver;
    private DetailsView mView;
    private Cancelable mRequestController;

    public DetailsPresenter(final DetailsModel model, final DetailsRouter router) {
        Validator.isArgNotNull(model, "model");
        Validator.isArgNotNull(router, "router");
        mModel = model;
        mObserver = createConnectivityObserver();
        mRouter = router;
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
                if (result) {
                    mView.showSuccess();
                } else {
                    mView.showError();
                }
            }
        };

        mRequestController = mModel.saveGifByUrl(url, downloadable);
    }

    public void cancelSaving() {
        if (mRequestController != null) {
            mRequestController.cancelRequest();
        }
    }

    private void switchToOffline() {
        mRouter.goToOfflineScreen();
    }

    public void switchToMainScreen() { mRouter.goToMainScreen(); }

    public void addObserver() {
        if (mObserver != null) {
            mModel.setObserver(mObserver);
        }
    }

    public void removeObserver() {
        if (mObserver != null) {
            mModel.removeObserver(mObserver);
        }
    }

    public void onSaveButtonClicked(final String url) {
        Validator.isArgNotNull(url, "url");
        saveGif(url);
    }

    public void onSnackBarClicked() {
        switchToOffline();
    }
}
