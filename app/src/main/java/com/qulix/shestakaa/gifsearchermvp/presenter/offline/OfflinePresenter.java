package com.qulix.shestakaa.gifsearchermvp.presenter.offline;

import com.qulix.shestakaa.gifsearchermvp.model.NetworkObservable;
import com.qulix.shestakaa.gifsearchermvp.model.offline.OfflineModel;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Loadable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.view.offline.OfflineView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class OfflinePresenter {

    private final OfflineModel mModel;
    private final Observer mObserver;
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

    private Observer createConnectivityObserver() {
        return new Observer() {
            @Override
            public void update(final Observable o, final Object arg) {
                if (mView != null) {
                    if (((NetworkObservable) o).isConnected()) {
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
        if (mObserver != null) {
            mModel.setObserver(mObserver);
        }
    }

    public void removeObserver() {
        if (mObserver != null) {
            mModel.removeObserver(mObserver);
        }
    }

    public void onSnackBarClicked() {
        switchToMainScreen();
    }
}
