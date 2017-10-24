package com.qulix.shestakaa.gifsearchermvp.view.offline;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.model.offline.OfflineModelImpl;
import com.qulix.shestakaa.gifsearchermvp.presenter.offline.OfflinePresenter;

public class OfflineFragment extends Fragment {


    private OfflinePresenter mPresenter;

    public OfflineFragment() {

    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_offline, container, false);
        mPresenter = new OfflinePresenter(new OfflineModelImpl(getContext()));
        final OfflineViewImpl offlineView = new OfflineViewImpl(v.findViewById(R.id.rootOffline),
                                                                mPresenter);

        return v;
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onCancelLoading();
    }

    public static OfflineFragment newInstance() {
        return new OfflineFragment();
    }
}
