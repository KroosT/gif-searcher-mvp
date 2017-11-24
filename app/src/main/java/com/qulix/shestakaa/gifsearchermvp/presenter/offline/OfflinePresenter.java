package com.qulix.shestakaa.gifsearchermvp.presenter.offline;

import com.qulix.shestakaa.gifsearchermvp.model.NetworkStateManager;
import com.qulix.shestakaa.gifsearchermvp.model.offline.OfflineModel;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Loadable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.view.offline.OfflineView;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

@ParametersAreNonnullByDefault
public class OfflinePresenter {

    private final OfflineModel mModel;
    private Cancelable mRequestHandler;
    private OfflineView mView;
    private final OfflineRouter mRouter;
    private final CompositeDisposable mDisposables;
    @Inject
    NetworkStateManager mNetworkStateManager;

    @Inject
    public OfflinePresenter(final OfflineModel model, final OfflineRouter router) {
        Validator.isArgNotNull(model, "model");
        Validator.isArgNotNull(router, "router");

        mModel = model;
        mRouter = router;
        mDisposables = new CompositeDisposable();
    }

    public void bindView(final OfflineView view) {
        Validator.isArgNotNull(view, "view");
        mView = view;
        mDisposables.add(mNetworkStateManager.getObservable().subscribe(this::processStatusChange));
        setInitialScreen();
    }

    public void unbindView() {
        mView = null;
        if (mDisposables != null && !mDisposables.isDisposed()) {
            mDisposables.clear();
        }
    }

    private void setInitialScreen() {

        final Loadable loadable = data -> mView.showAvailableGifs(data);

        mRequestHandler = mModel.loadAvailableGifs(loadable);
    }

    public void cancelLoading() {
        if (mRequestHandler != null) {
            mRequestHandler.cancelRequest();
        }
    }

    public void switchToMainScreen() {
        mRouter.goToMainScreen();
    }

    public void onSnackBarClicked() {
        switchToMainScreen();
    }

    private void processStatusChange(final Boolean status) {
        if (status) {
            mView.showOnlineModeAvailable();
        } else {
            mView.dismissOnlineModeAvailable();
        }
    }
}
