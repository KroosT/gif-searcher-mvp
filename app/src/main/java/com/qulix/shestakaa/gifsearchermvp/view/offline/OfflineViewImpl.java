package com.qulix.shestakaa.gifsearchermvp.view.offline;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.presenter.offline.OfflinePresenter;
import com.qulix.shestakaa.gifsearchermvp.utils.ConnectionStatus;
import com.qulix.shestakaa.gifsearchermvp.utils.NetworkStateReceiver;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.qulix.shestakaa.gifsearchermvp.utils.StringConstants.CONNECTION_RESTORED;
import static com.qulix.shestakaa.gifsearchermvp.utils.StringConstants.GO_ONLINE;

@ParametersAreNonnullByDefault
public class OfflineViewImpl implements OfflineView {

    private final View mView;
    private final OfflinePresenter mOfflinePresenter;
    private final OfflineAdapter mOfflineAdapter;
    private final Snackbar mSnackbar;

    public OfflineViewImpl(final View view, final OfflinePresenter offlinePresenter) {
        Validator.isArgNotNull(view, "view");
        Validator.isArgNotNull(offlinePresenter, "offlinePresenter");

        mView = view;
        mOfflinePresenter = offlinePresenter;
        mOfflinePresenter.onViewBind(this);

        mOfflineAdapter = new OfflineAdapter(mView.getContext(), new ArrayList<byte[]>());

        final TextView offlineTitle = view.findViewById(R.id.offline_title);
        offlineTitle.setText(R.string.offline_mode);

        final RecyclerView recyclerView = mView.findViewById(R.id.offline_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));
        recyclerView.setAdapter(mOfflineAdapter);

        mOfflinePresenter.onScreenStarted();

        final View.OnClickListener onSnackBarClick = new View.OnClickListener() {
            @Override
            public void onClick(final android.view.View v) {
                mOfflinePresenter.onSwitchToOnline();
            }
        };

        final Context context = mView.getContext();

        mSnackbar = Snackbar.make(mView, CONNECTION_RESTORED, Snackbar.LENGTH_LONG)
                            .setAction(GO_ONLINE, onSnackBarClick)
                            .setActionTextColor(ContextCompat.getColor(context,
                                                                       R.color.colorPrimary));
        final ConnectionStatus connectionStatus = NetworkStateReceiver.getObservable()
                                                                      .getConnectionStatus();
        if (connectionStatus == ConnectionStatus.CONNECTED) {
            mSnackbar.show();
        }
    }

    @Override
    public void showAvailableGifs(final List<byte[]> bytes) {
        Validator.isArgNotNull(bytes, "bytes");
        mOfflineAdapter.updateDate(bytes);
    }

    public void showOnlineModeAvailable() {
        mSnackbar.show();
    }

    public void dismissOnlineModeAvailable() {
        mSnackbar.dismiss();
    }
}
