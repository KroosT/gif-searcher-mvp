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

public class OfflineViewImpl implements OfflineViewInterface{

    private final View mView;
    private OfflinePresenter mOfflinePresenter;
    private final OfflineAdapter mOfflineAdapter;

    public OfflineViewImpl(final View view) {
        Validator.isArgNotNull(view, "view");
        mView = view;
        mOfflineAdapter = new OfflineAdapter(mView.getContext(), new ArrayList<byte[]>());

        final TextView offlineTitle = view.findViewById(R.id.offline_title);
        offlineTitle.setText(R.string.saved_gifs);

        final RecyclerView recyclerView = mView.findViewById(R.id.offline_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));
        recyclerView.setAdapter(mOfflineAdapter);
    }

    public void registerPresenter(final OfflinePresenter offlinePresenter) {
        Validator.isArgNotNull(offlinePresenter, "offlinePresenter");
        mOfflinePresenter = offlinePresenter;
        afterPresenterRegistered();
    }

    private void afterPresenterRegistered() {
        mOfflinePresenter.onViewStarted();
    }


    @Override
    public void showAvailableGifs(final List<byte[]> bytes) {
        mOfflineAdapter.updateDate(bytes);
    }
}
