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

    public void onViewBind(final DetailsView view) {
        Validator.isArgNotNull(view, "view");
        mView = view;
    }

    public void onViewUnbind() {
        mView = null;
    }

    public void onShowGif(final String url) {
        Validator.isArgNotNull(url, "url");
        mView.showGif(url);
    }

    public void onSaveGif(final String url) {
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

    public void onCancelSaving() {
        if (mRequestController != null) {
            mRequestController.onCancelRequest();
        }
    }

    public void onSwitchToOffline() {
        mRouter.goToOfflineScreen();
    }

    public void onSwitchToMainScreen() { mRouter.goToMainScreen(); }

    public void onAddObserver() {
        if (mObserver != null) {
            mModel.setObserver(mObserver);
        }
    }

    public void onRemoveObserver() {
        if (mObserver != null) {
            mModel.removeObserver(mObserver);
        }
    }
}
