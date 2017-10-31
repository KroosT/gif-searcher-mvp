package com.qulix.shestakaa.gifsearchermvp.view.offline;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.model.offline.OfflineModelImpl;
import com.qulix.shestakaa.gifsearchermvp.presenter.offline.OfflinePresenter;
import com.qulix.shestakaa.gifsearchermvp.presenter.offline.OfflineRouter;

public class OfflineFragment extends Fragment {

    private OfflinePresenter mPresenter;
    private OfflineViewImpl mView;

    public OfflineFragment() {

    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_offline, container, false);
        mPresenter = new OfflinePresenter(new OfflineModelImpl(getContext()),
                                          new OfflineRouter(getFragmentManager()));

        mView = new OfflineViewImpl(v.findViewById(R.id.rootOffline), mPresenter);
        if (isSinglePaneMode()) {
            setHasOptionsMenu(true);
        }
        return v;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_offline, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.offlineHome:
                mPresenter.onSwitchToMainScreen();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        mPresenter.onCancelLoading();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mPresenter.onViewUnbind();
        super.onDestroy();
    }

    public static OfflineFragment newInstance() {
        return new OfflineFragment();
    }

    private boolean isSinglePaneMode() {
        final View mainView = LayoutInflater.from(getContext())
                                            .inflate(R.layout.main, new LinearLayout(getContext()),
                                                     false);
        return mainView.findViewById(R.id.fragment) != null;
    }

}
