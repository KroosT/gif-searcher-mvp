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
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

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

        final Context context = mView.getContext();
        mOfflineAdapter = new OfflineAdapter(context, new ArrayList<byte[]>());

        final TextView offlineTitle = view.findViewById(R.id.offline_title);
        offlineTitle.setText(R.string.offline_mode);

        final RecyclerView recyclerView = mView.findViewById(R.id.offline_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mOfflineAdapter);

        final View.OnClickListener onSnackBarClick = new View.OnClickListener() {
            @Override
            public void onClick(final android.view.View v) {
                mOfflinePresenter.onSnackBarClicked();
            }
        };

        mSnackbar = Snackbar.make(mView, R.string.connection_restored, Snackbar.LENGTH_LONG)
                            .setAction(R.string.go_online, onSnackBarClick)
                            .setActionTextColor(ContextCompat.getColor(context,
                                                                       R.color.colorPrimary));
        mOfflinePresenter.bindView(this);
    }

    @Override
    public void showAvailableGifs(final List<byte[]> bytes) {
        Validator.isArgNotNull(bytes, "bytes");
        mOfflineAdapter.updateDate(bytes);
    }

    @Override
    public void showOnlineModeAvailable() {
        mSnackbar.show();
    }

    @Override
    public void dismissOnlineModeAvailable() {
        mSnackbar.dismiss();
    }
}
