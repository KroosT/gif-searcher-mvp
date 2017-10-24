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
    private Cancelable mRequest;
    private OfflineView mView;

    public OfflinePresenter(final OfflineModel offlineModel) {
        Validator.isArgNotNull(offlineModel, "offlineModel");
        mOfflineModel = offlineModel;
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
            public void onLoad(final List<byte[]> data) {
                mView.showAvailableGifs(data);
            }
        };

        mRequest = mOfflineModel.loadAvailableGifs(loadable);
    }

    public void onCancelLoading() {
        if (mRequest != null) {
            mRequest.onCancel();
        }
    }

}
