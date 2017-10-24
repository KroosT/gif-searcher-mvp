package com.qulix.shestakaa.gifsearchermvp.view.offline;

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

    public OfflineViewImpl(final View view, final OfflinePresenter offlinePresenter) {
        Validator.isArgNotNull(view, "view");
        Validator.isArgNotNull(offlinePresenter, "offlinePresenter");

        mView = view;
        mOfflinePresenter = offlinePresenter;
        mOfflinePresenter.onViewBind(this);

        mOfflineAdapter = new OfflineAdapter(mView.getContext(), new ArrayList<byte[]>());

        final TextView offlineTitle = view.findViewById(R.id.offline_title);
        offlineTitle.setText(R.string.saved_gifs);

        final RecyclerView recyclerView = mView.findViewById(R.id.offline_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));
        recyclerView.setAdapter(mOfflineAdapter);

        mOfflinePresenter.onScreenStarted();
    }

    @Override
    public void showAvailableGifs(final List<byte[]> bytes) {
        Validator.isArgNotNull(bytes, "bytes");
        mOfflineAdapter.updateDate(bytes);
    }
}
