package com.qulix.shestakaa.gifsearchermvp.presenter.offline;

import com.qulix.shestakaa.gifsearchermvp.model.offline.OfflineModel;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Loadable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.view.offline.OfflineView;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class OfflinePresenter {

    private final OfflineModel mOfflineModel;
    private Cancelable mRequestController;
    private OfflineView mView;
    private final OfflineRouter mRouter;

    public OfflinePresenter(final OfflineModel offlineModel, final OfflineRouter router) {
        Validator.isArgNotNull(offlineModel, "offlineModel");
        Validator.isArgNotNull(router, "router");

        mOfflineModel = offlineModel;
        mRouter = router;
    }

    public void onViewBind(final OfflineView view) {
        Validator.isArgNotNull(view, "view");
        mView = view;
    }

    public void onViewUnbind() {
        mView = null;
    }

    public void onScreenStarted() {

        final Loadable loadable = new Loadable() {
            @Override
            public void onDataLoaded(final List<byte[]> data) {
                mView.showAvailableGifs(data);
            }
        };

        mRequestController = mOfflineModel.loadAvailableGifs(loadable);
    }

    public void onCancelLoading() {
        if (mRequestController != null) {
            mRequestController.onCancelRequest();
        }
    }

    public void onSwitchToOnline() {
        mRouter.goToMainScreen();
    }
}
