package com.qulix.shestakaa.gifsearchermvp.view.offline;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.qulix.shestakaa.gifsearchermvp.GSApplication;
import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.presenter.offline.OfflinePresenter;
import com.qulix.shestakaa.gifsearchermvp.view.offline.module.OfflineFragmentModule;

import javax.inject.Inject;

public class OfflineFragment extends Fragment {

    @Inject
    OfflinePresenter mPresenter;
    private OfflineViewImpl mView;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_offline, container, false);
        if (savedInstanceState == null) {
            GSApplication.getComponent().plus(new OfflineFragmentModule(this)).inject(this);

            mView = new OfflineViewImpl(v.findViewById(R.id.rootOffline), mPresenter);
            if (isSinglePaneMode()) {
                setHasOptionsMenu(true);
            }
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
                mPresenter.switchToMainScreen();
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
        mPresenter.cancelLoading();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.unbindView();
        }
        super.onDestroyView();
    }

    public static OfflineFragment newInstance() {
        return new OfflineFragment();
    }

    private boolean isSinglePaneMode() {
        return getActivity().findViewById(R.id.fragmentDetail) == null;
    }

}
