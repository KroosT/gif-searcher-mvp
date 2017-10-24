package com.qulix.shestakaa.gifsearchermvp.presenter.offline;

import com.qulix.shestakaa.gifsearchermvp.model.offline.OfflineModelInterface;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Loadable;
import com.qulix.shestakaa.gifsearchermvp.view.offline.OfflineViewImpl;
import com.qulix.shestakaa.gifsearchermvp.view.offline.OfflineViewInterface;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class OfflinePresenter {

    private final OfflineViewInterface mOfflineViewInterface;
    private final OfflineModelInterface mOfflineModelInterface;
    private Cancelable mCancelable;

    public OfflinePresenter(final OfflineViewImpl offlineView,
                            final OfflineModelInterface offlineModelInterface) {
        mOfflineModelInterface = offlineModelInterface;
        mOfflineViewInterface = offlineView;
    }

    public void onViewStarted() {

        final Loadable loadable = new Loadable() {
            @Override
            public void onLoad(final List<byte[]> data) {
                mOfflineViewInterface.showAvailableGifs(data);
            }
        };

        mCancelable = mOfflineModelInterface.loadAvailableGifs(loadable);
    }

    public void onCancelLoading() {
        if (mCancelable != null) {
            mCancelable.onCancel();
        }
    }

}
