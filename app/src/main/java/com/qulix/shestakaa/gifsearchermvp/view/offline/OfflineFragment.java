package com.qulix.shestakaa.gifsearchermvp.view.offline;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.model.offline.OfflineModelImpl;
import com.qulix.shestakaa.gifsearchermvp.presenter.offline.OfflinePresenter;
import com.qulix.shestakaa.gifsearchermvp.presenter.offline.OfflineRouter;
import com.qulix.shestakaa.gifsearchermvp.utils.ConnectionStatus;
import com.qulix.shestakaa.gifsearchermvp.utils.NetworkObservable;
import com.qulix.shestakaa.gifsearchermvp.utils.NetworkStateReceiver;

import java.util.Observable;
import java.util.Observer;

public class OfflineFragment extends Fragment implements Observer {


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
        setHasOptionsMenu(true);
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
        NetworkStateReceiver.getObservable().addObserver(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mPresenter.onCancelLoading();
        NetworkStateReceiver.getObservable().deleteObserver(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mPresenter.onViewUnbind();
        super.onDestroy();
    }

    @Override
    public void update(final Observable o, final Object arg) {
        if (((NetworkObservable) o).getConnectionStatus() == ConnectionStatus.CONNECTED) {
            mView.showOnlineModeAvailable();
        } else {
            mView.dismissOnlineModeAvailable();
        }
    }

    public static OfflineFragment newInstance() {
        return new OfflineFragment();
    }

}
