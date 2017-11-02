package com.qulix.shestakaa.gifsearchermvp.presenter.offline;

import android.support.annotation.NonNull;

import com.qulix.shestakaa.gifsearchermvp.model.NetworkStateManager;
import com.qulix.shestakaa.gifsearchermvp.model.offline.OfflineModel;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.ConnectivityObserver;
import com.qulix.shestakaa.gifsearchermvp.utils.Loadable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.view.offline.OfflineView;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class OfflinePresenter {

    private final OfflineModel mModel;
    @NonNull
    private final ConnectivityObserver mObserver;
    private Cancelable mRequestController;
    private OfflineView mView;
    private final OfflineRouter mRouter;

    public OfflinePresenter(final OfflineModel model, final OfflineRouter router) {
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
                if (mView != null) {
                    if (manager.isConnected()) {
                        mView.showOnlineModeAvailable();
                    } else {
                        mView.dismissOnlineModeAvailable();
                    }
                }
            }
        };
    }

    public void bindView(final OfflineView view) {
        Validator.isArgNotNull(view, "view");
        mView = view;
        setInitialScreen();
    }

    public void unbindView() {
        mView = null;
    }

    private void setInitialScreen() {

        final Loadable loadable = new Loadable() {
            @Override
            public void onDataLoaded(final List<byte[]> data) {
                mView.showAvailableGifs(data);
            }
        };

        mRequestController = mModel.loadAvailableGifs(loadable);
    }

    public void cancelLoading() {
        if (mRequestController != null) {
            mRequestController.cancelRequest();
        }
    }

    public void switchToMainScreen() {
        mRouter.goToMainScreen();
    }

    public void addObserver() {
        mModel.addConnectivityObserver(mObserver);
    }

    public void removeObserver() {
        mModel.removeConnectivityObserver(mObserver);
    }

    public void onSnackBarClicked() {
        switchToMainScreen();
    }
}
